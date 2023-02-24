package javaAttractor.part3_chat_server.server;


import javaAttractor.part3_chat_server.server.domain.User;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class EchoServer {
    private final int port;
    private final ExecutorService pool = Executors.newCachedThreadPool();
    private final List<User> users = new ArrayList<>();

    private EchoServer(int port) {
        this.port = port;
    }

    static EchoServer bindToPort(int port) {
        return new EchoServer(port);
    }

    public void run() {
        try (var server = new ServerSocket(port)) {
            System.out.printf("Server started. Local port %s.%n", port);
            while (!server.isClosed()) {
                Socket socket = server.accept();
                User user = User.createNewUser(socket);
                users.add(user);
                pool.submit( () -> user.getHandler().handle(users) );
            }
        } catch (IOException e) {
            System.out.printf("Probably, port %s is busy.%n", port);
        }
    }
}
