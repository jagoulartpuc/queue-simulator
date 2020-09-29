package sma;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

public class Simulator {

    private Queue<Event> queue = new PriorityQueue<>();
    private int contRandoms = 0, lostClients1 = 0, lostClients2 = 0, queue1Controller = 0, queue2Controller = 0;
    private double globalTime = 2.5;
    private Map<Integer, Double> stateTimes1 = new HashMap<>();
    private Map<Integer, Double> stateTimes2 = new HashMap<>();

    private List<Double> timeList = new ArrayList<>();
    private double actualTime;

    public void runSimulation(String fileName, int seed) throws IOException {

        List<QueueModel> models = Reader.readFile(fileName);
        Generator generator = new Generator(seed, 100000);
        QueueModel model1 = models.get(0);
        QueueModel model2 = models.get(1);

        System.out.println("=============================================================");
        System.out.println("Fila 1: " + model1.getInDistribution() + "/" + model1.getOutDistribution() + "/" + model1.getServers() + "/" + model1.getClients());
        System.out.println("Fila 2: " + model2.getInDistribution() + "/" + model2.getOutDistribution() + "/" + model2.getServers() + "/" + model2.getClients());

        System.out.println("Chegada: " + model1.getInTimeStart() + "..." + model1.getInTimeEnd());
        System.out.println("Passagem: " + model1.getOutTimeStart() + "..." + model1.getOutTimeEnd());
        System.out.println("Atendimento: " + model2.getOutTimeStart() + "..." + model2.getOutTimeEnd());
        System.out.println("Semente: " + seed);
        System.out.println(" ");

        timeList.add(globalTime);
        while (contRandoms < generator.getRandoms().size()) {
            if (queue.isEmpty()) {
               manageArrival(model1, generator);
               timeList.add(queue.element().getTime());
            }
            if (queue.element().getType().equals(Event.Type.ARRIVAL)) {
                manageArrival(model1, generator);
                globalTime = queue.element().getTime();
                stateTimes1.put(queue1Controller, globalTime - timeList.get(timeList.size() - 1) + getStateTime(stateTimes1, queue1Controller));
                timeList.add(globalTime);
                queue.remove();
            } else if (queue.element().getType().equals(Event.Type.PASSAGE)) {
                managePassage(model1, model2, generator);
                globalTime = queue.element().getTime();
                stateTimes1.put(queue1Controller, globalTime - timeList.get(timeList.size() - 1) + getStateTime(stateTimes1, queue1Controller));
                timeList.add(globalTime);
                queue.remove();

            } else {
                manageExit(model2, generator);
                globalTime = queue.element().getTime();
                stateTimes2.put(queue2Controller, globalTime - timeList.get(timeList.size() - 1) + getStateTime(stateTimes2, queue2Controller));
                timeList.add(globalTime);
                queue.remove();
            }
            contRandoms++;
        }

        System.out.println("Estado 0 da fila 1: Tempo: " + formatNumber(getStateTime(stateTimes1, 1)) + ", Probabilidade: " + formatNumber(calculateProbability(globalTime, getStateTime(stateTimes1, 1))) + " %");
        System.out.println("Estado 1 da fila 1: Tempo: " + formatNumber(getStateTime(stateTimes1, 2)) + ", Probabilidade: " + formatNumber(calculateProbability(globalTime, getStateTime(stateTimes1, 2))) + " %");
        System.out.println("Estado 2 da fila 1: Tempo: " + formatNumber(getStateTime(stateTimes1, 3)) + ", Probabilidade: " + formatNumber(calculateProbability(globalTime, getStateTime(stateTimes1, 3))) + " %");
        System.out.println("Estado 3 da fila 1: Tempo: " + formatNumber(getStateTime(stateTimes1, 4)) + ", Probabilidade: " + formatNumber(calculateProbability(globalTime, getStateTime(stateTimes1, 4))) + " %");
        System.out.println("  ");
        System.out.println("Estado 1 da fila 2: Tempo: " + formatNumber(getStateTime(stateTimes2, 1)) + ", Probabilidade: " + formatNumber(calculateProbability(globalTime, getStateTime(stateTimes2, 1))) + " %");
        System.out.println("Estado 2 da fila 2: Tempo: " + formatNumber(getStateTime(stateTimes2, 2)) + ", Probabilidade: " + formatNumber(calculateProbability(globalTime, getStateTime(stateTimes2, 2))) + " %");
        System.out.println("Estado 3 da fila 2: Tempo: " + formatNumber(getStateTime(stateTimes2, 3)) + ", Probabilidade: " + formatNumber(calculateProbability(globalTime, getStateTime(stateTimes2, 3))) + " %");
        System.out.println("Estado 4 da fila 2: Tempo: " + formatNumber(getStateTime(stateTimes2, 4)) + ", Probabilidade: " + formatNumber(calculateProbability(globalTime, getStateTime(stateTimes2, 4))) + " %");
        System.out.println(" ");
        System.out.println("Total de clientes perdidos na fila 1: " + lostClients1);
        System.out.println("Total de clientes perdidos na fila 2: " + lostClients2);
        System.out.println("=============================================================");

    }

    public void manageArrival(QueueModel model, Generator generator) {
        double randArrival = generator.getNextIntBetween(model.getInTimeStart(), model.getInTimeEnd());
        double randPassage = generator.getNextIntBetween(model.getOutTimeStart(), model.getOutTimeEnd());

        if (queue1Controller < model.getClients()) {
            queue1Controller++;
            if (queue1Controller <= model.getServers()) {
                actualTime = randPassage + globalTime;
                Event event = new Event(Event.Type.PASSAGE, actualTime);
                queue.add(event);
            }
            actualTime = randArrival + globalTime;
            Event event = new Event(Event.Type.ARRIVAL, actualTime);
            queue.add(event);
        } else {
            lostClients1++;
        }
    }

    public void managePassage(QueueModel model1, QueueModel model2, Generator generator) {
        double randPassage = generator.getNextIntBetween(model1.getOutTimeStart(), model1.getOutTimeEnd());
        double randExit = generator.getNextIntBetween(model2.getOutTimeStart(), model2.getOutTimeEnd());
        queue1Controller--;
        if (queue1Controller >= model1.getServers()) {
            actualTime = randPassage + globalTime;
            Event event = new Event(Event.Type.PASSAGE, actualTime);
            queue.add(event);
        }
        if(queue2Controller < model2.getClients()) {
            queue2Controller++;
            if (queue2Controller <= model2.getServers()) {
                actualTime = randExit + globalTime;
                Event event = new Event(Event.Type.EXIT, actualTime);
                queue.add(event);
            }
        } else {
            lostClients2++;
        }

    }

    public void manageExit(QueueModel model, Generator generator) {
        double randExit = generator.getNextIntBetween(model.getOutTimeStart(), model.getOutTimeEnd());
        queue2Controller--;
        if (queue2Controller >= model.getServers()) {
            actualTime = randExit + globalTime;
            Event event = new Event(Event.Type.EXIT, actualTime);
            queue.add(event);
        }
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

}

