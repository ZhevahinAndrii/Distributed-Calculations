package tasktwo;
import javax.swing.*;
import java.awt.*;
import taskone.Slider;
public class SliderSemaphore extends Slider {
    protected JButton startRight,startLeft,stopRight,stopLeft;
    protected JLabel warningLabel;
    public synchronized boolean check_right_value(){
        return slider.getValue()<90;
    }
    public synchronized boolean check_left_value(){
        return slider.getValue()>10;
    }

    public SliderSemaphore(){
        super();


        startRight= new JButton("Start");
        startRight.setBounds(340,60,70,50);

        startLeft=new JButton("Start");
        startLeft.setBounds(20,60,70,50);

        stopRight=new JButton("Stop");
        stopRight.setBounds(415,60,70,50);

        stopLeft=new JButton("Stop");
        stopLeft.setBounds(95,60,70,50);

        warningLabel= new JLabel();
        warningLabel.setBounds(190,120,150,30);

        panel.add(startLeft);
        panel.add(startRight);
        panel.add(stopLeft);
        panel.add(stopRight);
        panel.add(warningLabel);
    }

    public void setWarning(){
        warningLabel.setText("Semaphore is busy");
    }
    public void clearWarning(){
        warningLabel.setText("");
    }
    public void setStartRightActionListener(Runnable r){
        startRight.addActionListener(actionevent-> r.run());
    }
    public void setStartLeftActionListener(Runnable r){
        startLeft.addActionListener(actionevent->r.run());
    }
    public void setStopRightActionListener(Runnable r){
        stopRight.addActionListener(actionevent->r.run());
    }
    public void setStopLeftActionListener(Runnable r){
        stopLeft.addActionListener(actionevent->r.run());
    }

}
