package sma.simulator;

import sma.domain.Event;
import sma.domain.Generator;
import sma.domain.QueueModel;
import sma.util.Printer;
import sma.util.Reader;

import java.io.IOException;
import java.util.*;

import static sma.util.Formatter.getStateTime;

public class TandemSimulator {

    private Queue<Event> events = new PriorityQueue<>();
    private int contRandoms = 0;
    private double globalTime = 0;
    private QueueModel queue1;
    private QueueModel queue2;

    public void runSimulation(String fileName, int seed) throws IOException {

        List<QueueModel> queues = Reader.readFile(fileName);
        Generator generator = new Generator(seed, 100000);
        queue1 = queues.get(0);
        queue2 = queues.get(1);

        Printer.printTandemInputs(queue1, queue2, seed);

        events.add(new Event(Event.Type.ARRIVAL, 2.5));
        while (contRandoms < generator.getRandoms().size()) {
            if (events.element().getType().equals(Event.Type.ARRIVAL)) {
                manageArrival(queue1, generator, events.element().getTime());
            } else if (events.element().getType().equals(Event.Type.PASSAGE)) {
                managePassage(queue1, queue2, generator, events.element().getTime());

            } else {
                manageExit(queue2, generator, events.element().getTime());
            }
            contRandoms++;
        }

        Printer.printOutputs(queues, globalTime);
    }

    public void manageArrival(QueueModel queue, Generator generator, double eventTime) {
        countTime(eventTime);
        if (queue.getCounter() < queue.getCapacity() || queue.getCapacity() == Integer.MAX_VALUE) {
            queue.incrementCounter();
            if (queue.getCounter() <= queue.getServers()) {
                events.add(new Event(Event.Type.PASSAGE, generator.getNextBetween(queue.getAttendenceTimeStart(), queue.getAttendenceTimeEnd()) + globalTime));
            }
        } else {
            queue.incrementLostClients();
        }
        events.add(new Event(Event.Type.ARRIVAL, generator.getNextBetween(queue.getArrivalTimeStart(), queue.getArrivalTimeEnd()) + globalTime));
    }

    public void managePassage(QueueModel queue1, QueueModel queue2, Generator generator, double eventTime) {
        countTime(eventTime);
        queue1.decrementCounter();
        if (queue1.getCounter() >= queue1.getServers()) {
            events.add(new Event(Event.Type.PASSAGE, generator.getNextBetween(queue1.getAttendenceTimeStart(), queue1.getAttendenceTimeEnd()) + globalTime));
        }
        if(queue2.getCounter() < queue2.getCapacity()) {
            queue2.incrementCounter();
            if (queue2.getCounter() <= queue2.getServers()) {
                events.add(new Event(Event.Type.EXIT, generator.getNextBetween(queue2.getAttendenceTimeStart(), queue2.getAttendenceTimeEnd()) + globalTime));
            }
        } else {
            queue2.incrementLostClients();
        }

    }

    public void manageExit(QueueModel queue, Generator generator, double eventTime) {
        countTime(eventTime);
        queue.decrementCounter();
        if (queue.getCounter() >= queue.getServers()) {
            events.add(new Event(Event.Type.EXIT, generator.getNextBetween(queue.getAttendenceTimeStart(), queue.getAttendenceTimeEnd()) + globalTime));
        }
    }

    public void countTime(double eventTime) {
        double delta = eventTime - globalTime;
        double previous1 = getStateTime(queue1.getStates(), queue1.getCounter());
        double previous2 = getStateTime(queue2.getStates(), queue2.getCounter());

        queue1.getStates().put(queue1.getCounter(), delta + previous1);
        queue2.getStates().put(queue2.getCounter(), delta + previous2);
        globalTime = eventTime;
        events.remove();
    }

}

