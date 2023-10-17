package taskone;

public class Pot {
    private final int honeyMax;
    static int curr=0;
    public Pot(int honeyMax){
        this.honeyMax = honeyMax;
    }
    public synchronized void addHoney(){
        while(isFull()){
            try{
                wait();
            }
            catch (InterruptedException e){
                e.printStackTrace();
            }
        }
        curr++;
        System.out.println("Bee brings honey to the pot.Now the size is:" + curr);
    }
    synchronized boolean isFull(){
        return honeyMax==curr;
    }
    synchronized void eatAllHoney(){
        System.out.println("Winnie have eaten all the honey");
        curr=0;
        notifyAll();
    }

}
