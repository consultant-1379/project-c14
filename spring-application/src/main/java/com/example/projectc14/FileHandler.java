package com.example.projectc14;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.security.SecureRandom;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class FileHandler {

    private final Random rand = new SecureRandom();

    public String saveGraph(String fileContents, String exportDirectory) throws IOException {
        String graphID = generateFileID();
        String fileName = graphID + ".txt";
        Path filePath = Paths.get(exportDirectory, fileName);
        Files.write(filePath, fileContents.getBytes(), StandardOpenOption.CREATE);
        return graphID;
    }

    public List<Double> loadGraph(String fileName, String exportDirectory) throws IOException {
        fileName += ".txt";
        Path path = Paths.get(exportDirectory, fileName);
        File file = new File(path.toString());
        List<Double> doubleList;
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String st = br.readLine();
            doubleList = Stream.of(st.split(",")).map(Double::valueOf).collect(Collectors.toList());
        }
        return doubleList;
    }

    public String generateFileID() {
        int number = rand.nextInt(900000) + 100000;
        return String.format("%06d", number);
    }
}
