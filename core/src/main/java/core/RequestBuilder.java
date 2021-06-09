package core;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class RequestBuilder {
    private static String body;

    public static Request buildRequest(BufferedReader input) throws IOException {
        String line = input.readLine();
        RequestType type = getType(line.split(" ")[0]);
        String url = line.split(" ")[1];
        StringBuilder builder;
        if (type == RequestType.POST){
            int length = getContentLength(input);
            body = getBody(input, length);
        }
        return new Request(type, url, body);
    }

    private static String getBody(BufferedReader input, int length) throws IOException {
        StringBuilder builder = new StringBuilder();
        String line = input.readLine();

        while (!line.trim().isEmpty()) {
            line = input.readLine();
        }

        while(length > 0) {
            length -= 2;
            line = input.readLine();
            length -= line.getBytes(StandardCharsets.UTF_8).length;
            builder.append(line);
        }
        return builder.toString();
    }

    private static int getContentLength(BufferedReader input) throws IOException {
        String line;
        do {
            line = input.readLine();
        } while (!line.toLowerCase().startsWith("content-length"));

        return Integer.parseInt(line.split(" ")[1]);
    }

    private static RequestType getType(String type) {
        RequestType requestType;

        requestType = switch (type) {
            case "GET" -> RequestType.GET;
            case "POST" -> RequestType.POST;
            case "HEAD" -> RequestType.HEAD;
            default -> throw new IllegalStateException("Unsupported Request Type: " + type);
        };
        return requestType;
    }
}
