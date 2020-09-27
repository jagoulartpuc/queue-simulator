package sma;

import java.util.ArrayList;
import java.util.List;

public class Generator {

    private List<Double> randoms;
    private int counter = 0;

    //Aqui são informados os parametros para o cálculo da congruência linear
    public Generator(int x, int size) {
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

    public double getNextIntBetween(int start, int end) {
        double x;
        if (counter == randoms.size()) {
            return (end - start) * randoms.get(counter-1) + start;
        } else {
            x = (end - start) * randoms.get(counter) + start;
        }
        counter++;
        return x;

    }



}
