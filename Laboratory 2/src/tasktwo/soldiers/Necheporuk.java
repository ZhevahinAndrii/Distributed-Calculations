package tasktwo.soldiers;
import tasktwo.Storage;
import tasktwo.Good;
public class Necheporuk implements Runnable {
    private Storage van;
    public Necheporuk(Storage van){
        this.van = van;
    }
    @Override
    public void run(){
        while(!van.isFinished() || !van.isEmpty()){
            Good good = van.get();
        }
    }
}
