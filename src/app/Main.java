package app;
import app.io.ConsoleWriter;
import app.io.FileReader;
import app.io.Reader;
import app.io.Writer;
import app.services.DataBase;
import app.services.DataBaseImpl;

public class Main{

    public static void main(String[] args) {

        Reader reader = new FileReader();
        Writer writer = new ConsoleWriter();
        DataBase dataBaseImpl = new DataBaseImpl();
        Interpreter interpreter = new Interpreter(reader, writer, dataBaseImpl);
        interpreter.run();
    }

}
