package ua.ithillel.thread.thread;

public class MyDaemonThread implements Runnable {
    @Override
    public void run() {
        try {
            while (true) {
                System.out.println("Checking connection with server");
                Thread.sleep(20);
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
