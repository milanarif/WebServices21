package core;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

public class ResponseSender {
    public static void sendResponse(OutputStream outputToClient, Response response) throws IOException {
        System.out.println(response.toString());
        StringBuilder responseString = new StringBuilder();

        responseString.append("HTTP/1.1 ").append(response.getCode()).append("\r\n");

        responseString.append("Content-Length: ").append(response.getContentLength()).append("\r\n");

        if (response.getContentType() != null) {
            responseString.append("Content-Type: ").append(response.getContentType()).append("\r\n\r\n");
        }

        if (response.getBody() != null) {
            responseString.append(response.getBody());
        }

        System.out.println("\n" + responseString);

        outputToClient.write(responseString.toString().getBytes(StandardCharsets.UTF_8));
        outputToClient.flush();
    }
}
