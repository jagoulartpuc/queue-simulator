package sma;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

public class Simulator {

    private Queue<Event> queue = new PriorityQueue<>();
    private int contRandoms = 0, lostClients = 0, queueManager = 0;
    private double globalTime = 2.5;
    private Map<Integer, Double> times = new HashMap<>();
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

        QueueModel model = Reader.readFile(fileName);
        Generator generator = new Generator(seed, 20);

        System.out.println("=============================================================");
        System.out.println("Fila: " + model.getInDistribution() + "/" + model.getOutDistribution() + "/" + model.getServers() + "/" + model.getClients());
        System.out.println("Chegada: " + model.getInTimeStart() + "..." + model.getInTimeEnd());
        System.out.println("Atendimento: " + model.getOutTimeStart() + "..." + model.getOutTimeEnd());
        System.out.println("Semente: " + seed);

        timeList.add(globalTime);
        while (contRandoms < generator.getRandoms().size()) {
            if (queue.isEmpty()) {
               manageArrival(model, generator);
               timeList.add(queue.element().getTime());
            }
            if (queue.element().getType().equals(Event.Type.ARRIVAL)) {
                manageArrival(model, generator);
                globalTime = queue.element().getTime();
                times.put(queueManager, globalTime - timeList.get(timeList.size() - 1));
                timeList.add(globalTime);
                queue.remove();
            } else {
                manageExit(model, generator);
                globalTime = queue.element().getTime();
                times.put(queueManager, globalTime - timeList.get(timeList.size() - 1));
                timeList.add(globalTime);
                queue.remove();
            }
            contRandoms++;
        }

        System.out.println("Estado da fila: 0, Tempo: " + times.get(1) + ", Probabilidade: " + calculateProbability(globalTime, times.get(1)));
        System.out.println("Global time: " + globalTime + ", " + times.get(1));
        System.out.println("Estado da fila: 1, Tempo: " + times.get(2) + ", Probabilidade: " + calculateProbability(globalTime, times.get(2)));
        System.out.println("Estado da fila: 2, Tempo: " + times.get(3) + ", Probabilidade: " + calculateProbability(globalTime, times.get(3)));
        System.out.println("Estado da fila: 3, Tempo: " + times.get(4) + ", Probabilidade: " + calculateProbability(globalTime, times.get(4)));
        System.out.println("Total de clientes perdidos: " + lostClients);
        System.out.println("=============================================================");

    }

    public void manageArrival(QueueModel model, Generator generator) {
        double randArrival = generator.getNextIntBetween(model.getInTimeStart(), model.getInTimeEnd());
        double randExit = generator.getNextIntBetween(model.getOutTimeStart(), model.getOutTimeEnd());

        if (queueManager < model.getClients()) {
            queueManager++;
            if (queueManager <= model.getServers()) {
                actualTime = randExit + globalTime;
                Event event = new Event(Event.Type.EXIT, actualTime);
                queue.add(event);
                //System.out.println("Event: " + event);
            }
            actualTime = randArrival + globalTime;
            Event event = new Event(Event.Type.ARRIVAL, actualTime);
            queue.add(event);
            //System.out.println("Event: " + event);
        } else {
            lostClients++;
        }
    }

    public void manageExit(QueueModel model, Generator generator) {
        double rand = generator.getNextIntBetween(model.getOutTimeStart(), model.getOutTimeEnd());
        queueManager--;
        if (queueManager >= model.getServers()) {
            actualTime = rand + globalTime;
            Event event = new Event(Event.Type.EXIT, actualTime);
            queue.add(event);
            //System.out.println("Event: " + event);
        }
    }

    public double calculateProbability(double totalTime, double globalTime) {
        return (globalTime * 100) / totalTime;
    }

}

