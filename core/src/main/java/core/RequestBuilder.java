package core;

import java.io.*;
import java.nio.charset.StandardCharsets;

public class RequestBuilder {
    private static String body;

    public static Request buildRequest(InputStream input) throws IOException {
        String line = readLine(input);
        RequestType type = getType(line.split(" ")[0]);
        String url = line.split(" ")[1];
        if (type == RequestType.POST){
            int length = getContentLength(input);
            body = getBody(input, length);
        }
        return new Request(type, url, body);
    }

    private static String getBody(InputStream input, int length) throws IOException {
        String line = readLine(input);

        while (!line.trim().isEmpty()) {
            line = readLine(input);
        }

        return readBody(input, length);
    }

    private static String readLine(InputStream is) throws IOException {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        output.reset();
        byte data;
        byte delim = (byte) '\n';

        while ((data = (byte)is.read()) != delim) {
            output.write(data);
        }

        return output.toString();
    }

    private static String readBody(InputStream is, int length) throws IOException {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        output.reset();
        byte data;

        do {
            data = (byte)is.read();
            output.write(data);
            length--;
        } while (length > 0);

        return output.toString(StandardCharsets.UTF_8);
    }

    private static int getContentLength(InputStream input) throws IOException {
        String line;
        do {
            line = readLine(input);
        } while (!line.toLowerCase().startsWith("content-length"));

        return Integer.parseInt(line.replaceAll("[^0-9.]", ""));
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
