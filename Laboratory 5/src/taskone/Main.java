package taskone;

import java.security.SecureRandom;
import java.util.Arrays;
public class Main {
    private static final SecureRandom random = new SecureRandom();
    private static final int SIZE=150;
    private static final int PARTS = 3;
    private static final Thread[] threads = new Thread[PARTS];
    private static final int[] recruits = new int[SIZE];
    private static final Barrier barrier = new Barrier(PARTS);

    public static void main(String[] args){
        Recruits.fillFinishedArray(PARTS);
        fillArray();
        initThreads();
        System.out.println("Result:"+Arrays.toString(recruits));
    }
    private static void fillArray(){
        for(int i=0;i<SIZE;i++){
            if(random.nextBoolean())
                recruits[i]=1;
            else
                recruits[i]=0;
        }
    }
    private static void initThreads(){
        for(int i=0;i<threads.length;i++){
            if(i==0)
                threads[i]=new Thread(new Recruits(recruits,barrier,i,0,(i+1)*(SIZE/PARTS)+1));
            else if (i==threads.length-1)
                threads[i] = new Thread(new Recruits(recruits,barrier,i,(i)*(SIZE/PARTS)-1,(i+1)*(SIZE/PARTS)));
            else
                threads[i] = new Thread(new Recruits(recruits,barrier,i,(i)*(SIZE/PARTS)-1,(i+1)*(SIZE/PARTS)));
        }
        for(Thread thread:threads){
            thread.start();
        }
        for(Thread thread:threads){
            try{
                thread.join();
            }
            catch (InterruptedException e){
                e.printStackTrace();
            }
        }
    }
}
