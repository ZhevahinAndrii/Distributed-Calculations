package tasktwo;
import java.util.Random;
public class Main {
    public static void main(String[] args){
        Random random=new Random();
        int number_of_visitors = 10;
        Thread[] visitors = new Thread[number_of_visitors];
        Barber barber =new Barber();

        for(int i =0;i<number_of_visitors;i++){
            try{
                Thread.sleep(random.nextInt(200,700));
            }
            catch(InterruptedException e){
                e.printStackTrace();
            }
            visitors[i] = new Thread(new Visitor(barber),"Visitor "+ i);
            visitors[i].start();
        }
        for (int i=0;i<number_of_visitors;i++){
            try{
                visitors[i].join();
            }
            catch (InterruptedException e){
                e.printStackTrace();
            }
        }

    }
}
