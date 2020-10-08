package sma.domain;

public class QueueModel {

    private int inTimeStart;
    private int inTimeEnd;
    private char inDistribution;
    private char outDistribution;
    private int servers;
    private int capacity;
    private int outTimeStart;
    private int outTimeEnd;

    public QueueModel() {
    }

    public int getInTimeStart() {
        return inTimeStart;
    }

    public void setInTimeStart(int inTimeStart) {
        this.inTimeStart = inTimeStart;
    }

    public int getInTimeEnd() {
        return inTimeEnd;
    }

    public void setInTimeEnd(int inTimeEnd) {
        this.inTimeEnd = inTimeEnd;
    }

    public char getInDistribution() {
        return inDistribution;
    }

    public void setInDistribution(char inDistribution) {
        this.inDistribution = inDistribution;
    }

    public char getOutDistribution() {
        return outDistribution;
    }

    public void setOutDistribution(char outDistribution) {
        this.outDistribution = outDistribution;
    }

    public int getServers() {
        return servers;
    }

    public void setServers(int servers) {
        this.servers = servers;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public int getOutTimeStart() {
        return outTimeStart;
    }

    public void setOutTimeStart(int outTimeStart) {
        this.outTimeStart = outTimeStart;
    }

    public int getOutTimeEnd() {
        return outTimeEnd;
    }

    public void setOutTimeEnd(int outTimeEnd) {
        this.outTimeEnd = outTimeEnd;
    }

    @Override
    public String toString() {
        return "QueueModel{" +
                "inTimeStart=" + inTimeStart +
                ", inTimeEnd=" + inTimeEnd +
                ", inDistribution=" + inDistribution +
                ", outDistribution=" + outDistribution +
                ", servers=" + servers +
                ", capacity=" + capacity +
                ", outTimeStart=" + outTimeStart +
                ", outTimeEnd=" + outTimeEnd +
                '}';
    }
}
