package threadsample;

import java.util.concurrent.locks.ReentrantLock;

public class ThreadDemo extends Thread {
   
    private ReentrantLock lockController;
    private IStringWalker stringWalker;
    private int yieldAfter;
    ThreadDemo( 
           String threadName, 
           ReentrantLock lockController,
           IStringWalker stringWalker,
           int yieldAfter) 
    {
        System.out.println("Creating thread " +  threadName );
        this.setName(threadName);
        this.lockController = lockController;
        this.stringWalker = stringWalker;
        this.yieldAfter = yieldAfter;
    }
   
    public void run() {
        System.out.println("Staring thread " +  this.getName() );
        int releaseCount = 0;
        while(stringWalker.CanMove())
        {
            try 
            {
                //------------------------------------------------------------------------------
                // Lock here. If another thread has this lock, then this thread will wait
                // only one thread at a time can have acquire a lock.
                //------------------------------------------------------------------------------
                lockController.lock();
                //------------------------------------------------------------------------------
                // Now move through the string, and after X characters we release the lock and
                // allow another thread to pick it up.
                //------------------------------------------------------------------------------
                releaseCount = 0;
                while(releaseCount < yieldAfter && stringWalker.MoveNext())
                {
                    releaseCount++;
                    System.out.println(this.getName() + " at pos " + stringWalker.getCurrentPosition() + " is char " + Character.toString(stringWalker.getCurrentCharacter()));
                    Thread.sleep(200);
                }
                //------------------------------------------------------------------------------
                // Now pause to allow the user, to see the transfer of processing happening
                //------------------------------------------------------------------------------
                System.out.println("Thread " +  this.getName() + " is yielding to another thread.");
                Thread.sleep(1000);
                //------------------------------------------------------------------------------
                // Release the lock, and do a tiny pause, so that the lock scheduler can notice
                // the release & unblock the other threads, so one of them can acquire the lock.
                //------------------------------------------------------------------------------
                lockController.unlock();
                Thread.sleep(10);
            } catch (InterruptedException e) 
            {
                System.out.println("Thread " +  this.getName() + " interrupted.");
            }
      }
      System.out.println("Thread " +  this.getName() + " exiting.");
   }
}