package ua.ithillel.thread.counter;

public class Counter {
    private int count;

    // unfair
    public int incrementAndGet() {
       synchronized (this) {
           count++; // postfix increment
       }

        // read count
        // count + 1
        // write count

        return count;
//        return ++count; // prefix increment
    }

    public synchronized int getCount() {
        return count;
    }
}
