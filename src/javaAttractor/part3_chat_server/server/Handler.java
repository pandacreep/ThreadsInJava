package javaAttractor.part3_chat_server.server;

import javaAttractor.part3_chat_server.server.domain.User;
import javaAttractor.part3_chat_server.server.streams.SocketReader;
import javaAttractor.part3_chat_server.server.streams.SocketWriter;

import java.io.*;
import java.net.Socket;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class Handler {
    private final Sender sender = new Sender();
    private final Socket socket;
    private final Scanner reader;
    private final PrintWriter writer;
    private final String userName;

    public Handler(Socket socket, String userName) {
        this.socket = socket;
        this.userName = userName;
        this.reader = SocketReader.getReader(socket);
        this.writer = SocketWriter.getWriter(socket);
    }

    public void handle(List<User> users) {
        System.out.printf("Client [%s] connected: %s%n", userName, socket);

        try (socket) {
            welcomeMessages(users);
            while (true) {
                String message = reader.nextLine().strip();
                System.out.printf("Received from [%s]: %s%n", userName, message);
                if (message.equalsIgnoreCase("bye")) {
                    sender.sendToAll("[--Server--] " + userName + " left this chat.", users, userName);
                    break;
                }

                String userForPrivateMassage = userForPrivateMessageOrNull(message, users);
                if (userForPrivateMassage != null) {
                    sendPrivateMessage(users, message, userForPrivateMassage);
                    System.out.println("Private message sent to: " + userForPrivateMassage);
                } else {
                    String messageForAll = "[" + userName + "] " + message;
                    sender.sendToAll(messageForAll, users, userName);
                }
            }
        } catch (NoSuchElementException e) {
            System.out.printf("Client [%s] dropped the connection!\n", userName);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            reader.close();
            writer.close();
        }
        System.out.printf("Client [%s] disconnected: %s%n", userName, socket);
    }

    private void sendPrivateMessage(List<User> users, String message, String receiver) {
        for (User user : users) {
            if (user.getName().equalsIgnoreCase(receiver)) {
                String privateMessage = "[" + userName + " (private)] "
                            + message.substring(0, message.indexOf(" > "));
                user.getHandler().sendToOne(privateMessage);
            }
        }
    }

    private void welcomeMessages(List<User> users) throws IOException {
        sender.send("[--Server--] Hello! Your nickname is " + userName + ".", writer);
        sender.send("[--Server--] To leave the chat, pls type 'bye'.", writer);
        sender.send("[--Server--] To send private message type after it ' > ' and the receiver name.\n", writer);

        sender.sendToAll("[--Server--] " + userName + " joined this chat.", users, userName);
    }

    public void sendToOne(String message){
        writer.write(message);
        writer.write(System.lineSeparator());
        writer.flush();
    }

    private String userForPrivateMessageOrNull(String message, List<User> users) {
        int index = message.indexOf(" > ");
        if (index < 0) return null;
        String userName = message.substring(index + " > ".length());
        for (User user : users) {
            if (user.getName().equalsIgnoreCase(userName)) {
                return user.getName();
            }
        }
        return null;
    }
}
