package sma;

import sma.simulator.Simulator;
import java.io.IOException;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws IOException {
        System.out.println("Digite o nome do arquivo (sem extenção): ");
        Scanner in = new Scanner(System.in);
        String name = in.nextLine();
        (new Simulator()).runSimulation(name + ".txt", 3);

    }
}
