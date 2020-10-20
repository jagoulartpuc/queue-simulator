package sma.domain;

public class Connection implements Comparable<Connection> {
    private String direction;
    private double prob;

    public Connection(String direction, double prob) {
        this.direction = direction;
        this.prob = prob;
    }

    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }

    public double getProb() {
        return prob;
    }

    public void setProb(double prob) {
        this.prob = prob;
    }

    public int compareTo(Connection other) {
        return Double.compare(other.prob, this.prob);
    }

    @Override
    public String toString() {
        return "{" +
                "direction='" + direction + '\'' +
                ", prob=" + prob +
                '}';
    }
}
