package ua.ithillel.thread.server;

import java.io.IOException;

public class MultiThreadedServerFactory extends ServerFactory {
    @Override
    public Server createServer(ServerType type, int port) throws IOException {
        return switch (type) {
            case MULTI_THREADED -> new MultiThreadedBlockingServer(port);
            case MULTIPLE_THREADED_THREADPOOL -> new ThreadPoolBlockingServer(port, 4);
            default -> throw new IllegalArgumentException("Unsupported server type: " + type);
        };
    }
}
