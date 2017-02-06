package threadsample;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class ThreadSample {

    public static void main(String[] args) throws IOException {
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
        int yieldAfter = 0;
        while(yieldAfter < 3 || yieldAfter > 6)
        {
            System.out.print("Enter the number of characters to processing on (3 to 6)  ?");
            String yieldValueString = br.readLine();
            yieldAfter = Integer.parseInt(yieldValueString);
        }
        
        ReentrantLock lock = new ReentrantLock();
        Thread forwardThread = new ThreadDemo("FWD", lock, forwardWalker, yieldAfter);
        Thread reverseThread = new ThreadDemo("REV", lock, reverseWalker, yieldAfter);
        forwardThread.start();
        reverseThread.start();
    }
}


