package sma;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Reader {

    public static List<QueueModel> readFile(String fileName) throws IOException {
        Path path = Paths.get(fileName);
        List<QueueModel> models = new ArrayList<>();
        try (Scanner sc = new Scanner(Files.newBufferedReader(path, StandardCharsets.UTF_8))) {
            sc.useDelimiter("[\n]");
            QueueModel model;
            while (sc.hasNext()) {
                String line = sc.nextLine();
                if (line.contains("/")) {
                    model = new QueueModel();
                    String[] kendallNotation = line.split("/");
                    model.setInDistribution(kendallNotation[0].charAt(0));
                    model.setOutDistribution(kendallNotation[1].charAt(0));
                    model.setServers(Integer.parseInt(kendallNotation[2]));
                    model.setCapacity(Integer.parseInt(kendallNotation[3]));
                    models.add(model);
                } else {
                    String[] timeUnits = line.split(",");
                    models.get(0).setInTimeStart(Integer.parseInt(timeUnits[0]));
                    models.get(0).setInTimeEnd(Integer.parseInt(timeUnits[1]));
                    models.get(0).setOutTimeStart(Integer.parseInt(timeUnits[2]));
                    models.get(0).setOutTimeEnd(Integer.parseInt(timeUnits[3]));

                    models.get(1).setOutTimeStart(Integer.parseInt(timeUnits[4]));
                    models.get(1).setOutTimeEnd(Integer.parseInt(timeUnits[5]));
                }
            }
        }
        return models;
    }
}
