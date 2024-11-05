package ua.ithillel.thread.server;

public interface Server extends AutoCloseable {
    public void start() throws Exception;
}
