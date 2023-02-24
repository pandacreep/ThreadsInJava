package javaAttractor.part2_multi_tread_server.server;

public class Main {
    public static final int PORT = 8076;
    public static void main(String[] args) {
        EchoServer.bindToPort(PORT).run();
    }
}
