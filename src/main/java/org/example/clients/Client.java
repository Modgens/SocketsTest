package org.example.clients;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.time.LocalDateTime;
import java.util.Scanner;

import static java.lang.Thread.sleep;

public class Client {

    public static void main(String[] args) {
        new Client().startServer();
    }

    public void startServer() {
        try (Socket socket = new Socket("localhost", 1234)) {

            System.out.println("Client started...");
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);

            System.out.println("Enter your id:");
            out.println(new Scanner(System.in).nextLong());

            String serverMessage;
            try {
                sleep(2000);
                while (!socket.isClosed() && (serverMessage = in.readLine()) != null) {
                    System.out.println("Server: " + serverMessage);
                    out.println(LocalDateTime.now());
                }
            } catch (IOException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
