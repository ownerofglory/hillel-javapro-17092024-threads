package ua.ithillel.thread.counter;

import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class SemaphoreCounter {
    private int count;
    private Semaphore semaphore = new Semaphore(1);
    private Semaphore readSemaphore = new Semaphore(2);

    // unfair
    public int incrementAndGet() {
        try {
            semaphore.acquire();
//            boolean b = semaphore.tryAcquire(1000, TimeUnit.MILLISECONDS);

            count++; // postfix increment

        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            semaphore.release();
        }


        // read count
        // count + 1
        // write count

        return count;
//        return ++count; // prefix increment
    }

    public int getCount() {
        int val;
        try {
            readSemaphore.acquire();

            val = count;
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {

            readSemaphore.release();
        }
        return val;
    }
}
