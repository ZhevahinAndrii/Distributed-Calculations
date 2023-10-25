package tasktwo;

import tasktwo.Main.PlantState;

import static tasktwo.Main.RW_LOCK;
import static tasktwo.Main.justSleep;

public class ConsoleLogger extends Thread{
    private final PlantState[][] garden;
    public ConsoleLogger(PlantState[][] garden){
        this.garden = garden;
    }
    @Override
    public void run(){
        while(!Thread.interrupted()){
            RW_LOCK.readLock().lock();
            StringBuilder sb = new StringBuilder();
            for(PlantState[] plantStates:garden){
                for(PlantState plantState:plantStates){
                    sb.append(plantState.toString()).append(" ");
                }
                sb.append("\n");
            }
            System.out.println(sb);
            RW_LOCK.readLock().unlock();
            justSleep(4500);
        }
    }
}
