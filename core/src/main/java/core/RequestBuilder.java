package core;

import java.io.BufferedReader;
import java.io.IOException;

public class RequestBuilder {
    private static String body;

    public static Request buildRequest(BufferedReader input) throws IOException {
        String line = input.readLine();
        RequestType type = getType(line.split(" ")[0]);
        String url = line.split(" ")[1];
        StringBuilder builder;
        if (type == RequestType.POST){
            body = getBody(input);
        }
        return new Request(type, url, body);
    }

    private static String getBody(BufferedReader input) throws IOException {
        StringBuilder builder = new StringBuilder();
        int contentLength = getContentLength(input);
        String line = input.readLine();

        while (!line.trim().isEmpty()) {
            line = input.readLine();
        }

        while(line != null) {
            line = input.readLine();
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
