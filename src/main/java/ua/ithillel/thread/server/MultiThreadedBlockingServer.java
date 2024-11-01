package ua.ithillel.thread.server;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.time.LocalDateTime;

public class MultiThreadedBlockingServer implements AutoCloseable {
    private final ServerSocket serverSocket;

    public MultiThreadedBlockingServer(int port) throws IOException {
        this.serverSocket = new ServerSocket(port);
    }

    public void start() throws IOException {
        while (true) {
            Socket connection = serverSocket.accept();

            Runnable serverThred = () -> {
                try (
                        OutputStream sos = connection.getOutputStream();
                        BufferedWriter connWriter = new BufferedWriter(new OutputStreamWriter(sos));
                        connection;
                        ) {
                    String resp = String.format("HTTP/1.1 200 OK\r\n" +
                        "Content-Type: text/html\r\n\r\n" +
                        """
                                                    <html>
                                                    <body>
                                                    <h1>Hello World</h1>
                                                    <h2>%s</h2>
                                                    </body>
                                                    </html>""", LocalDateTime.now());

                    connWriter.write(resp);
                    connWriter.flush();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            };
            new Thread(serverThred).start();

        }
    }


    @Override
    public void close() throws Exception {
        this.serverSocket.close();
    }
}
