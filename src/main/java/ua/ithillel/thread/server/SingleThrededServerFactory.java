package ua.ithillel.thread.server;

import java.io.IOException;

public class SingleThrededServerFactory extends ServerFactory {
    @Override
    public Server createServer(ServerType type, int port) throws IOException {
        return switch (type) {
            case SINGLE_THREADED -> new BlockingServer(port);
            case MULTI_THREADED -> new NonBlockingServer(port);
            default -> throw new IllegalArgumentException("Unsupported server type: " + type);
        };
    }
}
