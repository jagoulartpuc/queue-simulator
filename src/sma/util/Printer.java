package sma.util;

import sma.domain.QueueModel;

import java.util.List;
import java.util.Map;

import static sma.util.Formatter.*;

public class Printer {

    public static void printSimpleInputs(QueueModel queue, int seed) {
        System.out.println("=============================================================");
        System.out.println(queue.getName() + ": G/G/" + queue.getServers() + "/" + queue.getCapacity());
        System.out.println("Chegada: " + queue.getArrivalTimeStart() + "..." + queue.getArrivalTimeEnd());
        System.out.println("Atendimento: " + queue.getAttendenceTimeStart() + "..." + queue.getAttendenceTimeEnd());
        System.out.println("Semente: " + seed);
        System.out.println(" ");
    }

    public static void printTandemInputs(QueueModel queue1, QueueModel queue2, int seed) {
        System.out.println("=============================================================");
        System.out.println(queue1.getName() + ": G/G/" + queue1.getServers() + "/" + queue1.getCapacity());
        System.out.println(queue2.getName() + ": G/G/" + queue2.getServers() + "/" + queue2.getCapacity());
        System.out.println("Chegada: " + queue1.getArrivalTimeStart() + "..." + queue1.getArrivalTimeEnd());
        System.out.println("Passagem: " + queue1.getAttendenceTimeStart() + "..." + queue1.getArrivalTimeEnd());
        System.out.println("Atendimento: " + queue2.getAttendenceTimeStart() + "..." + queue2.getAttendenceTimeEnd());
        System.out.println("Semente: " + seed);
    }

    public static void printProbInputs(List<QueueModel> queues, int seed) {
        System.out.println("=============================================================");
        for (QueueModel queue: queues) {
            if (queue.getCapacity() == Integer.MAX_VALUE) {
                System.out.println(queue.getName() + ": G/G/" + queue.getServers());
            } else {
                System.out.println(queue.getName() + ": G/G/" + queue.getServers() + "/" + queue.getCapacity());
            }
            if (queue.getConnections() != null) {
                System.out.println("Probabilidades: " + queue.getConnections());
            }
            if (queue.getName().equals("Q1")) {
                System.out.println("Chegada: " + queue.getArrivalTimeStart() + "..." + queue.getArrivalTimeEnd());
            }
            System.out.println("Passagem: " + queue.getAttendenceTimeStart() + "..." + queue.getAttendenceTimeEnd());
        }
        System.out.println("Semente: " + seed);
    }

    public static void printOutputs(List<QueueModel> queues, double globalTime) {
        for(QueueModel queue: queues) {
            System.out.println(" ");
            printStates(queue.getName(), queue.getStates(), globalTime);
            System.out.println("Tempo total: " + getTotalTime(queue.getStates()));
            System.out.println("Total de clientes perdidos na fila " + queue.getName() + ": " + queue.getLostClients());
        }
        System.out.println(" ");
        System.out.println("=============================================================");
    }

    public static void printStates(String name, Map<Integer, Double> stateTimes, double globalTime) {
        System.out.println(name + ":");
        for (int i = 0; i < stateTimes.values().size(); i++){
            System.out.println("Estado " + i + " da fila: Tempo: " + formatNumber(getStateTime(stateTimes, i)) + ", Probabilidade: " + formatNumber(calculateProbability(globalTime, getStateTime(stateTimes, i))) + " %");
        }
    }
}
