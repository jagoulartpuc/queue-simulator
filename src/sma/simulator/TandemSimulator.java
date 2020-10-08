package sma.simulator;

import sma.domain.Event;
import sma.domain.Generator;
import sma.domain.QueueModel;
import sma.util.Printer;
import sma.util.Reader;

import java.io.IOException;
import java.util.*;

public class TandemSimulator {

    private Queue<Event> queue = new PriorityQueue<>();
    private int contRandoms = 0, lostClients1 = 0, lostClients2 = 0, queue1Controller = 0, queue2Controller = 0;
    private double globalTime = 0;
    private Map<Integer, Double> stateTimes1 = new HashMap<>();
    private Map<Integer, Double> stateTimes2 = new HashMap<>();

    public void runSimulation(String fileName, int seed) throws IOException {

        List<QueueModel> models = Reader.readFile(fileName);
        Generator generator = new Generator(seed, 100000);
        QueueModel model1 = models.get(0);
        QueueModel model2 = models.get(1);

        Printer.printTandemInputs(model1, model2, seed);

        queue.add(new Event(Event.Type.ARRIVAL, 2.5));
        while (contRandoms < generator.getRandoms().size()) {
            if (queue.element().getType().equals(Event.Type.ARRIVAL)) {
                manageArrival(model1, generator, queue.element().getTime());
            } else if (queue.element().getType().equals(Event.Type.PASSAGE)) {
                managePassage(model1, model2, generator, queue.element().getTime());

            } else {
                manageExit(model2, generator, queue.element().getTime());
            }
            contRandoms++;
        }

        Printer.printTandemOutputs(stateTimes1, stateTimes2, globalTime, lostClients1, lostClients2);

    }

    public void manageArrival(QueueModel model, Generator generator, double eventTime) {
        countTime(eventTime);
        if (queue1Controller < model.getCapacity()) {
            queue1Controller++;
            if (queue1Controller <= model.getServers()) {
                queue.add(new Event(Event.Type.PASSAGE, generator.getNextIntBetween(model.getOutTimeStart(), model.getOutTimeEnd()) + globalTime));
            }
        } else {
            lostClients1++;
        }
        queue.add(new Event(Event.Type.ARRIVAL, generator.getNextIntBetween(model.getInTimeStart(), model.getInTimeEnd()) + globalTime));
    }

    public void managePassage(QueueModel model1, QueueModel model2, Generator generator, double eventTime) {
        countTime(eventTime);
        queue1Controller--;
        if (queue1Controller >= model1.getServers()) {
            queue.add(new Event(Event.Type.PASSAGE, generator.getNextIntBetween(model1.getOutTimeStart(), model1.getOutTimeEnd()) + globalTime));
        }
        if(queue2Controller < model2.getCapacity()) {
            queue2Controller++;
            if (queue2Controller <= model2.getServers()) {
                queue.add(new Event(Event.Type.EXIT, generator.getNextIntBetween(model2.getOutTimeStart(), model2.getOutTimeEnd()) + globalTime));
            }
        } else {
            lostClients2++;
        }

    }

    public void manageExit(QueueModel model, Generator generator, double eventTime) {
        countTime(eventTime);
        queue2Controller--;
        if (queue2Controller >= model.getServers()) {
            queue.add(new Event(Event.Type.EXIT, generator.getNextIntBetween(model.getOutTimeStart(), model.getOutTimeEnd()) + globalTime));
        }
    }

    public void countTime(double eventTime) {
        double delta = eventTime - globalTime;
        double previous1 = getStateTime(stateTimes1, queue1Controller);
        double previous2 = getStateTime(stateTimes2, queue2Controller);

        stateTimes1.put(queue1Controller, delta + previous1);
        stateTimes2.put(queue2Controller, delta + previous2);
        globalTime = eventTime;
        queue.remove();
    }

    public double calculateProbability(double totalTime, double globalTime) {
        return (globalTime * 100) / totalTime;
    }

    public String formatNumber(double n) {
        return String.format("%.4f", n);
    }

    public double getStateTime(Map<Integer, Double> map, int key) {
        if (map.get(key) == null) {
            return 0.0;
        } else {
            return map.get(key);
        }
    }

    public double getTotalTime(Map<Integer, Double> states) {
        int sum = 0;
        for (double time: states.values()) {
            sum+= time;
        }
        return sum;
    }

}

