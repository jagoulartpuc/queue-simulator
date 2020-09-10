package sma;

public class Event implements Comparable<Event> {
    private Type type;
    private double time;

    public Event(Type type, double time) {
        this.type = type;
        this.time = time;
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
        ARRIVAL, EXIT
    }

    @Override
    public String toString() {
        return "{type=" + type +
                ", time=" + time +
                '}';
    }
}
