package models;
import java.io.Serializable;
public class Group implements Serializable{
    private int id;

    private String name;
    private String studyingProgram;
    public Group(int id,String name){
        this.id = id;
        this.name = name;
        this.studyingProgram="";
    }
    public Group(int id,String name,String studyingProgram){
        this(id,name);
        this.studyingProgram = studyingProgram;
    }
    @Override
    public String toString(){
        return "Group{name="+ name + ",id=" + id +",studying program='"+studyingProgram+"'}";
    }
    public String getName(){
        return this.name;
    }
    public String getStudyingProgram(){
        return this.studyingProgram;
    }
    public int getId() {
        return this.id;
    }
}
