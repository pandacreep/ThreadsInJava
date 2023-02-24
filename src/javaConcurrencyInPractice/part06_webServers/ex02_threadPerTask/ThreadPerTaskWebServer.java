package javaConcurrencyInPractice.part06_webServers.ex02_threadPerTask;

import javaConcurrencyInPractice.part06_webServers.core.Request;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ThreadPerTaskWebServer {
    public static void main(String[] args) throws IOException {
        int port = 8076;
        ServerSocket socket = new ServerSocket(port);
        while (true) {
            final Socket connection = socket.accept();
            Runnable task = new Runnable() {
                @Override
                public void run() {
                    Request.handleRequest(connection);
                }
            };
            new Thread(task).start();
        }
    }
}
