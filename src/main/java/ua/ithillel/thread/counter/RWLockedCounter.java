package ua.ithillel.thread.counter;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class RWLockedCounter {
    private int count;
    private Lock readLock;
    private Lock writeLock;


    public RWLockedCounter() {
        ReadWriteLock rwLock = new ReentrantReadWriteLock();
        readLock = rwLock.readLock();
        writeLock = rwLock.writeLock();
    }

    // unfair
    public int incrementAndGet() {
        try {
            while (!writeLock.tryLock()) {
//            writeLock.lock();

                System.out.println("Waiting for write lock");
            }

            count++;

        } finally {
            writeLock.unlock();
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
            readLock.lock();

            val = count;
        } finally {

            readLock.unlock();
        }
        return val;
    }
}
