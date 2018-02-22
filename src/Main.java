import io.ConsoleReader;
import io.ConsoleWriter;
import io.Reader;
import io.Writer;
import models.DataBase;
import models.DataBaseImpl;

public class Main{

    public static void main(String[] args) {

        Reader reader = new ConsoleReader();
        Writer writer = new ConsoleWriter();
        DataBase dataBaseImpl = new DataBaseImpl();
        Interpreter interpreter = new Interpreter(reader, writer, dataBaseImpl);
        interpreter.run();
    }

}
