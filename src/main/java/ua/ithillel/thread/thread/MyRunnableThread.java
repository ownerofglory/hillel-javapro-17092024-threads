package ua.ithillel.thread.thread;

public class MyRunnableThread implements Runnable {
    private final String name;

    public MyRunnableThread(String name) {
        this.name = name;
    }

    @Override
    public void run() {
        try {
            for (int i = 0; i < 10_000; i++) {
                if (Thread.currentThread().isInterrupted()) {
                    System.out.printf("Thread %s is interrupted.%n", name);
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
