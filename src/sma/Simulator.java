package sma;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

public class Simulator {

    private Queue<Event> queue = new PriorityQueue<>();
    private int contRandoms = 0, lostClients = 0, queue1Controller = 0, queue2Controller = 0;
    private double globalTime = 2.5;
    private Map<Integer, Double> stateTimes1 = new HashMap<>();
    private Map<Integer, Double> stateTimes2 = new HashMap<>();

    private List<Double> timeList = new ArrayList<>();
    private double actualTime;

    //Metodo principal baseado no algoritmo visto em aula:
//    CHEGADA
//    contabiliza tempo
//    se FILA < 3
//      ◦ FILA++
//      ◦ se FILA <= 1
//          agenda SAIDA(T+rnd(3..6))
//      agenda CHEGADA(T+rnd(1..2))
//    SAIDA
//    contabiliza tempo
//    FILA ––
//      se FILA >= 1
//          ◦ agenda SAIDA(T+rnd(3..6))
    //Será impresso:
    //As configurações de fila, semente, Tempo da simulação, total de chegadas e saídas junto ao total de clientes perdidos.
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
                stateTimes1.put(queue1Controller, globalTime - timeList.get(timeList.size() - 1));
                timeList.add(globalTime);
                queue.remove();
            }
            else if (queue.element().getType().equals(Event.Type.PASSAGE)) {
                managePassage(model1, model2, generator);
                globalTime = queue.element().getTime();
                stateTimes1.put(queue1Controller, globalTime - timeList.get(timeList.size() - 1));
                timeList.add(globalTime);
                queue.remove();

            } else {
                manageExit(model2, generator);
                globalTime = queue.element().getTime();
                stateTimes2.put(queue2Controller, globalTime - timeList.get(timeList.size() - 1));
                timeList.add(globalTime);
                queue.remove();
            }
            contRandoms++;
        }

        System.out.println("Estado da fila 1: 0, Tempo: " + formatNumber(stateTimes1.get(0)) + ", Probabilidade: " + formatNumber(calculateProbability(globalTime, stateTimes1.get(0))) + " %");
        System.out.println("Estado da fila 1: 1, Tempo: " + formatNumber(stateTimes1.get(1)) + ", Probabilidade: " + formatNumber(calculateProbability(globalTime, stateTimes1.get(1))) + " %");
        System.out.println("Estado da fila 1: 2, Tempo: " + formatNumber(stateTimes1.get(2)) + ", Probabilidade: " + formatNumber(calculateProbability(globalTime, stateTimes1.get(2))) + " %");
        System.out.println("Estado da fila 1: 3, Tempo: " + formatNumber(stateTimes1.get(3)) + ", Probabilidade: " + formatNumber(calculateProbability(globalTime, stateTimes1.get(3))) + " %");
        System.out.println("  ");
        System.out.println("Estado da fila 2: 0, Tempo: " + formatNumber(stateTimes2.get(1)) + ", Probabilidade: " + formatNumber(calculateProbability(globalTime, stateTimes2.get(1))) + " %");
        System.out.println("Estado da fila 2: 1, Tempo: " + formatNumber(stateTimes2.get(2)) + ", Probabilidade: " + formatNumber(calculateProbability(globalTime, stateTimes2.get(2))) + " %");
        System.out.println("Estado da fila 2: 2, Tempo: " + formatNumber(stateTimes2.get(3)) + ", Probabilidade: " + formatNumber(calculateProbability(globalTime, stateTimes2.get(3))) + " %");
        System.out.println("Estado da fila 2: 3, Tempo: " + formatNumber(stateTimes2.get(4)) + ", Probabilidade: " + formatNumber(calculateProbability(globalTime, stateTimes2.get(4))) + " %");

        System.out.println("Total de clientes perdidos: " + lostClients);
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
            lostClients++;
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

}

