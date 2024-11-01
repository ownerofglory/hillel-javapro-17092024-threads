package ua.ithillel.thread.senderreceiver;

import java.util.Queue;

public class Sender {
    private Queue<Message> messageQueue;

    public Sender(Queue<Message> messageQueue) {
        this.messageQueue = messageQueue;
    }

    public void sendMessage(Message message) {
        synchronized (messageQueue) {
            messageQueue.add(message);

            messageQueue.notifyAll();
        }
    }
}
