package javaAttractor.part3_chat_server.server;


import javaAttractor.part3_chat_server.server.domain.User;

import java.io.IOException;
import java.io.Writer;
import java.util.List;

public class Sender {
    public void sendToAll(String message, List<User> users, String userName) {
        for (User user : users) {
            if (user.getName().equals(userName)) continue;
            user.getHandler().sendToOne(message);
        }
    }

    public void send(String message, Writer writer) throws IOException {
        writer.write(message);
        writer.write(System.lineSeparator());
        writer.flush();
    }
}
