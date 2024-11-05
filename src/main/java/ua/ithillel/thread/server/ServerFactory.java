package ua.ithillel.thread.server;

import java.io.IOException;

public class ServerFactory {
    public Server createServer(ServerType type, int port) throws IOException {
        return switch (type) {
            case MULTI_THREADED -> new MultiThreadedBlockingServer(port);
            case SINGLE_THREADED -> new BlockingServer(port);
            case SINGLE_THREADED_ASYNC -> new NonBlockingServer(port);
            case MULTIPLE_THREADED_THREADPOOL -> new ThreadPoolBlockingServer(port, 4);
            default -> throw new IllegalArgumentException("Unsupported server type: " + type);
        };
    }
}
