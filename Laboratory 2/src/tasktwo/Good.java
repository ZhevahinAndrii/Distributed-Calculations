package tasktwo;

public class Good
{
    private int id;
    public Good(int id)
    {
        this.id = id;
    }
    public int getId()
    {
        return this.id;
    }
    public void setId(int value){
        this.id = value;
    }
    @Override
    public String toString(){
        return "Good{id="+id+"}";
    }
}
