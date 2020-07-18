package com.joshbousfield.server;

import java.util.ArrayList;
import java.util.List;

public class ServerClientList {
    public final static List<ServerClient> clients = new ArrayList<>();

    public static void clientLogOn(ServerClient client) {
        if (client != null) {
            clients.add(client);
            System.out.println(client.getName() + " logged on");//make global
            globalMessage(client.getName() + " logged on", client);
        }
    }

    public static void clientLogOff(ServerClient client) {
        if (clients.remove(client)) {
            System.out.println(client.getName() + " logged off");
        }
    }

    public static ServerClient findUser(String name) {
        for (ServerClient client : clients) {
            if (client.getName().equals(name)) {
                return client;
            }
        }
        return null;
    }

    /**
     *
     * @param serverClient the message won't be passed to the user if passed
     */
    public static void globalMessage(String message, ServerClient serverClient) {
        for (ServerClient client : clients) {
            if (!client.equals(serverClient)) {
                client.sendClientMessage(message);
            }
        }
    }

    public static void printUsers(ServerClient serverClient) {
        if (serverClient != null) {
            serverClient.sendClientMessage("------active users------");
        }
        for (ServerClient client : clients) {
            if (serverClient != null) {
                System.out.println("sending message: " + client.getName());
                serverClient.sendClientMessage(client.getName());
            } else {
                System.out.println(client.getName());
            }
        }
    }
}
