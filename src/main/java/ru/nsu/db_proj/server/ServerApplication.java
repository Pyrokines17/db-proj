package ru.nsu.db_proj.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ServerApplication {
    private static final int PORT = 12345;
    private static final ExecutorService executor = Executors.newFixedThreadPool(10);
    private static boolean isRunning = true;

    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            System.out.println("Server started on port " + PORT);

            Runtime.getRuntime().addShutdownHook(new Thread(() -> {
                System.out.println("Shutting down server...");
                executor.shutdown();
                isRunning = false;
            }));

            while (isRunning) {
                Socket clientSocket = serverSocket.accept();
                executor.submit(new ClientThread(clientSocket));
                System.out.println("Client connected: " + clientSocket.getInetAddress() + ":" + clientSocket.getPort());
            }
        } catch (IOException e) {
            System.err.println("Error starting server: " + e.getMessage());
        }
    }
}
