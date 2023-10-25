package tasktwo;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.concurrent.locks.ReentrantReadWriteLock;
public class Main {
    private static final int N=5;
    private static final int M=10;

    public static final String FILE_LOG_NAME = "C:\\Distibuted Calculations\\Laboratory 4\\src\\tasktwo\\garden_log.txt";
    public static final ReentrantReadWriteLock RW_LOCK = new ReentrantReadWriteLock();

    public enum PlantState{
        WATERED,
        DRY;
        public String toString(){
            return this==WATERED ?"W":"D";
        }

    }
    public static void main(String[] args) throws FileNotFoundException {
        new PrintWriter(FILE_LOG_NAME).close();

        PlantState[][] garden = createGarden();
        ConsoleLogger consoleLogger = new ConsoleLogger(garden);
        FileLogger fileLogger = new FileLogger(garden);

        Gardener gardener = new Gardener(garden);

        Nature nature = new Nature(garden);

        consoleLogger.start();
        fileLogger.start();

        gardener.start();
        nature.start();
    }
    public static void justSleep(int millis){
        try{
            Thread.sleep(millis);
        }
        catch (InterruptedException e){
            e.printStackTrace();
        }
    }
    private static PlantState[][] createGarden(){
        PlantState[][] garden = new PlantState[N][M];
        for(int i = 0;i<N;i++){
            for(int j=0;j<M;j++){
                garden[i][j] = PlantState.WATERED;
            }
        }
        return garden;
    }
}
