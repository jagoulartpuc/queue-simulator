package sma.util;

import sma.domain.QueueModel;
import java.util.Map;

import static sma.util.Formatter.*;

public class Printer {

    public static void printSimpleInputs(QueueModel model, int seed) {
        System.out.println("=============================================================");
        System.out.println("Fila 1: " + model.getInDistribution() + "/" + model.getOutDistribution() + "/" + model.getServers() + "/" + model.getCapacity());
        System.out.println("Chegada: " + model.getInTimeStart() + "..." + model.getInTimeEnd());
        System.out.println("Atendimento: " + model.getOutTimeStart() + "..." + model.getOutTimeEnd());
        System.out.println("Semente: " + seed);
        System.out.println(" ");
    }

    public static void printTandemInputs(QueueModel model1, QueueModel model2, int seed) {
        System.out.println("=============================================================");
        System.out.println("Fila 1: " + model1.getInDistribution() + "/" + model1.getOutDistribution() + "/" + model1.getServers() + "/" + model1.getCapacity());
        System.out.println("Fila 2: " + model2.getInDistribution() + "/" + model2.getOutDistribution() + "/" + model2.getServers() + "/" + model2.getCapacity());
        System.out.println("Chegada: " + model1.getInTimeStart() + "..." + model1.getInTimeEnd());
        System.out.println("Passagem: " + model1.getOutTimeStart() + "..." + model1.getOutTimeEnd());
        System.out.println("Atendimento: " + model2.getOutTimeStart() + "..." + model2.getOutTimeEnd());
        System.out.println("Semente: " + seed);
        System.out.println(" ");
    }

    public static void printSimpleStates(Map<Integer, Double> stateTimes, double globalTime) {
        for (int i = 0; i < stateTimes.values().size(); i++){
            System.out.println("Estado " + i + " da fila: Tempo: " + formatNumber(getStateTime(stateTimes, i)) + ", Probabilidade: " + formatNumber(calculateProbability(globalTime, getStateTime(stateTimes, i))) + " %");
        }
        System.out.println("Tempo total: " + getTotalTime(stateTimes));
    }

    public static void printTandemStates(Map<Integer, Double> stateTimes1, Map<Integer, Double> stateTimes2, double globalTime, int lostClients1, int lostClients2) {
        printSimpleStates(stateTimes1, globalTime);
        System.out.println(" ");
        printSimpleStates(stateTimes2, globalTime);
        System.out.println(" ");
        System.out.println("Total de clientes perdidos na fila 1: " + lostClients1);
        System.out.println("Total de clientes perdidos na fila 2: " + lostClients2);
        System.out.println("=============================================================");
    }
}
