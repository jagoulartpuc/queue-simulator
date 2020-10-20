package sma.simulator;

import sma.domain.Event;
import sma.domain.Generator;
import sma.domain.QueueModel;
import sma.util.Printer;
import sma.util.Reader;

import java.io.IOException;
import java.util.*;

import static sma.util.Formatter.*;

public class SimpleSimulator {

    private Queue<Event> queue = new PriorityQueue<>();
    private int contRandoms = 0, lostClients = 0, queueController = 0;
    private double globalTime = 0;
    private Map<Integer, Double> stateTimes = new HashMap<>();

    public void runSimulation(String fileName, int seed) throws IOException {

        Generator generator = new Generator(seed, 100000);
        QueueModel model = Reader.readFile(fileName).get(0);
        Printer.printSimpleInputs(model, seed);

        queue.add(new Event(Event.Type.ARRIVAL, 1.0));
        while (contRandoms < generator.getRandoms().size()) {
            if (queue.element().getType().equals(Event.Type.ARRIVAL)) {
                manageArrival(model, generator, queue.element().getTime());
            } else {
                manageExit(model, generator, queue.element().getTime());
            }
            contRandoms++;
        }

        Printer.printStates(model.getName(), stateTimes, globalTime);
        System.out.println("Total de clientes perdidos na fila: " + lostClients);
        System.out.println("=============================================================");

    }

    public void manageArrival(QueueModel model, Generator generator, double eventTime) {
        countTime(eventTime);
        if (queueController < model.getCapacity()) {
            queueController++;
            if (queueController <= model.getServers()) {
                queue.add(new Event(Event.Type.EXIT, generator.getNextBetween(model.getAttendenceTimeStart(), model.getAttendenceTimeEnd()) + globalTime));
            }
        } else {
            lostClients++;
        }
        queue.add(new Event(Event.Type.ARRIVAL, generator.getNextBetween(model.getArrivalTimeStart(), model.getArrivalTimeEnd()) + globalTime));
    }

    public void manageExit(QueueModel model, Generator generator, double eventTime) {
        countTime(eventTime);
        queueController--;
        if (queueController >= model.getServers()) {
            queue.add(new Event(Event.Type.EXIT, generator.getNextBetween(model.getAttendenceTimeStart(), model.getAttendenceTimeEnd()) + globalTime));
        }
    }

    public void countTime(double eventTime) {
        double delta = eventTime - globalTime;
        double previous = getStateTime(stateTimes, queueController);
        stateTimes.put(queueController, delta + previous);
        globalTime = eventTime;
        queue.remove();
    }

}