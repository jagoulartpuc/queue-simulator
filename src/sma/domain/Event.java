package sma.domain;

public class Event implements Comparable<Event> {
    private Type type;
    private double time;
    private String origin;
    private String destiny;

    public Event(Type type, double time) {
        this.type = type;
        this.time = time;
    }

    public Event(Type type, double time, String origin) {
        this.type = type;
        this.time = time;
        this.origin = origin;
    }

    public Event(Type type, double time, String origin, String destiny) {
        this.type = type;
        this.time = time;
        this.origin = origin;
        this.destiny = destiny;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public double getTime() {
        return time;
    }

    public void setTime(double time) {
        this.time = time;
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public String getDestiny() {
        return destiny;
    }

    public void setDestiny(String destiny) {
        this.destiny = destiny;
    }

    public int compareTo(Event other) {
        if (this.time < other.time) {
            return -1;
        }
        if (this.time > other.time) {
            return 1;
        }
        return 0;
    }

    public enum Type {
        ARRIVAL, EXIT, PASSAGE
    }

    @Override
    public String toString() {
        return "Event{" +
                "type=" + type +
                ", time=" + time +
                ", origin='" + origin + '\'' +
                ", destiny='" + destiny + '\'' +
                '}';
    }
}
