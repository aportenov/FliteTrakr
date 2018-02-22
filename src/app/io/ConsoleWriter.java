package app.io;

public class ConsoleWriter implements Writer{

    @Override
    public void writeSingleLine(String line){
        System.out.println(line);
    }
}
