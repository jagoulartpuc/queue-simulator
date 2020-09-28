package sma;

import java.io.IOException;

public class Main {

    //No método principal é passado o arquivo para rodar no simulador, rodando 5 vezes os dois arquivos de entrada e exibindo o tempo e
    // a perda média de clientes.
    public static void main(String[] args) throws IOException {

        Simulator simulator = new Simulator();
        double sumClients1 = 0, sumTime1 = 0, sumclients2 = 0, sumTime2 = 0;
        int[] seeds = {3, 2, 5, 9, 1};
        simulator.runSimulation("model3.txt", 9);

    }
}
