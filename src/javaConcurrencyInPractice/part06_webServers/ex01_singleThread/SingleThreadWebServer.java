package javaConcurrencyInPractice.part06_webServers.ex01_singleThread;

import javaConcurrencyInPractice.part06_webServers.core.Request;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class SingleThreadWebServer {
    public static void main(String[] args) throws IOException {
        int port = 8076;
        ServerSocket socket = new ServerSocket(port);
        while (true) {
            Socket connection = socket.accept();
            Request.handleRequest(connection);
        }
    }
}
