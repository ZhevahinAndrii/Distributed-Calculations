package taskone;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static taskone.Main.CUSTOM_RW_LOCK;
import static taskone.Main.justSleep;

public class SearchByLastName extends Thread{
    private final String FILE_NAME,lastName;

    public SearchByLastName(String filename,String lastName){
        this.FILE_NAME = filename;
        this.lastName = lastName;
    }
    @Override
    public void run(){
        while(!Thread.interrupted()){
            CUSTOM_RW_LOCK.readLock();
            Path path = Path.of(FILE_NAME);
            try{
                var lines = Files.readAllLines(path);
                for(var line:lines){
                    Record record = Record.fromString(line);
                    if(record.getPhoneNumber().equals(lastName)){
                        System.out.println("Found phone number: " + record.getPhoneNumber()+" by last name: "+record.getLastName());

                        CUSTOM_RW_LOCK.readUnlock();
                        return;
                    }
                }
            }
            catch(IOException e){
                throw new RuntimeException(e);
            }

            CUSTOM_RW_LOCK.readUnlock();
            justSleep(200);
        }
    }

}
