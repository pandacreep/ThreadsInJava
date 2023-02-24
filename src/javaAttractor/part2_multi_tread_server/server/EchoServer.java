package javaAttractor.part2_multi_tread_server.server;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.UnaryOperator;

public class EchoServer {
    private final int port;
    private final HashMap<String, UnaryOperator<String>> commands = new HashMap<>();
    private final ExecutorService pool = Executors.newCachedThreadPool();

    private EchoServer(int port) {
        this.port = port;
        commands.put("date", (str) -> LocalDate.now().toString());
        commands.put("time", (str) -> {
            return LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss")).toString();
        });
        commands.put("reverse" , (str) -> {
            var s = new StringBuilder(str);
            return s.reverse().toString();
        });
        commands.put("upper" , (str) -> {
            return str.toUpperCase();
        });
    }

    static EchoServer bindToPort(int port) {
        return new EchoServer(port);
    }

    public void run() {
        try (var server = new ServerSocket(port)) {
            System.out.printf("Server started at port %s.%n", port);
            while (!server.isClosed()) {
                Socket socket = server.accept();
                System.out.println("Connection was setup.");
                pool.submit( () -> handle(socket) );
            }
        } catch (IOException e) {
            var formatMsg = "Probably port %s is busy. %n";
            System.out.printf(formatMsg, port);
            e.printStackTrace();
        }
    }

    private void handle(Socket socket) {
        System.out.printf("Client connected: %s%n", socket);

        try (Scanner reader = getReader(socket); PrintWriter writer = getWriter(socket); socket) {
            sendResponse("Hi " + socket, writer);
            while (true) {
                String message = reader.nextLine();
                System.out.printf("Got: %s%n", message);
                if (isEmptyMsg(message) || isQuitMsg(message)) {
                    System.out.printf("Bye!%n");
                    return;
                }

                sendResponse(message.toUpperCase(), writer);
            }
        } catch (NoSuchElementException e) {
            System.out.println("Client dropped the connection!");
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.printf("Client disconnected: %s%n", socket);
    }

    private static PrintWriter getWriter(Socket socket) throws IOException {
        OutputStream stream = socket.getOutputStream();
        return new PrintWriter(stream);
    }
    private static Scanner getReader(Socket socket) throws IOException {
        InputStream stream = socket.getInputStream();
        InputStreamReader input = new InputStreamReader(stream,
                StandardCharsets.UTF_8);
        return new Scanner(input);
    }

    private static boolean isQuitMsg(String message) {
        return "bye".equals(message.toLowerCase());
    }

    private static boolean isEmptyMsg(String message) {
        return message == null || message.isBlank();
    }

    private static void sendResponse(String response, Writer writer) throws IOException {
        writer.write(response);
        writer.write(System.lineSeparator());
        writer.flush();
    }


    private String getResponse(String message) {
        String echo;
        String[] words = decomposeMsg(message);
        if (words != null) {
            echo = commands.get(words[0]).apply(words[1]);
        } else if (commands.containsKey(message)
                && !message.equals("reverse")
                && !message.equals("upper")) {
            echo = commands.get(message).apply("N/A");
        } else {
            echo = message;
        }
        return echo;
    }

    private static String[] decomposeMsg(String str) {
        int index = str.indexOf(" ");
        if (index == -1)
            return null;
        String[] s = {str.substring(0, index).strip(), str.substring(index).strip()};
        if (s[0].equals("reverse") || s[0].equals("upper"))
            return s;
        return null;
    }
}
