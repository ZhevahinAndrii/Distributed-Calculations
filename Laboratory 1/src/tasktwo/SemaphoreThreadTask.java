package tasktwo;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
public class SemaphoreThreadTask
{
    private AtomicInteger semaphore;
    private SliderSemaphore slider;
    private Thread t1,t2;
    private Runnable right,left;
    private volatile AtomicBoolean rightRunning,leftRunning;
    public SemaphoreThreadTask(){
//        super();
        rightRunning=new AtomicBoolean(true);
        leftRunning=new AtomicBoolean(true);

        slider=new SliderSemaphore();
        semaphore=new AtomicInteger(0);
        right=()->{
            while(rightRunning.get()){
                if (slider.check_right_value()){
                    slider.moveOnePositionRight();
                }
            }
        };
        left=()->{
            while(leftRunning.get()){
                if(slider.check_left_value())
                {
                slider.moveOnePositionLeft();
                }
            }
        };
        slider.setStartLeftActionListener(this::startLeftThread);
        slider.setStartRightActionListener(this::startRightThread);
        slider.setStopLeftActionListener(this::stopLeftThread);
        slider.setStopRightActionListener(this::stopRightThread);
        slider.setLeftDecrActionListener(this::decrLeftPriority );
        slider.setLeftIncrActionListener(this::incrLeftPriority);
        slider.setRightDecrActionListener(this::decrRightPriority);
        slider.setRightIncrActionListener(this::incrRightPriority);
    }
    public synchronized void startLeftThread( ){
        if(semaphore.get()==1) {
            slider.setWarning();
            return;
        }
            if (semaphore.compareAndSet(0,1)){
               leftRunning.set(true);
               t1=new Thread(left,"Thread1");
               t1.start();
               slider.setLeftLabelText(Integer.toString(t1.getPriority()));
               slider.clearWarning();
            }
        }
        public synchronized void startRightThread(){
            if(semaphore.get()==1){
                slider.setWarning();
                return;
            }
            if(semaphore.compareAndSet(0,1)){
                rightRunning.set(true);
                t2=new Thread(right,"Thread2");
                t2.start();
                slider.setRightLabelText(Integer.toString(t2.getPriority()));
                slider.clearWarning();
            }
        }
        public synchronized void stopLeftThread(){
            slider.clearWarning();
            if(semaphore.get()==0) return;
            if(t1!=null){
                if(semaphore.compareAndSet(1,0)) {
                    leftRunning.set(false);
                    slider.setLeftLabelText("");
                    System.out.println(leftRunning);
                    t1=null;
                }
                }
            }
            public synchronized void stopRightThread(){
                slider.clearWarning();
                if(semaphore.get()==0) return;
                if(t2!=null){
                    if(semaphore.compareAndSet(1,0)) {
                        rightRunning.set(false);
                        slider.setRightLabelText("");
                        System.out.println(rightRunning);
                        t2=null;
                    }
                }
            }
            public void incrRightPriority(){
                if(t2.getPriority()<10){
                    t2.setPriority(t2.getPriority()+1);
                }
                slider.setRightLabelText(Integer.toString(t2.getPriority()));
            }
            public void incrLeftPriority(){
                if(t1.getPriority()<10){
                    t1.setPriority(t1.getPriority()+1);
                }
                slider.setLeftLabelText(Integer.toString(t1.getPriority()));
            }
            public void decrRightPriority(){
                if(t2.getPriority()>1) {
                    t2.setPriority(t2.getPriority()-1);
                }
                slider.setRightLabelText(Integer.toString(t2.getPriority()));
                }
            public void decrLeftPriority(){
                if(t1.getPriority()>1){
                    t1.setPriority(t1.getPriority()-1);
                }
                slider.setLeftLabelText(Integer.toString(t1.getPriority()));
            }
            }





