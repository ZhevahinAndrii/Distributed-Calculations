package taskone;
import java.util.Random;
public class Bee implements Runnable{
    private static final  Random random = new Random();
    private final Pot pot;
    private final Bear bear;
    public Bee(Pot pot,Bear bear){

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
