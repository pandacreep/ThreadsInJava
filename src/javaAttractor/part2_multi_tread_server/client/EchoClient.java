package javaAttractor.part2_multi_tread_server.client;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class EchoClient {
    private final int port;
    private final String host;

    public EchoClient(String host, int port) {
        this.port = port;
        this.host = host;
    }

    public static EchoClient connectTo(int port) {
        var localhost = "127.0.0.1";
        return new EchoClient(localhost, port);
    }

    public static EchoClient connectTo(String host, int port) {
        return new EchoClient(host, port);
    }

    public void run() {
        try (var socket = new Socket(host, port)) {
            System.out.printf("Client is connected to host %s:%s.%n", host, port);
            System.out.println("Write bye, to exit");

            var scanner = new Scanner(System.in, StandardCharsets.UTF_8);
            var output = socket.getOutputStream();
            var writer = new PrintWriter(output);

            var input = socket.getInputStream();
            var isr = new InputStreamReader(input, StandardCharsets.UTF_8);
            var scannerISR = new Scanner(isr);

            try (scanner; writer; scannerISR) {
                System.out.println(scannerISR.nextLine().strip());
                while (true) {
                    String message = scanner.nextLine();
                    writer.write(message);
                    writer.write(System.lineSeparator());
                    writer.flush();
                    if ("bye".equals(message)) {
                        return;
                    }
                    var messageReceived = scannerISR.nextLine().strip();
                    System.out.printf("Received back: %s%n", messageReceived);
                }
            }
        } catch (NoSuchElementException e) {
            System.out.println("Connection dropped!");
        } catch (IOException e) {
            var msg = "Can't connect to %s:%s!%n";
            System.out.printf(msg, host, port);
        }
    }
}
