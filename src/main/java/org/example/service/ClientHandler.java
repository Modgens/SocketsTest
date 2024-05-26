package org.example.service;

import org.example.model.Message;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.SocketException;
import java.time.LocalDateTime;

import static java.lang.Thread.sleep;

public class ClientHandler implements Runnable {
    private Socket socket;
    private long clientId;
    private PrintWriter out;
    private MessageService messageService;

    public ClientHandler(Socket socket, MessageService messageService) {
        this.socket = socket;
        this.messageService = messageService;

    }

    @Override
    public void run() {
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream(), true);

            // Приймаємо ID від клієнта
            String idLine = in.readLine();
            try {
                clientId = Long.parseLong(idLine);
            } catch (NumberFormatException e) {
                out.println("Invalid ID format.");
                socket.close();
                return;
            }

            while (!socket.isClosed()) {
                if (!messageService.hasUndeliveredMessages(clientId)) {
                    System.out.println("There is no more messages for Client with id - " + clientId);
                    sleep(1000);
                    continue;
                }
                Message message = messageService.getUndeliveredMessageByClientId(clientId);
                out.println(message.getText());

                String response;
                if ((response = in.readLine()) != null) {
                    System.out.println("Server send message to Client" + clientId + " message - " + message.getText());
                    message.setDeliveredTime(LocalDateTime.parse(response));
                    messageService.updateMessage(message);
                }
            }
        } catch (SocketException ex) {
            System.out.println("Connection is reset!");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}