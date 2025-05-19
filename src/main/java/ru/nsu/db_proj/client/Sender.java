package ru.nsu.db_proj.client;

import ru.nsu.db_proj.Message;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Sender {
    private static final String HOST = "localhost";
    private static final int PORT = 12345;

    private Socket socket;
    private BufferedReader input;
    private PrintWriter output;

    public Sender() {
        connect();
    }

    private void connect() {
        try {
            socket = new Socket(HOST, PORT);
            input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            output = new PrintWriter(socket.getOutputStream(), true);
        } catch (IOException e) {
            System.err.println("Error connecting to server: " + e.getMessage());
            System.exit(1);
        }
    }

    public Message sendRequest(Message request) {
        try {
            output.println(request.toJson());
            String response = input.readLine();

            if (response == null) {
                throw new IOException("Server closed the connection");
            }

            Message responseMessage = Message.fromJson(response);

            if (!responseMessage.getType().equals("RESPONSE")) {
                throw new IOException("Unexpected response type: " + responseMessage.getType());
            }

            return responseMessage;
        } catch (Exception e) {
            System.err.println("Error sending request: " + e.getMessage());
            return null;
        }
    }

    public void close() {
        try {
            if (input != null) input.close();
            if (output != null) output.close();
            if (socket != null) socket.close();
        } catch (IOException e) {
            System.err.println("Error closing connection: " + e.getMessage());
        }
    }
}
