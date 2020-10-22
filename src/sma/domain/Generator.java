package sma.domain;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public class Generator {

    private List<Double> randoms;
    private PrintWriter printWriter;

    //Aqui são informados os parametros para o cálculo da congruência linear
    public Generator(int x, int size) throws FileNotFoundException {
        this.randoms = calculateCongruentLinear(1664525.0, x, 4294967295.0, 1013904223, size);
        printWriter = new PrintWriter("randoms.txt");

    }

    public List<Double> getRandoms() {
        return randoms;
    }

    public double head() {
        double first =  randoms.get(0);
        printWriter.println("- " + first);
        return first;
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