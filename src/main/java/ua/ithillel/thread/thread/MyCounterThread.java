package ua.ithillel.thread.thread;

public class MyCounterThread extends Thread {
    private final String name;

    public MyCounterThread(String name) {
        super(name);
        this.name = name;
    }

    @Override
    public void run() {
        try {
            for (int i = 0; i < 10_000; i++) {
                if (interrupted()) {
                    System.out.println("Thread " + name + " interrupted");
                    return;
                }

                System.out.printf("%s: %d\n", name, i);
                Thread.sleep(10);
            }
        } catch (InterruptedException e) {
            System.out.printf("%s: Interrupted\n", name);
        }
    }
}
