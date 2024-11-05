package ua.ithillel.thread.counter;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class LockedCounter {
    private int count;
    private Lock lock = new ReentrantLock(true);

    // unfair
    public int incrementAndGet() {
        try {
            lock.lock();

            count++; // postfix increment

        } finally {
            lock.unlock();
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
            lock.lock();

            val = count;
        } finally {

            lock.unlock();
        }
        return val;
    }
}
