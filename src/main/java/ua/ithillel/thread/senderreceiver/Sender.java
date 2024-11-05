package ua.ithillel.thread.senderreceiver;

import ua.ithillel.thread.weather.model.Weather;

import java.util.Queue;
import java.util.concurrent.Exchanger;

public class Sender {
    private Queue<Message> messageQueue;
    private Exchanger<Weather> exchanger;

    public Sender(Queue<Message> messageQueue, Exchanger<Weather> exchanger) {
        this.messageQueue = messageQueue;
        this.exchanger = exchanger;
    }

    public void sendMessage(Message message) {
        synchronized (messageQueue) {
            messageQueue.add(message);

            messageQueue.notify();
        }

        try {
            Weather exchange = exchanger.exchange(null);
            System.out.printf("Weather received: %s\n", exchange);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

    }
}
