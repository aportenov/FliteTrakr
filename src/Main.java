import io.FileReader;
import io.ConsoleWriter;
import io.Reader;
import io.Writer;
import models.DataBase;
import models.DataBaseImpl;

import java.nio.file.Path;
import java.nio.file.Paths;

public class Main{

    public static void main(String[] args) {

        Reader reader = new FileReader();
        Writer writer = new ConsoleWriter();
        DataBase dataBaseImpl = new DataBaseImpl();
        Interpreter interpreter = new Interpreter(reader, writer, dataBaseImpl);
        interpreter.run();
    }

}
