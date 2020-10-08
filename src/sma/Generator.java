package sma;

import java.util.ArrayList;
import java.util.List;

public class Generator {

    private List<Double> randoms;
    private int counter = 0;

    //Aqui são informados os parametros para o cálculo da congruência linear
    public Generator(int x, int size) {
        this.randoms = calculateCongruentLinear(1664525.0, x, 4294967295.0, 1013904223, size);
    }

    public List<Double> getRandoms() {
        return randoms;
    }

    public List<Double> calculateCongruentLinear(double a, int x, double m, double c, int size) {
        randoms = new ArrayList<>();
        double previous = x;
        double xi;
        for (int i = 0; i < size; i++) {
//            if (randoms.isEmpty()) {
            xi = ((a * previous) + c) % m;
            randoms.add(xi/m);
            previous = xi;
//            } else {
//                double xi = ((a * randoms.get(i - 1)) + c) % m;
//                randoms.add(xi/m);
//            }
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
