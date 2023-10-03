package tasktwo;
import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class Storage {
    private static Random random=new Random();
    private ArrayList<Good> goods;
    private ReentrantLock lock;
    private Condition condition;
    private String name;
    private AtomicBoolean finish;

    public Storage(String name){
        this.name = name;
        this.goods = new ArrayList<>();
        this.lock = new ReentrantLock();
        this.condition = lock.newCondition();
        this.finish = new AtomicBoolean(false);

    }
    public Storage(String name,ArrayList<Good> goods){
        this(name);
        this.goods = goods;

    }
    public void put(Good good){
        lock.lock();
        try{
            if(good!=null){
                goods.add(good);
                System.out.println(Thread.currentThread().getName()+ " puts "+good.toString()+" to "+name);
                condition.signalAll();
                lock.unlock();
                Thread.sleep(random.nextInt(100,1000));


            }
        }
        catch (InterruptedException e){
            e.printStackTrace();
        }


    }
    public void setFinish(){
        lock.lock();
        finish.set(true);
        condition.signalAll();
        lock.unlock();
    }
    public Good get(){
        lock.lock();
        Good good=null;
        try{

            while(goods.isEmpty() && !finish.get()){
                condition.await();
            }
            if(!goods.isEmpty()){

                good = goods.get(goods.size()-1);
                goods.remove(good);
                System.out.println(Thread.currentThread().getName()+ " gets " +good.toString()+" from "+name);
                lock.unlock();
                Thread.sleep(random.nextInt(100,1000));


            }
        }
        catch (InterruptedException e){
            e.printStackTrace();
        }
        finally{

            return good;
        }
    }
    public boolean isFinished(){
        return finish.get();
    }
    public boolean isEmpty(){
        return goods.isEmpty();
    }

}
