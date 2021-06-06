package pl.roksana;


public class SnakeThread extends Thread
{
    private static String ThreadName;
    private final ProgramThreads GroupOfThreads;

    public SnakeThread(ProgramThreads Threads, int ID){
        super(Threads, "NewThread " + ID);
        ThreadName = "NewThread " + ID;
        this.GroupOfThreads = Threads;
    }

    @Override
    public void run()
    {
        while (!isInterrupted())
        {
            Runnable task = null;
            try {
                task = GroupOfThreads.getTask();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if(task == null)
                return;

            try {
                //System.out.println("Dziala" + ThreadName);
                task.run();
            }
            catch (Throwable t) {
                GroupOfThreads.uncaughtException(this, t);
            }
        }
    }
}
