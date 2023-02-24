package javaAttractor.part1_one_tread_server.server;

public class Main {
    public static void main(String[] args) {
        EchoServer.bindToPort(5000).run();
    }
}
