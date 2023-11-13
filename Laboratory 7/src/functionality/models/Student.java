package models;

import java.io.Serializable;

public class Student implements Serializable{
    public Integer id;
    private final String firstName;
    private final String lastName;
    private final Group group;
    private final double averageMark;

    public Student(Integer id,String first_name,String last_name,double average_mark,Group group){
        this.id = id;
        this.firstName = first_name;
        this.lastName = last_name;
        this.averageMark = average_mark;
        this.group = group;
    }
    @Override
    public String toString(){
        return "Student{id="+id+",first_name='"+firstName+"',last_name='"+lastName+"'average_mark="+averageMark+",group="+group+"}";
    }
    public Integer getId(){
        return this.id;
    }
    public String getFirstName(){
        return this.firstName;
    }
    public String getLastName(){
        return this.lastName;

    }
    public Group getGroup(){
        return this.group;
    }
    public double getAverageMark(){
        return this.averageMark;
    }
}
