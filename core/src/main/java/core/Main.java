package core;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {
    public static void main(String[] args) {
        System.out.println("Server running");

        ExecutorService executorService = Executors.newCachedThreadPool();

        try {
            ServerSocket serverSocket = new ServerSocket(80);
            while (true) {
                Socket client = serverSocket.accept();
                executorService.submit(() -> ConnectionHandler.handleConnection(client));
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
}
