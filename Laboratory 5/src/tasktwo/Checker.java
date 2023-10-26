package tasktwo;
import java.util.Arrays;
public class Checker {
    private boolean isRunning =true;
    private int threadsCounter = 0;
    private final int threadsNumber;
    private final int[] threadsInfo;
    private boolean aBoolean =false;
    public Checker(int threadsNumber){
        this.threadsNumber = threadsNumber;
        threadsInfo = new int[threadsNumber];
    }
    public boolean isRunning(){
        return this.isRunning;
    }
    public void equalityCheck(){
        int equal = threadsNumber;
        Arrays.sort(threadsInfo);
        for(int i=0;i<threadsInfo.length-1;i++){
            if(threadsInfo[i]!=threadsInfo[i+1]) equal--;
        }
        if(equal>=3){
                isRunning = false;
        }
    }
    public synchronized void getInfo(int data){
        threadsInfo[threadsCounter] = data;
        threadsCounter++;
        if(threadsCounter==threadsNumber){
            notifyAll();
            aBoolean = true;
        }
        while(!aBoolean){
            try{
                wait();
            }
            catch (InterruptedException e){
                e.printStackTrace();
            }
        }
        threadsCounter--;
        if(threadsCounter==0){
            equalityCheck();
            aBoolean=false;
        }
    }
}
