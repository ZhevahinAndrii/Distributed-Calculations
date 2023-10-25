package taskone;

public class CustomRWLock {
    private int readers,writers;
    private int writeRequests=0;

    public synchronized void readLock(){
        while(writers>0|| writeRequests>0){
            try{
                this.wait();
            }
            catch (InterruptedException e){
                e.printStackTrace();
            }
        }
        readers++;
    }
    public synchronized void readUnlock(){
        readers--;
        this.notifyAll();
    }
    public synchronized void writeLock(){
        writeRequests++;
        while(readers>0 || writers>0){
            try{
                this.wait();
            }
            catch (InterruptedException e){
                e.printStackTrace();
            }
        }
        writeRequests--;
        writers++;

    }
    public synchronized void writeUnlock(){
        writers--;
        this.notifyAll();
    }
}
