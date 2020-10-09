package sma;

import sma.simulator.SimpleSimulator;
import sma.simulator.TandemSimulator;
import sma.util.Reader;

import java.io.IOException;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws IOException {
        System.out.println("Digite o nome do arquivo (sem extenção): ");
        Scanner in = new Scanner(System.in);
        String name = in.nextLine();
        directToSimulator(name + ".txt");

    }

    public static void directToSimulator(String fileName) throws IOException {
        int lines = Reader.countFileLines(fileName);
        if (lines == 2) {
            SimpleSimulator simulator = new SimpleSimulator();
            simulator.runSimulation(fileName, 3);
        } else if (lines == 3) {
            TandemSimulator simulator = new TandemSimulator();
            simulator.runSimulation(fileName, 3);
        } else {
            System.out.println("TO DO");
        }
    }
}
