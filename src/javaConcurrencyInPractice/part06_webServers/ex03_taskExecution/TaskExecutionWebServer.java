package javaConcurrencyInPractice.part06_webServers.ex03_taskExecution;

import javaConcurrencyInPractice.part06_webServers.core.Request;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class TaskExecutionWebServer {
    private static final int NTHREADS = 5;
    private static final int PORT = 8076;
    private static final Executor exec = Executors.newFixedThreadPool(NTHREADS);

    public static void main(String[] args) throws IOException {

        ServerSocket socket = new ServerSocket(PORT);
        while (true) {
            final Socket connection = socket.accept();
            Runnable task = new Runnable() {
                @Override
                public void run() {
                    Request.handleRequest(connection);
                }
            };
            exec.execute(task);
        }
    }
}
