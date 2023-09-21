package taskone;

public class SimpleThreadTask {
    private Slider slider;
    private Thread firstThread,secondThread;

    private Runnable right,left;

    public SimpleThreadTask(){
        slider=new Slider();
        right=()->{
            while (true){
                if(slider.slider.getValue()<90){
                    slider.moveOnePositionRight();
            }}
        };
        left=()->{
            while (true){
                if(slider.slider.getValue()>10){
                slider.moveOnePositionLeft();
            }}
        };
        slider.setLeftDecrActionListener(this::decrLeftPriority);
        slider.setLeftIncrActionListener(this::incrLeftPriority);
        slider.setRightDecrActionListener(this::decrRightPriority);
        slider.setRightIncrActionListener(this::incrRightPriority);
    }
    public void execute(){
        firstThread=new Thread(left,"thread1");
        secondThread=new Thread(right,"thread2");

        slider.setLeftLabelText(Integer.toString(firstThread.getPriority()));
        slider.setRightLabelText(Integer.toString(secondThread.getPriority()));

        firstThread.setDaemon(true);
        secondThread.setDaemon(true);
        firstThread.start();
        secondThread.start();
    }
    public void incrRightPriority(){
        if(secondThread.getPriority()<10) secondThread.setPriority(secondThread.getPriority()+1);
        slider.setRightLabelText(Integer.toString(secondThread.getPriority()));

    }
    public void incrLeftPriority(){
        if(firstThread.getPriority()<10) firstThread.setPriority(firstThread.getPriority()+1);
        slider.setLeftLabelText(Integer.toString(firstThread.getPriority()));
    }
    public void decrRightPriority(){
        if(secondThread.getPriority()>1) secondThread.setPriority(secondThread.getPriority()-1);
        slider.setRightLabelText(Integer.toString(secondThread.getPriority()));
    }
    public void decrLeftPriority(){
        if(firstThread.getPriority()>1) firstThread.setPriority(firstThread.getPriority()-1);
        slider.setLeftLabelText(Integer.toString(firstThread.getPriority()));
    }
}
