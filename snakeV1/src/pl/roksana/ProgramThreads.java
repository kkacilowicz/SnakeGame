package pl.roksana;

import java.util.LinkedList;
import java.util.List;

public class ProgramThreads extends ThreadGroup
{
    private int numberOfThreads;
    private List<Runnable> Tasks;

    public ProgramThreads(int NumberOfThreads) {
        super("ThreadPool");
        this.numberOfThreads = NumberOfThreads;
        this.Tasks = new LinkedList<Runnable>();
        startThreads();
    }
    private void startThreads(){
        for (int i = 0; i < numberOfThreads; i++) {
            new SnakeThread(this, i).start();
        }
    }
    protected synchronized Runnable getTask() throws InterruptedException
    {
        while (this.Tasks.size() == 0)
        {
            wait();                 //czekam na polecenie
        }
        return this.Tasks.remove(0);    //zdejmij z listy zadań do wykonania
    }

    public synchronized void runTask(Runnable task)
    {
        if(task != null)
        {
            Tasks.add(task);
            notify();           //obudź wolny wątek do działania
        }
    }

}
