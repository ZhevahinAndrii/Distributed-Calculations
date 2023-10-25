package taskone;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;


import static taskone.Main.CUSTOM_RW_LOCK;
import static taskone.Main.justSleep;
public class SearchByPhone extends Thread {
    private final String FILE_NAME;
    private final String phone;

    public SearchByPhone(String filename,String phone){
        this.FILE_NAME = filename;
        this.phone=phone;
    }
    @Override
    public void run(){
        while(!Thread.interrupted()){
            CUSTOM_RW_LOCK.readLock();
            Path path = Path.of(FILE_NAME);

            try{
                var lines = Files.readAllLines(path);
                for (var line:lines){
                    Record record = Record.fromString(line);
                    if(record.getPhoneNumber().equals(phone)){
                        System.out.println("Found full name: " +record.getFullName()+" by phone: "+record.getPhoneNumber());
                        CUSTOM_RW_LOCK.readUnlock();
                        return;
                    }
                }
            }
            catch (IOException e){
                throw new RuntimeException(e);
            }

            CUSTOM_RW_LOCK.readUnlock();
            justSleep(200);
        }
    }

}
