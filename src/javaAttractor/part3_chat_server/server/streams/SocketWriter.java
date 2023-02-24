package javaAttractor.part3_chat_server.server.streams;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;

public class SocketWriter {
    public static PrintWriter getWriter(Socket socket) {
        try {
            OutputStream stream = socket.getOutputStream();
            return new PrintWriter(stream);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
