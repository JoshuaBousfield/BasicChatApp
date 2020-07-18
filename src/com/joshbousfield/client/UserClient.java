package com.joshbousfield.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Scanner;

public class UserClient {

    private String name;

    public UserClient(String name) {
        this.name = name;
    }

    public void connectClient() {
        try (Socket socket = new Socket(InetAddress.getByName("192.168.20.38"), 5000)) {
            PrintWriter command = new PrintWriter(socket.getOutputStream(), true);
            //send the name to the server
            sendCommand(name, command);
            new Thread(new UserClientReceive(socket)).start();
            Scanner scanner = new Scanner(System.in);
            String commandString;

            while(true) {
                //will need an auto /w
                commandString = scanner.nextLine();
                sendCommand(commandString, command);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void sendCommand(String command, PrintWriter writer) {
        writer.println(command);
    }
}
