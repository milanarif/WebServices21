package core;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

public class ResponseSender {
    public static void sendResponse(OutputStream outputToClient, String response) throws IOException {
        String header = "HTTP/1.1 200 OK\r\nContent-length: " + response.length() + "\r\n\r\n";
        outputToClient.write(header.getBytes(StandardCharsets.UTF_8));

        byte[] body = response.getBytes(StandardCharsets.UTF_8);
        outputToClient.write(body);

        outputToClient.flush();
    }
}
