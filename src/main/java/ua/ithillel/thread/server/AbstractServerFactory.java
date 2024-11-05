package ua.ithillel.thread.server;

public class AbstractServerFactory {
    public ServerFactory createSingleThreadedServerFactory() {
        return new SingleThrededServerFactory();
    }

    public ServerFactory createMultiThreadedServerFactory() {
        return new MultiThreadedServerFactory();
    }
}
