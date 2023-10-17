package taskone;
import javax.print.DocFlavor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
public class Main {
    public static void main(String[] args){
        int beeNumber=5;
        Pot honey = new Pot(10);
        Bear bear = new Bear(honey);
        new Thread(bear).start();

        ExecutorService executors = Executors.newFixedThreadPool(beeNumber);
        for(int i =0;i<beeNumber;i++){
            Runnable bee = new Bee(honey,bear);
            executors.execute(bee);
        }
        executors.shutdown();
    }
}
