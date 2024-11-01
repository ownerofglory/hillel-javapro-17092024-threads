package ua.ithillel.thread.counter;

import java.util.concurrent.atomic.AtomicInteger;

public class AtomicCounter {
    private final AtomicInteger atomicInteger = new AtomicInteger(0);


    public int incrementAndGet() {
        return atomicInteger.incrementAndGet();
    }
}
