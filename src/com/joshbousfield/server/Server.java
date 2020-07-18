package com.joshbousfield.server;

import com.joshbousfield.server.ServerClient;

import java.io.IOException;
import java.net.*;

public class Server implements Runnable {
    private ServerSocket serverSocket;

    //TODO workout how to find internal ip for the server socket
    public Server(String ipAddress) {
        try {
            if (ipAddress != null) {
                //192.168.20.38
                this.serverSocket = new ServerSocket(5000, 50, InetAddress.getByName("192.168.20.38"));
                System.out.println("Connected");
//                this.serverSocket = new ServerSocket(5000);
                System.out.println(serverSocket.getInetAddress());
            } else {
                this.serverSocket = new ServerSocket(5000, 1, InetAddress.getLocalHost());
            }
        } catch (IOException e) {
            System.out.println("Server Socket error: " + e.getMessage());
        }

    }

    @Override
    public void run() {
        while (true) {
            try {
                new Thread(new ServerClient(serverSocket.accept())).start();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
