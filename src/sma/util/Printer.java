package sma.util;

import sma.domain.QueueModel;

import java.util.Map;
import java.util.Queue;

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

    public static void printOutputs(Map<Integer, Double> stateTimes, double globalTime) {
        System.out.println("Estado 0 da fila 1: Tempo: " + formatNumber(getStateTime(stateTimes, 0)) + ", Probabilidade: " + formatNumber(calculateProbability(globalTime, getStateTime(stateTimes, 0))) + " %");
        System.out.println("Estado 1 da fila 1: Tempo: " + formatNumber(getStateTime(stateTimes, 1)) + ", Probabilidade: " + formatNumber(calculateProbability(globalTime, getStateTime(stateTimes, 1))) + " %");
        System.out.println("Estado 2 da fila 1: Tempo: " + formatNumber(getStateTime(stateTimes, 2)) + ", Probabilidade: " + formatNumber(calculateProbability(globalTime, getStateTime(stateTimes, 2))) + " %");
        System.out.println("Estado 3 da fila 1: Tempo: " + formatNumber(getStateTime(stateTimes, 3)) + ", Probabilidade: " + formatNumber(calculateProbability(globalTime, getStateTime(stateTimes, 3))) + " %");
    }

    public static void printSimpleOutputs(Map<Integer, Double> stateTimes, double globalTime) {
        printOutputs(stateTimes, globalTime);
        System.out.println("Estado 4 da fila 1: Tempo: " + formatNumber(getStateTime(stateTimes, 4)) + ", Probabilidade: " + formatNumber(calculateProbability(globalTime, getStateTime(stateTimes, 4))) + " %");
        System.out.println("Estado 5 da fila 1: Tempo: " + formatNumber(getStateTime(stateTimes, 5)) + ", Probabilidade: " + formatNumber(calculateProbability(globalTime, getStateTime(stateTimes, 5))) + " %");

        System.out.println("Tempo total fila 1: " + getTotalTime(stateTimes));
        System.out.println(" ");

    }

    public static void printTandemOutputs(Map<Integer, Double> stateTimes1, Map<Integer, Double> stateTimes2, double globalTime, int lostClients1, int lostClients2) {
        printOutputs(stateTimes1, globalTime);
        System.out.println("Tempo total fila 1: " + getTotalTime(stateTimes1));
        System.out.println(" ");
        System.out.println("Estado 0 da fila 2: Tempo: " + formatNumber(getStateTime(stateTimes2, 0)) + ", Probabilidade: " + formatNumber(calculateProbability(globalTime, getStateTime(stateTimes2, 0))) + " %");
        System.out.println("Estado 1 da fila 2: Tempo: " + formatNumber(getStateTime(stateTimes2, 1)) + ", Probabilidade: " + formatNumber(calculateProbability(globalTime, getStateTime(stateTimes2, 1))) + " %");
        System.out.println("Estado 2 da fila 2: Tempo: " + formatNumber(getStateTime(stateTimes2, 2)) + ", Probabilidade: " + formatNumber(calculateProbability(globalTime, getStateTime(stateTimes2, 2))) + " %");
        System.out.println("Estado 3 da fila 2: Tempo: " + formatNumber(getStateTime(stateTimes2, 3)) + ", Probabilidade: " + formatNumber(calculateProbability(globalTime, getStateTime(stateTimes2, 3))) + " %");
        System.out.println("Tempo total fila 2: " + getTotalTime(stateTimes2));
        System.out.println(" ");
        System.out.println("Total de clientes perdidos na fila 1: " + lostClients1);
        System.out.println("Total de clientes perdidos na fila 2: " + lostClients2);
        System.out.println("=============================================================");
    }
}
