package sma.util;

import sma.domain.Connection;
import sma.domain.QueueModel;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

public class Reader {

    public static int countFileLines(String fileName) throws IOException {
        Path path = Paths.get(fileName);
        List<String> lines = new ArrayList<>();
        try (Scanner sc = new Scanner(Files.newBufferedReader(path, StandardCharsets.UTF_8))) {
            sc.useDelimiter("[\n]");
            while (sc.hasNext()) {
                String line = sc.nextLine();
                lines.add(line);
            }
            return lines.size();
        }
    }

    public static List<QueueModel> readFile(String fileName) throws IOException {
        Path path = Paths.get(fileName);
        List<QueueModel> queues = new ArrayList<>();
        try (Scanner sc = new Scanner(Files.newBufferedReader(path, StandardCharsets.UTF_8))) {
            sc.useDelimiter("[\n]");
            QueueModel queue = null;
            while (sc.hasNext()) {
                String line = sc.nextLine();
                if (line.contains("name")) {
                    queue = new QueueModel();
                    queue.setName(line.split(":")[1]);

                }
                if (line.contains("servers")) {
                    queue.setServers(Integer.parseInt(line.split(":")[1]));
                }
                if (line.contains("capacity")) {
                    String[] capacity = line.split(":");
                    if (capacity[1].equals("INF")) {
                        queue.setCapacity(Integer.MAX_VALUE);
                    } else {
                        queue.setCapacity(Integer.parseInt(capacity[1]));
                    }
                }
                if (line.contains("arrival")) {
                    String[] times = line.split(":")[1].split("-");
                    queue.setArrivalTimeStart(Double.parseDouble(times[0]));
                    queue.setArrivalTimeEnd(Double.parseDouble(times[1]));
                }
                if (line.contains("attendence")) {
                    String[] times = line.split(":")[1].split("-");
                    queue.setAttendenceTimeStart(Double.parseDouble(times[0]));
                    queue.setAttendenceTimeEnd(Double.parseDouble(times[1]));
                }
                if (line.contains("connections")) {
                    Queue<Connection> connections = new PriorityQueue<>();
                    String[] probabilities = line.split(":")[1].split(" ");
                    for (int i = 0; i < probabilities.length; i+=2) {
                        try {
                            connections.add(new Connection(probabilities[i], Double.parseDouble(probabilities[i + 1])));
                        } catch (ArrayIndexOutOfBoundsException e) {
                            break;
                        }
                    }
                    queue.setConnections(connections);
                }
                if (line.contains("===")) {
                    queues.add(queue);
                }
            }
        }
        return queues;
    }
}
