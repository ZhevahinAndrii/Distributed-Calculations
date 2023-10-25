package taskone;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.FileAttribute;
import java.util.List;
import java.util.Random;
import static taskone.Writer.OperationType.DELETE;
import static taskone.Writer.OperationType.APPEND;

public class Main {
    public static final Random random = new Random();
    public static final CustomRWLock CUSTOM_RW_LOCK = new CustomRWLock();
    public static void main(String[] args) throws IOException{
        final String FILE_NAME = "C:\\Distibuted Calculations\\Laboratory 4\\src\\taskone\\database.txt";
        new PrintWriter(FILE_NAME).close();
        var records = List.of(
                new Record("John","Smith","Jr","+380975575274"),
                new Record("Alex","Adams","Johns","+380984481685"),
                new Record("Jahsey","Onfroy","Ricardo","+380675528220")
        );

        var appender = new Writer(FILE_NAME,records,APPEND);
        var searcherByPhone = new SearchByPhone(FILE_NAME,"+380975575274");
        var searcherByLastName = new SearchByLastName(FILE_NAME,"Ricardo");
        var deleter = new Writer(FILE_NAME,List.of(new Record("Alex", "Adams","Johns","+380984481685")),DELETE);


        appender.start();
        searcherByLastName.start();
        searcherByPhone.start();
        deleter.start();
    }
    public static void justSleep(long millis){
        try{
            Thread.sleep(millis);
        }
        catch (InterruptedException e){
            e.printStackTrace();
        }
    }
}
