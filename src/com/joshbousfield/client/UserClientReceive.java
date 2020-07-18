package com.joshbousfield.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class UserClientReceive implements Runnable {
    private Socket socket;

    public UserClientReceive(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        String receivableMessage;
        while(true) {
            try {
                BufferedReader server = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                receivableMessage = server.readLine();
                System.out.println(receivableMessage);
            } catch (IOException e) {
                System.out.println("IOException in  UserClientReceive: " + e.getMessage());
            }
        }
    }
}
