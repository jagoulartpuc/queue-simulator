package sma;

public class OutData {
    private double time;
    private int lostClients;

    public OutData(double time, int lostClients) {
        this.time = time;
        this.lostClients = lostClients;
    }

    public double getTime() {
        return time;
    }

    public void setTime(double time) {
        this.time = time;
    }

    public int getLostClients() {
        return lostClients;
    }

    public void setLostClients(int lostClients) {
        this.lostClients = lostClients;
    }

    @Override
    public String toString() {
        return "{time=" + time +
                ", lostClients=" + lostClients +
                '}';
    }
}
