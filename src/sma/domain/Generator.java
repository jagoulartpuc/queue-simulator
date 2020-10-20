package sma.domain;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class Generator {

    private List<Double> randoms;

    //Aqui são informados os parametros para o cálculo da congruência linear
    public Generator(int x, int size) {
        this.randoms = calculateCongruentLinear(1664525.0, x, 4294967295.0, 1013904223, size);
//        this.randoms =  new LinkedList<>(Arrays.asList(
//                0.2176,
//                0.0103
//                , 0.1109
//                , 0.3456
//                , 0.991
//                , 0.2323
//                , 0.9211
//                , 0.0322
//                , 0.1211
//                , 0.5131
//                , 0.7208
//                , 0.9172
//                , 0.9922
//                , 0.8324
//                , 0.5011
//                , 0.2931));
    }

    public List<Double> getRandoms() {
        return randoms;
    }

    public double head() {
        return randoms.get(0);
    }

    public List<Double> calculateCongruentLinear(double a, int x, double m, double c, int size) {
        randoms = new ArrayList<>();
        double previous = x;
        double xi;
        for (int i = 0; i < size; i++) {
            xi = ((a * previous) + c) % m;
            randoms.add(xi/m);
            previous = xi;
        }
        return randoms;
    }

    public double getNext() {
        double x = head();
        randoms.remove(head());
        return x;
    }

    public double getNextBetween(double start, double end) {
        double x = (end - start) * head() + start;
        randoms.remove(head());
        return x;
    }

}