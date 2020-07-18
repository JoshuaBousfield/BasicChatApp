package com.joshbousfield.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.*;

public class ServerMain {

    public static void main(String[] args) {
        Server server = new Server(getPublicIp());
        new Thread(server).start();
    }

    private static String getPublicIp() {
        String ip = "";
        try {
            URL url = new URL("https://bot.whatismyipaddress.com/");

            BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()));
            ip = reader.readLine().trim();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(ip);
        return ip;
    }
}
