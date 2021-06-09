package core;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

public class ResponseSender {
    public static void sendResponse(OutputStream outputToClient, Response response) throws IOException {
        StringBuilder headerString = new StringBuilder();

        headerString.append("HTTP/1.1 ").append(response.getCode()).append("\r\n");

        headerString.append("Content-Length: ").append(response.getContentLength()).append("\r\n");

        if (response.getContentType() != null) {
            headerString.append("Content-Type: ").append(response.getContentType()).append("\r\n\r\n");
        }

        outputToClient.write(headerString.toString().getBytes(StandardCharsets.UTF_8));

        if (response.getBody() != null) {
            outputToClient.write(response.getBody());
            outputToClient.flush();
        } else outputToClient.flush();

    }
}
