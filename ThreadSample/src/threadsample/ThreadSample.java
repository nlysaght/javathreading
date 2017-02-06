package threadsample;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class ThreadSample {

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
        IStringWalker forwardWalker = new ForwardStringWalker(alphabet);
        IStringWalker reverseWalker = new ReverseStringWalker(alphabet);


        System.out.println("********************************************************************");
        System.out.println("** This sample runs 2 threads each of which walk a string.        **");
        System.out.println("** 1. Walks the string in a forward direction                     **");
        System.out.println("** 2. Walks the string in the reverse direction                   **");
        System.out.println("** Each walker yields processing to the other every X characters  **");
        System.out.println("** to demonstrate the processor locking out the other process     **");
        System.out.println("** until it has finished it's work.                               **");
        System.out.println("********************************************************************");
        System.out.println("");
        //------------------------------------------------------------------------------
        // Loop around until we get an input value between 3 & 6 inclusive.
        //------------------------------------------------------------------------------
        int yieldAfter = 0;
        while(yieldAfter < 3 || yieldAfter > 6)
        {
            System.out.print("Enter the number of characters to switch threads (3 to 6)  ?");
            String yieldValueString = br.readLine();
            if(yieldValueString.length() == 0)
                yieldValueString = "3";
            yieldAfter = Integer.parseInt(yieldValueString);
        }
        
        //------------------------------------------------------------------------------
        // Now we create a couple of threads, but only allow one thread at a time to be
        // active, each thread needs to acquire a lock on the lockController. If it can't
        // acquire the lock, it wait around this till can.
        //------------------------------------------------------------------------------
        ReentrantLock lockController = new ReentrantLock();
        Thread forwardThread = new ThreadDemo("FWD>", lockController, forwardWalker, yieldAfter);
        Thread reverseThread = new ThreadDemo("REV>", lockController, reverseWalker, yieldAfter);
        forwardThread.start();
        reverseThread.start();
        //------------------------------------------------------------------------------
        // We need to wait until both threads have completed processing.
        //------------------------------------------------------------------------------
        forwardThread.join();
        reverseThread.join();
    }
}


