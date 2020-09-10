package sma;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

public class Reader {

    public static QueueModel readFile(String fileName) throws IOException {
        Path path = Paths.get(fileName);
        QueueModel model = new QueueModel();
        try (Scanner sc = new Scanner(Files.newBufferedReader(path, StandardCharsets.UTF_8))) {
            sc.useDelimiter("[\n]");

            while (sc.hasNext()) {
                String line = sc.nextLine();
                if (line.contains("/")) {
                    String[] kendallNotation = line.split("/");
                    model.setInDistribution(kendallNotation[0].charAt(0));
                    model.setOutDistribution(kendallNotation[1].charAt(0));
                    model.setServers(Integer.parseInt(kendallNotation[2]));
                    model.setClients(Integer.parseInt(kendallNotation[3]));
                } else {
                    String[] timeUnits = line.split(",");
                    model.setInTimeStart(Integer.parseInt(timeUnits[0]));
                    model.setInTimeEnd(Integer.parseInt(timeUnits[1]));
                    model.setOutTimeStart(Integer.parseInt(timeUnits[2]));
                    model.setOutTimeEnd(Integer.parseInt(timeUnits[3]));
                }
            }
        }

        return model;
    }
}
