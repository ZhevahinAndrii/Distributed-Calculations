package taskone;
import java.util.Random;
public class Bee implements Runnable{
    private static Random random;
    private Pot pot;
    private Bear bear;
    public Bee(Pot pot,Bear bear){
        random = new Random();
        this.pot=pot;
        this.bear = bear;

    }
    @Override
    public void run(){
        while(true){
            try{
                Thread.sleep(random.nextInt(500,1000));
            }
            catch(Exception e){
                e.printStackTrace();
            }
            pot.addHoney();
            if(pot.isFull()){
                bear.wakeUp();
            }
        }
    }
}
