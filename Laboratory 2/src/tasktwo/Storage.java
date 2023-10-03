package tasktwo;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class Storage {
    private ArrayList<Good> goods;
    private ReentrantLock lock;
    private Condition condition;
    private String name;
    private AtomicBoolean finish;
}
