package taskone;
import static taskone.Main.CUSTOM_RW_LOCK;
import static taskone.Main.justSleep;
import static taskone.Writer.OperationType.DELETE;
import static taskone.Main.random;
import java.io.IOException;
import java.nio.file.StandardOpenOption;
import java.util.List;
import java.nio.file.Path;
import java.nio.file.Files;
public class Writer extends Thread{
    public enum OperationType{
        APPEND,
        DELETE
    }
    private final String FILE_NAME;
    private final OperationType type;
    private final List<Record> records;
    public Writer(String filename, List<Record> records,OperationType type){
        this.FILE_NAME = filename;
        this.records = records;
        this.type = type;
    }
    @Override
    public void run(){
        for(var record:records){
            Path path = Path.of(FILE_NAME);
            String recordString = record.toString();
            if(type==DELETE){
                while(true){
                    CUSTOM_RW_LOCK.writeLock();
                    try{
                        List<String> lines = Files.readAllLines(path);
                        boolean removed = lines.remove(recordString.substring(0,recordString.length()-1));
                        if(removed){
                            Files.write(path,lines);
                            System.out.println("Removed record: "+recordString);
                            CUSTOM_RW_LOCK.writeUnlock();
                            break;
                        }
                    }
                    catch (IOException e){
                        throw new RuntimeException(e);
                    }

                    CUSTOM_RW_LOCK.writeUnlock();
                    justSleep(random.nextInt(1500,2500));
                }
            }
            else{
                CUSTOM_RW_LOCK.writeLock();
                try{
                    Files.write(path,recordString.getBytes(), StandardOpenOption.APPEND);
                    System.out.println("Appended record: "+recordString);
                }
                catch (IOException e){
                    throw new RuntimeException(e);
                }

                CUSTOM_RW_LOCK.writeUnlock();
                justSleep(random.nextInt(1500,2500));
            }
        }
    }

}
