package ua.ithillel.thread.thread;

import java.util.concurrent.CountDownLatch;

public class MyReader implements Runnable {
    private CountDownLatch countDownLatch;
    private StringBuffer stringBuffer;

    public MyReader(CountDownLatch countDownLatch, StringBuffer stringBuffer) {
        this.countDownLatch = countDownLatch;
        this.stringBuffer = stringBuffer;
    }

    @Override
    public void run() {

    }
}
