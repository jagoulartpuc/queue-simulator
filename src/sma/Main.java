package sma;

import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException {

        Simulator simulator = new Simulator();
        simulator.runSimulation("model3.txt", 3);

    }
}
