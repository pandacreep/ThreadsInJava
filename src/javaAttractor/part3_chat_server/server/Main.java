package javaAttractor.part3_chat_server.server;

public class Main {
    public static void main(String[] args) {
        EchoServer.bindToPort(8076).run();
    }
}
