package tasktwo;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.concurrent.Semaphore;
public class Barber {
    private final Semaphore semaphore = new Semaphore(1);
    private final Queue<Visitor> visitors = new ConcurrentLinkedDeque<>();

    public void addVisitor(Visitor visitorRunnable){
        visitors.add(visitorRunnable);
        System.out.println(Thread.currentThread().getName()+ " went into the queue");
        cutHair();
    }
    public void cutHair(){
        try{
            semaphore.acquire();
            System.out.println(Thread.currentThread().getName()+" sat down in the chair");
            visitors.remove();
            Thread.sleep(1500);
            System.out.println(Thread.currentThread().getName()+" exited barbershop");
            semaphore.release();
        }
        catch(InterruptedException e){
            e.printStackTrace();
        }
    }
}
