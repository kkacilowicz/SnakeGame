package pl.roksana;

import java.util.LinkedList;
import java.util.List;

/// <summary>
/// Class to manage all threads
/// </summary>
public class ProgramThreads extends ThreadGroup
{
    private int numberOfThreads;
    private List<Runnable> Tasks;

    public ProgramThreads(int NumberOfThreads) {
        super("ProgramThreads");
        this.numberOfThreads = NumberOfThreads;
        this.Tasks = new LinkedList<Runnable>();
        startThreads();
    }

    /// <summary>
    /// Function to create threads waiting for tasks
    /// </summary>
    private void startThreads(){
        for (int i = 0; i < numberOfThreads; i++) {
            new SnakeThread(this, i).start();
        }
    }
    /// <summary>
    /// Function to get task from the list
    /// </summary>
    protected synchronized Runnable getTask() throws InterruptedException
    {
        while (this.Tasks.size() == 0)
        {
            wait();                 //czekam na polecenie
        }
        return this.Tasks.remove(0);    //zdejmij z listy zadań do wykonania
    }

    /// <summary>
    /// Function to notify threads to runa particular task
    /// </summary>
    public synchronized void runTask(Runnable task)
    {
        if(task != null)
        {
            Tasks.add(task);
            notify();           //obudź wolny wątek do działania
        }
    }

}
