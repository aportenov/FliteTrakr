package app.io;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class FileReader implements Reader {

    @Override
    public StringBuilder readLine() {
        String input = "";
        StringBuilder data = new StringBuilder();
        try (InputStream in = this.getClass().getResourceAsStream("/input.txt");
             BufferedReader reader = new BufferedReader(new InputStreamReader(in))) {
            while ((input = reader.readLine()) != null) {
                data.append(input).append("\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return data;
    }
}
