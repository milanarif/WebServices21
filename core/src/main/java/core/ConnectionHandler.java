package core;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class ConnectionHandler {
    public static void handleConnection(Socket client) {
        try {

            //Receive Request
            var inputFromClient = new BufferedReader(new InputStreamReader((client.getInputStream())));
            System.out.println("Input Received");

            //Build Request
            Request request = RequestBuilder.buildRequest(inputFromClient);
            System.out.println("Request Built");
            System.out.println(request.toString());

            //Create Response
            String response = RequestHandler.handleRequest(request);
            System.out.println("Response Created");
            System.out.println(response);

            //Send Response
            var outputToClient = client.getOutputStream();
            ResponseSender.sendResponse(outputToClient, response);
            System.out.println("Response Sent");

            //Close thread
            inputFromClient.close();
            outputToClient.close();
            client.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
}
