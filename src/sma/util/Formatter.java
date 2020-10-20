package sma.util;

import java.util.Map;

public class Formatter {

    public static double calculateProbability(double totalTime, double globalTime) {
        return (globalTime * 100) / totalTime;
    }

    public static String formatNumber(double n) {
        return String.format("%.4f", n);
    }

    public static double getStateTime(Map<Integer, Double> map, int key) {
        if (map.get(key) == null) {
            return 0.0;
        } else {
            return map.get(key);
        }
    }

    public static String getTotalTime(Map<Integer, Double> states) {
        double sum = 0;
        for (double time: states.values()) {
            sum+= time;
        }
        return formatNumber(sum);
    }
}
