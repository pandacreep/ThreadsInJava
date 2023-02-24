package javaAttractor.part3_chat_server.server.domain;


import javaAttractor.part3_chat_server.server.Handler;

import java.net.Socket;
import java.util.Random;

public class User {
    private final String name;
    private final Socket socket;
    private static int count = 0;
    private final Handler handler;

    private User (Socket socket) {
        this.socket = socket;
        String[] names = {"SkyHero", "IceKing", "Mouse", "Elf", "SuperMan", "AnimeLover"};
        this.name = names[(new Random()).nextInt(names.length)] + ++count;
        this.handler = new Handler(socket, name);
    }

    public static User createNewUser(Socket socket) {
        return new User(socket);
    }

    public String getName() {
        return name;
    }

    public Handler getHandler() {
        return handler;
    }
}
