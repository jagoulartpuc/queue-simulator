package sma.domain;

import java.util.HashMap;
import java.util.Map;
import java.util.Queue;

public class QueueModel {

    private String name;
    private double arrivalTimeStart;
    private double arrivalTimeEnd;
    private int servers;
    private int capacity;
    private double attendenceTimeStart;
    private double attendenceTimeEnd;
    private int counter = 0;
    private Queue<Connection> connections;
    private Map<Integer, Double> states = new HashMap<>();
    private int lostClients = 0;

    public QueueModel() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public double getArrivalTimeStart() {
        return arrivalTimeStart;
    }

    public void setArrivalTimeStart(double arrivalTimeStart) {
        this.arrivalTimeStart = arrivalTimeStart;
    }

    public double getArrivalTimeEnd() {
        return arrivalTimeEnd;
    }

    public void setArrivalTimeEnd(double arrivalTimeEnd) {
        this.arrivalTimeEnd = arrivalTimeEnd;
    }

    public double getAttendenceTimeStart() {
        return attendenceTimeStart;
    }

    public void setAttendenceTimeStart(double attendenceTimeStart) {
        this.attendenceTimeStart = attendenceTimeStart;
    }

    public double getAttendenceTimeEnd() {
        return attendenceTimeEnd;
    }

    public void setAttendenceTimeEnd(double attendenceTimeEnd) {
        this.attendenceTimeEnd = attendenceTimeEnd;
    }

    public int getCounter() {
        return counter;
    }

    public void incrementCounter() {
        this.counter++;
    }

    public void decrementCounter() {
        this.counter--;
    }

    public Queue<Connection> getConnections() {
        return connections;
    }

    public void setConnections(Queue<Connection> connections) {
        this.connections = connections;
    }

    public Map<Integer, Double> getStates() {
        return states;
    }

    public void setStates(Map<Integer, Double> states) {
        this.states = states;
    }

    public int getLostClients() {
        return lostClients;
    }

    public void incrementLostClients() {
        this.lostClients++;
    }

    @Override
    public String toString() {
        return "{" +
                "name='" + name + '\'' +
                ", arrivalTimeStart=" + arrivalTimeStart +
                ", arrivalTimeEnd=" + arrivalTimeEnd +
                ", servers=" + servers +
                ", capacity=" + capacity +
                ", attendenceTimeStart=" + attendenceTimeStart +
                ", attendenceTimeEnd=" + attendenceTimeEnd +
                ", counter=" + counter +
                ", connections=" + connections +
                ", states=" + states +
                ", lostClients=" + lostClients +
                '}';
    }
}
