package javaAttractor.part1_one_tread_server.client;

public class Main {
    public static void main(String[] args) {
        EchoClient.connectTo(5000).run();
    }
}
