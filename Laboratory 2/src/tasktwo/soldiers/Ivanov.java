package tasktwo.soldiers;
import tasktwo.Good;
import tasktwo.Storage;

public class Ivanov implements Runnable {
    private Storage warehouse;
    private Storage outer;
    public Ivanov(Storage warehouse,Storage outer){
        this.outer = outer;
        this.warehouse = warehouse;

    }
    @Override
    public void run(){
        while(!warehouse.isEmpty()||!warehouse.isFinished()){
            Good good = warehouse.get();
            outer.put(good);
        }
        outer.setFinish();
    }
}
