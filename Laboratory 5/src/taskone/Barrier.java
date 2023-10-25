package taskone;

public class Barrier {
    private final int awaitTarget;
    private int awaitCurrent=0;

    public Barrier(int awaitTarget){
        this.awaitTarget = awaitTarget;
    }
    public synchronized void await() throws InterruptedException{
        awaitCurrent++;
        if(awaitCurrent<awaitTarget){
            this.wait();
        }
        else
        {
            this.notifyAll();
            awaitCurrent=0;
        }
    }
}
