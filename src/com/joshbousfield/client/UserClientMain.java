package com.joshbousfield.client;


import com.joshbousfield.client.UserClient;

import java.util.Scanner;

public class UserClientMain {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Welcome to ChatApp");
        System.out.print("Enter Display Name: ");
        UserClient client = new UserClient(scanner.nextLine());
        client.connectClient();
        //add info to use
    }

}
