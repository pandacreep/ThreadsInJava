package javaAttractor.part3_chat_server.server.streams;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class SocketReader {
    public static Scanner getReader(Socket socket) {
        try {
            InputStream stream = socket.getInputStream();
            InputStreamReader input = new InputStreamReader(stream, StandardCharsets.UTF_8);
            return new Scanner(input);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

}
