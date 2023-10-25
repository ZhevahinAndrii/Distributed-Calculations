package tasktwo;
import tasktwo.Main.PlantState;

import static tasktwo.Main.RW_LOCK;
import static tasktwo.Main.justSleep;
public class Nature extends Thread{
    private final PlantState[][] garden;
    public Nature(PlantState[][] garden){
        this.garden = garden;
    }
    @Override
    public void run(){
        while(!Thread.interrupted()){
            RW_LOCK.writeLock().lock();

            for(int i =0;i<3;i++){
                garden[getRandomRow()][getRandomColumn()] = PlantState.DRY;
            }
            RW_LOCK.writeLock().unlock();
            justSleep(450);
        }
    }
    private int getRandomRow(){
        return (int)(Math.random()*garden.length);
    }
    private int getRandomColumn(){
        return (int)(Math.random()*garden[0].length);
    }


}
