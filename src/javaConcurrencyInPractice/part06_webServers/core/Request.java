package javaConcurrencyInPractice.part06_webServers.core;

import java.net.Socket;
import java.time.LocalDateTime;

public class Request {
    public static final int SLEEP_TIME_MILLIS = 10_000;
    public static void handleRequest(Socket connection) {
        LocalDateTime dateTime = LocalDateTime.now();
        System.out.println(dateTime + " started handleRequest method");
        try {
            Thread.sleep(SLEEP_TIME_MILLIS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
