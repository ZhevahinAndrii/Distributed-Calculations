package taskone;
import java.util.Random;
import java.util.concurrent.RecursiveAction;
import java.util.concurrent.atomic.AtomicBoolean;

public class FlockSearch extends RecursiveAction {
    public static AtomicBoolean stopFlag = new AtomicBoolean(false);
    private static boolean isFinished =false;
    public static int location_of_winnie;
    private final int startArea, finishArea, flocknumber;

    public FlockSearch(int startArea, int finishArea, int flocknumber){
        this.startArea=startArea;
        this.finishArea=finishArea;
        this.flocknumber=flocknumber;

    }
    @Override
    protected synchronized void compute(){
        System.out.println("Flock number "+this.flocknumber + " started to search");
        for (int i=this.startArea; i<this.finishArea; i++){
            if(isFinished){
                try
                {
                Thread.sleep(500);
                break;
                }
                catch (InterruptedException e){
                    e.printStackTrace();
                }

            }
            if(i == location_of_winnie){
                System.out.println("Flock number "+this.flocknumber+" founded the Winnie!");
                isFinished=true;
                return;
            }
            try{
                Thread.sleep(1000);
            }
            catch (InterruptedException e){
                e.printStackTrace();
            }
        }
        System.out.println("Flock number "+ this.flocknumber+ "has not found the Winnie!" );
    }
}
