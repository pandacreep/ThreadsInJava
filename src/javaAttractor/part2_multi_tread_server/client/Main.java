package javaAttractor.part2_multi_tread_server.client;

public class Main {
    public static final int PORT = 8076;
    public static final String LOCALHOST = "127.0.0.1";
    public static void main(String[] args) {
        EchoClient.connectTo(LOCALHOST, PORT).run();
    }
}
