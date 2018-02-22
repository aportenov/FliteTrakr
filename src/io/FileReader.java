package io;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

public class FileReader implements Reader {

    @Override

    public StringBuilder readLine() {
        try {
            Path path = Paths.get(getClass().getClassLoader()
                    .getResource("input.txt").toURI());
            try (Stream<String> lines = Files.lines(path);){
                StringBuilder data = new StringBuilder();
                lines.forEach(line -> data.append(line).append("\n"));
                return data;
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

        return null;
    }
}
