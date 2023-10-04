package taskone;

import javax.swing.*;
import java.awt.*;
public class Slider extends JFrame
{
    protected JButton incrLeft,incrRight,decrLeft,decrRight;
    private JLabel rightLabel,leftLabel;
    protected JSlider slider;
    protected JPanel panel;

    public Slider(){
        JFrame.setDefaultLookAndFeelDecorated(true);
        setPreferredSize(new Dimension(550,400));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        panel=new JPanel();

        slider=new JSlider(0,100,50);
        slider.setBounds(190,5,150,50);
        slider.setMinorTickSpacing(10);
        slider.setMajorTickSpacing(20);
        slider.setPaintTicks(true);
        slider.setPaintLabels(true);



        incrRight=new JButton("+");
        incrRight.setBounds(340,5,50,50);
        incrLeft=new JButton("+");
        incrLeft.setBounds(60,5,50,50);




        decrRight=new JButton("-");
        decrRight.setBounds(395,5,50,50);
        decrLeft=new JButton("-");
        decrLeft.setBounds(115, 5, 50, 50);



        rightLabel=new JLabel();
        rightLabel.setBounds(470,5,50,50);
        leftLabel=new JLabel();
        leftLabel.setBounds(25,5,50,50);

        panel.setLayout(null);
        panel.add(leftLabel);
        panel.add(incrLeft);
        panel.add(decrLeft);
        panel.add(slider);
        panel.add(incrRight);
        panel.add(decrRight);
        panel.add(rightLabel);

        add(panel);
        pack();
        setTitle("TaskOne");
        setVisible(true);

    }
    public synchronized void moveOnePositionRight()
        {
        slider.setValue(slider.getValue()+1);
        try{
            Thread.sleep(200);
        }
        catch (InterruptedException e){
            Thread.currentThread().interrupt();
        }
        }
    public synchronized void moveOnePositionLeft(){
        slider.setValue(slider.getValue()-1);
        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
    public synchronized void setRightIncrActionListener(Runnable r){
        incrRight.addActionListener(actionevent->r.run());

    }
    public synchronized void setLeftIncrActionListener(Runnable r){
        incrLeft.addActionListener(actionevent->r.run());
    }
    public synchronized void setRightDecrActionListener(Runnable r) {
        decrRight.addActionListener(actionEvent -> r.run());
    }
    public synchronized void setLeftDecrActionListener(Runnable r) {
        decrLeft.addActionListener(actionEvent -> r.run());
    }
    public void setRightLabelText(String text) {
        rightLabel.setText(text);
    }

    public void setLeftLabelText(String text) {
        leftLabel.setText(text);
    }

}
