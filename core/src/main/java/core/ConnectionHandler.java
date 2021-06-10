package core;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class ConnectionHandler {
    public static void handleConnection(Socket client) {
        try {

            //Receive Request
            var inputFromClient = client.getInputStream();

            //Build Request
            Request request = RequestBuilder.buildRequest(inputFromClient);

            //Create Response
            Response response = RequestHandler.handleRequest(request);

            //Send Response
            var outputToClient = client.getOutputStream();
            ResponseSender.sendResponse(outputToClient, response);

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
