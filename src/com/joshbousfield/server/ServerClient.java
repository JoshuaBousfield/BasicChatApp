package com.joshbousfield.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class ServerClient implements Runnable {
    private String name;
    private Socket socket;
    boolean connected;

    public ServerClient(Socket socket) {
        this.socket = socket;
        connected = true;
        Scanner scanner = new Scanner(System.in);
    }

    @Override
    public void run() {
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        do {
            String command = reader.readLine();
            System.out.println(name + ": " + command);
            if (name == null) {
                name = command;
                ServerClientList.clientLogOn(this);
            } else {
                processCommand(command);
            }

        } while (connected);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                socket.close();
                connected = false;
                ServerClientList.clientLogOff(this);
            } catch (IOException e) {
                System.out.println("Error closing socket:  " + name + " " + e.getMessage());
            }
        }
    }

    /**
     * passed from the client terminal
     * a typical command is as follows
     * /[command] (username) (optional message)
     * only the [command] is required to be processed
     * at the very least. a (username) is required
     * depending on the command
     *
     * @param command gets split into a String[]
     */
    private void processCommand(String command) {
        String[] str = command.split(" ");
        switch (str[0]) {
            //add /help
            case "/w":
                if (str[1] != null && str[2] != null) {
                    ServerClient endClient = ServerClientList.findUser(str[1]);
                    StringBuilder message = new StringBuilder();
                    for (int i=2; i<str.length; i++) {
                        message.append(str[i]);
                    }
                    if ((endClient != null)) {
                        whisperUser(endClient, message.toString());
                    }
                }
                break;
            case "/list":
                ServerClientList.printUsers(this);
                break;
            case "/q":
                connected = false;
                break;
            default:
                //global message
                ServerClientList.globalMessage(name + ": " + command, this);
                break;
        }

    }

    private void whisperUser(ServerClient endClient, String message) {
        //need to add whisper from
        endClient.sendClientMessage("From " + name + ": " + message);
    }

    public void sendClientMessage(String message) {
        try {
            PrintWriter writer = new PrintWriter(socket.getOutputStream(), true);
            writer.println(message);
            Thread.sleep(10);
        } catch (IOException | InterruptedException e) {
            System.out.println("sendClientMessage IOException " + name + ": " + e.getMessage());
        }
    }

    public String getName() {
        return name;
    }
}
