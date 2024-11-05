package ua.ithillel.thread.server;

import java.io.IOException;

public class Servers {
    public static Server createAsyncServer(int port) throws IOException {
        return new NonBlockingServer(port);
    };

    public static Server createMulthiThreadedServer(int port) throws IOException {
        return new MultiThreadedBlockingServer(port);
    }

    public static Server createThreadPoolServer(int port, int threadCount) throws IOException {
        return new ThreadPoolBlockingServer(port, threadCount);
    }
}
