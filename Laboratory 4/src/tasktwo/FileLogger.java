package tasktwo;

import tasktwo.Main.PlantState;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

import static tasktwo.Main.*;
public class FileLogger extends Thread{
    private final PlantState[][] garden;
    public FileLogger(PlantState[][] garden){
        this.garden = garden;
    }
    @Override
    public void run(){
        while(!Thread.interrupted()){
            RW_LOCK.readLock().lock();
            StringBuilder sb = new StringBuilder();
            for(PlantState[] plantStates:garden){
                for(PlantState plantState:plantStates){
                    sb.append(plantState.toString()).append(' ');
                }
                sb.append("\n");
            }
            sb.append("\n");
            try{
                Files.write(Path.of(FILE_LOG_NAME),sb.toString().getBytes(),StandardOpenOption.APPEND);
            }
            catch (IOException e){
                e.printStackTrace();
                RW_LOCK.readLock().unlock();
                throw new RuntimeException(e);
            }
            RW_LOCK.readLock().unlock();
            justSleep(5000);
            }
        }
    }

