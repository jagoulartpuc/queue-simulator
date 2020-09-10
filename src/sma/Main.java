package sma;

import java.io.IOException;

public class Main {

    //No método principal é passado o arquivo para rodar no simulador, rodando 5 vezes os dois arquivos de entrada e exibindo o tempo e
    // a perda média de clientes.
    public static void main(String[] args) throws IOException {

        Simulator simulator = new Simulator();
        double sumClients1 = 0, sumTime1 = 0, sumclients2 = 0, sumTime2 = 0;
        int[] seeds = {3,2,5,9,1};
        for (int i = 0; i < 5; i++) {
            OutData outData = simulator.runSimulation("model1.txt", seeds[i]);
            sumClients1 += outData.getLostClients();
            sumTime1 += outData.getTime();
        }

        for (int i = 0; i < 5; i++) {
            OutData outData = simulator.runSimulation("model2.txt", seeds[i]);
            sumclients2 += outData.getLostClients();
            sumTime2 += outData.getTime();
        }

        System.out.println("Perda média de clientes no modelo 1: " + sumClients1/5);
        System.out.println("Tempo médio de simulação no modelo 1: " + sumTime1/5);
        System.out.println(" ");
        System.out.println("Perda média de clientes no modelo 2: " + sumclients2/5);
        System.out.println("Tempo médio de simulação no modelo 2: " + sumTime2/5);
    }
}
