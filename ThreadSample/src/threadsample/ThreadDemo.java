package threadsample;

import java.util.concurrent.locks.ReentrantLock;

public class ThreadDemo extends Thread {
   
    private ReentrantLock lock;
    private IStringWalker stringWalker;
    private int yieldAfter;
    ThreadDemo( 
           String threadName, 
           ReentrantLock lock,
           IStringWalker stringWalker,
           int yieldAfter) 
    {
        System.out.println("Creating " +  threadName );
        this.setName(threadName);
        this.lock = lock;
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
                lock.lock();
                releaseCount = 0;
                while(releaseCount < yieldAfter && stringWalker.MoveNext())
                {
                    releaseCount++;
                    System.out.println(this.getName() + " at pos " + stringWalker.getCurrentPosition() + " is char " + Character.toString(stringWalker.getCurrentCharacter()));
                    Thread.sleep(200);
                }
                lock.unlock();
                System.out.println("Thread " +  this.getName() + " is yielding to another thread.");
            } catch (InterruptedException e) 
            {
                System.out.println("Thread " +  this.getName() + " interrupted.");
            }
      }
      System.out.println("Thread " +  this.getName() + " exiting.");
   }
}