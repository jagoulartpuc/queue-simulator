package sma;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

public class Simulator {

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
    public OutData runSimulation(String fileName, int seed) throws IOException {
        double simulationStartTime = System.nanoTime() / Math.pow(10,9);
        Queue<Event> queue = new PriorityQueue<>();
        QueueModel model = Reader.readFile(fileName);
        Generator generator = new Generator(seed, 100000);
        int contRandoms = 0, lostClients = 0, queueManager = 0, totalArrivals = 0, totalExits = 0;
        System.out.println("=============================================================");
        System.out.println("Fila: " + model.getInDistribution() + "/" + model.getOutDistribution() + "/" + model.getServers() + "/" + model.getClients());
        System.out.println("Chegada: " + model.getInTimeStart() + "..." + model.getInTimeEnd());
        System.out.println("Atendimento: " + model.getOutTimeStart() + "..." + model.getOutTimeEnd());
        System.out.println("Semente: " + seed);

        //Será gerado um arquivo texto com a sequência inteira de eventos da simulação
        //Ao final, serão dois arquivos contento a sequência dos dois arquivos de entrada
        PrintWriter eventsSequence = new PrintWriter(new File(fileName + "-eventsSequence.txt"));

        while (contRandoms < generator.getRandoms().size()) {
            if (queue.isEmpty()) {
                double startTime = System.nanoTime() / Math.pow(10,9);
                if (queueManager < model.getClients()) {
                    queueManager++;
                    if (queueManager <= model.getServers()) {
                        double endtime = System.nanoTime() / Math.pow(10,9);
                        double duration =  (endtime - startTime) + generator.getNextIntBetween(model.getOutTimeStart(), model.getOutTimeEnd());
                        Event event = new Event(Event.Type.EXIT, duration);
                        queue.add(event);
                        eventsSequence.println(event);
                        totalExits++;
                    }
                    double endtime2 = System.nanoTime() / Math.pow(10,9);
                    double duration2 = (endtime2 - startTime) + generator.getNextIntBetween(model.getInTimeStart(), model.getInTimeEnd());
                    Event event = new Event(Event.Type.ARRIVAL, duration2);
                    queue.add(event);
                    eventsSequence.println(event);
                    totalArrivals++;
                } else {
                    lostClients++;
                }
            }
            if (queue.element().getType().equals(Event.Type.ARRIVAL)) {
                queue.remove();
                double startTime = System.nanoTime() / Math.pow(10,9);
                if (queueManager < model.getClients()) {
                    queueManager++;
                    if (queueManager <= model.getServers()) {
                        double endtime = System.nanoTime() / Math.pow(10,9);
                        double duration =  (endtime - startTime) + generator.getNextIntBetween(model.getOutTimeStart(), model.getOutTimeEnd());
                        Event event = new Event(Event.Type.EXIT, duration);
                        queue.add(event);
                        eventsSequence.println(event);
                        totalExits++;
                    }
                    double endtime2 = System.nanoTime() / Math.pow(10,9);
                    double duration2 = (endtime2 - startTime) + generator.getNextIntBetween(model.getInTimeStart(), model.getInTimeEnd());
                    Event event = new Event(Event.Type.ARRIVAL, duration2);
                    queue.add(event);
                    eventsSequence.println(event);
                    totalArrivals++;
                } else {
                    lostClients++;
                }
            } else {
                queue.remove();
                double startTime = System.nanoTime() / Math.pow(10, 9);
                queueManager--;
                if (queueManager >= model.getServers()) {
                    double endtime = System.nanoTime() / Math.pow(10,9);
                    double duration = (endtime - startTime) + generator.getNextIntBetween(model.getOutTimeStart(), model.getOutTimeEnd());
                    Event event = new Event(Event.Type.EXIT, duration);
                    queue.add(event);
                    eventsSequence.println(event);
                    totalExits++;
                }
            }
            contRandoms++;
        }
        double simulationTime = (System.nanoTime() / Math.pow(10,9)) - simulationStartTime;
        System.out.println("Tempo total da simulação: " + simulationTime);
        System.out.println("Total de chegadas agendadas: " + totalArrivals);
        System.out.println("Total de saídas agendadas: " + totalExits);
        System.out.println("Total de clientes perdidos: " + lostClients);
        System.out.println("=============================================================");
        System.out.println(" ");

        return new OutData(simulationTime, lostClients);

    }
}

