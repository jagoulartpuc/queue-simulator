package sma.simulator;

import sma.domain.Event;
import sma.domain.Generator;
import sma.domain.Connection;
import sma.domain.QueueModel;
import sma.util.Printer;
import sma.util.Reader;
import java.io.IOException;
import java.util.*;

import static java.util.stream.Collectors.toMap;
import static sma.util.Formatter.getStateTime;

public class Simulator {

    private Queue<Event> events = new PriorityQueue<>();
    private double globalTime = 0;
    private Map<String, QueueModel> queueMap;

    public void runSimulation(String fileName, int seed) throws IOException {

        Generator generator = new Generator(seed, 100001);
        List<QueueModel> queueModels = Reader.readFile(fileName);
        queueMap = initQueues(queueModels);

        Printer.printInputs(queueModels, seed);

        events.add(new Event(Event.Type.ARRIVAL, 1));
        while (!generator.getRandoms().isEmpty()) {
            if (events.element().getType().equals(Event.Type.ARRIVAL)) {
                manageArrival(queueMap.get("Q1"), generator, events.element().getTime());
            } else if (events.element().getType().equals(Event.Type.PASSAGE)) {
                managePassage(events.element().getOrigin(), events.element().getDestiny(), generator, events.element().getTime());
            } else {
                manageExit(events.element().getOrigin(), generator, events.element().getTime());
            }
        }

        Printer.printOutputs(queueModels, globalTime);
    }

    private void manageArrival(QueueModel queue, Generator generator, double eventTime) {
        countTime(eventTime);
        if (queue.getCounter() < queue.getCapacity()) {
            queue.incrementCounter();
            if (queue.getCounter() <= queue.getServers()) {
                if (queue.getConnections() != null) {
                    routeEvents(generator, queue);
                } else {
                    if (queueMap.size() > 1) {
                        events.add(new Event(Event.Type.PASSAGE, generator.getNextBetween(queue.getAttendenceTimeStart(), queue.getAttendenceTimeEnd()) + globalTime, queue.getName(), "Q2"));
                    } else {
                        events.add(new Event(Event.Type.EXIT, generator.getNextBetween(queue.getAttendenceTimeStart(), queue.getAttendenceTimeEnd()) + globalTime, queue.getName()));
                    }
                }
            }
        } else {
            queue.incrementLostClients();
        }
        events.add(new Event(Event.Type.ARRIVAL, generator.getNextBetween(queue.getArrivalTimeStart(), queue.getArrivalTimeEnd()) + globalTime));
    }

    private void managePassage(String origin, String destiny, Generator generator, double eventTime) {
        countTime(eventTime);
        QueueModel originQueue = queueMap.get(origin);
        QueueModel destinyQueue = queueMap.get(destiny);
        originQueue.decrementCounter();
        if (originQueue.getCounter() >= originQueue.getServers()) {
            if (originQueue.getConnections() != null) {
                routeEvents(generator, originQueue);
            } else {
                events.add(new Event(Event.Type.PASSAGE, generator.getNextBetween(originQueue.getAttendenceTimeStart(), originQueue.getAttendenceTimeEnd()) + globalTime, origin, destiny));
            }
        }
        if(destinyQueue.getCounter() < destinyQueue.getCapacity()) {
            destinyQueue.incrementCounter();
            if (destinyQueue.getCounter() <= destinyQueue.getServers()) {
                if (destinyQueue.getConnections() != null) {
                    routeEvents(generator, destinyQueue);
                } else {
                    events.add(new Event(Event.Type.EXIT, generator.getNextBetween(destinyQueue.getAttendenceTimeStart(), destinyQueue.getAttendenceTimeEnd()) + globalTime, destiny));
                }
            }
        } else {
            destinyQueue.incrementLostClients();
        }
    }

    private void manageExit(String origin, Generator generator, double eventTime) {
        countTime(eventTime);
        QueueModel queue = queueMap.get(origin);
        queue.decrementCounter();
        if (queue.getCounter() >= queue.getServers()) {
            if (queue.getConnections() != null) {
                routeEvents(generator, queue);
            } else {
                events.add(new Event(Event.Type.EXIT, generator.getNextBetween(queue.getAttendenceTimeStart(), queue.getAttendenceTimeEnd()) + globalTime, queue.getName()));
            }
        }
    }

    private void countTime(double eventTime) {
        double delta = eventTime - globalTime;
        for (QueueModel queue: queueMap.values()) {
            double previous = getStateTime(queue.getStates(), queue.getCounter());
            queue.getStates().put(queue.getCounter(), delta + previous);
        }
        globalTime = eventTime;
        events.remove();
    }

    private Map<String, QueueModel> initQueues(List<QueueModel> queues) {
        return queues.stream().collect(toMap(QueueModel::getName, q -> q));
    }

    private void routeEvents(Generator generator, QueueModel queue) {
        double rand = generator.getNext();
        double probability = 0;
        for (Connection connection: queue.getConnections()) {
            probability += connection.getProb();
            if (connection.getDirection().equals("out")) {
                if (rand < probability) {
                    events.add(new Event(Event.Type.EXIT, generator.getNextBetween(queue.getAttendenceTimeStart(), queue.getAttendenceTimeEnd()) + globalTime, queue.getName()));
                    break;
                }
            } else {
                if (rand < probability) {
                    events.add(new Event(Event.Type.PASSAGE, generator.getNextBetween(queue.getAttendenceTimeStart(), queue.getAttendenceTimeEnd()) + globalTime, queue.getName(), connection.getDirection()));
                    break;
                }
            }
        }
    }

}