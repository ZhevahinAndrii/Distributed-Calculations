package task5;

import java.io.Serializable;


public class DataObject implements Serializable {
    private final int command;
    private final Object data;
    public DataObject(int command,Object data){
        this.command = command;
        this.data = data;
    }
    public int getCommand(){
        return this.command;
    }
    public Object getData(){
        return this.data;
    }
}
