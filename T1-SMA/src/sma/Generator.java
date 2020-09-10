package sma;

import java.util.ArrayList;
import java.util.List;

public class Generator {

    private List<Double> randoms;
    private int counter;

    //Aqui são informados os parametros para o cálculo da congruência linear
    public Generator(int x, int size) {
        this.counter = counter;
        this.randoms = calculateCongruentLinear(4, x, 13, 9, size);
    }

    public List<Double> getRandoms() {
        return randoms;
    }

    public List<Double> calculateCongruentLinear(int a, int x, int m, int c, int size) {
        randoms = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            if (randoms.isEmpty()) {
                double xi = ((a * x) + c) % m;
                randoms.add(xi/m);
            } else {
                double xi = ((a * randoms.get(i - 1)) + c) % m;
                randoms.add(xi/m);
            }
        }
        return randoms;
    }

    //Método para pegar um inteiro a partir de um aleatório decimal da lista e dado um intervalo
    public int getNextIntBetween(int start, int end) {
        String u = randoms.get(counter).toString();
        if (counter > randoms.size() * 0.9) {
            counter = 1;
        }
        for(int i = u.length(); i > 0; i--) {
            if (u.charAt(i - 1) == '.') {
                break;
            }
            int rand = Integer.parseInt(String.valueOf(u.charAt(i - 1)));
            if (rand >= start && rand <= end) {
                counter++;
                return rand;
            }
        }
        counter++;
        return (start + end)/2;
    }



}
