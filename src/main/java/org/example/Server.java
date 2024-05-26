package org.example;

import com.github.javafaker.Faker;
import org.example.model.Message;
import org.example.service.ClientHandler;
import org.example.service.MessageService;
import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Server {
    private final int PORT = 1234;
    private final int CLIENT_COUNT = 3;
    private final MessageService messageService;

    public Server(MessageService messageService) {
        this.messageService = messageService;
    }

    public static void main(String[] args) {
        new Server(new MessageService()).startServer();
    }

    public void startServer() {

        addMessages();

        try (ServerSocket serverSocket = new ServerSocket(PORT, CLIENT_COUNT)) {
            System.out.println("Server started...");

            while (true) {
                Socket socket = serverSocket.accept();
                new Thread(new ClientHandler(socket, messageService)).start();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void addMessages() {

        if(messageService.getMessageCount()!=0)
            return;

        Random random = new Random();
        Faker faker = Faker.instance();
        List<Message> list = new ArrayList<>();

        for (int i = 0; i < 1000; i++) {
            list.add(new Message(faker.lorem().sentence(5), (long) random.nextInt(3)));
        }
        messageService.saveAll(list);
    }
}
