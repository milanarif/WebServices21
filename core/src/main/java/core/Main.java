package core;

import spi.WeatherData;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ServiceLoader;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {
    public static void main(String[] args) {
        System.out.println("Server running");

        ExecutorService executorService = Executors.newCachedThreadPool();

        ServiceLoader<WeatherData> data = ServiceLoader.load(WeatherData.class);

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
