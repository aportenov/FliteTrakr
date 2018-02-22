package io;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class ConsoleReader implements Reader {

    private BufferedReader br;

    @Override
    public String readLine() throws IOException {
        return this.getInstace().readLine();
    }

    private BufferedReader getInstace() {
        if(this.br == null){
            this.br = new BufferedReader(new InputStreamReader(System.in));
        }

        return br;
    }
}
