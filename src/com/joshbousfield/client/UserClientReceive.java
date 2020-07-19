package com.joshbousfield.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class UserClientReceive implements Runnable {
    private final Socket socket;
    private boolean running;

    public UserClientReceive(Socket socket) {
        this.socket = socket;
        this.running = true;
    }

    @Override
    public void run() {
        String receivableMessage;
        try {
            BufferedReader server = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            while (running) {
                receivableMessage = server.readLine();
                System.out.println(receivableMessage);
            }
        } catch (IOException e) {
            System.out.println("IOException in UserClientReceive: " + e.getMessage());
        }
    }

    public void stop() {
        running = false;
    }
}
