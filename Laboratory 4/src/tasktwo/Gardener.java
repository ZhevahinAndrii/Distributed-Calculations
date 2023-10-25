package tasktwo;

import tasktwo.Main.PlantState;
import static tasktwo.Main.RW_LOCK;
import static tasktwo.Main.justSleep;
public class Gardener extends Thread{
    private final PlantState[][] garden;
    public Gardener(PlantState[][] garden){
        this.garden = garden;
    }
    @Override
    public void run(){
        while(!Thread.interrupted()){
            RW_LOCK.writeLock().lock();
            for(int i=0;i<garden.length;i++){
                for(int j=0;j<garden[i].length;j++){
                    if(garden[i][j]==PlantState.DRY){
                        garden[i][j] = PlantState.WATERED;
                    }
                }
            }
            RW_LOCK.writeLock().unlock();

            justSleep(2000);
        }
    }
}
