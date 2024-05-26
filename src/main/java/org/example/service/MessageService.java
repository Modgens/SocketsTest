package org.example.service;

import org.example.dao.MessageDAO;
import org.example.model.Message;

import java.util.List;

public class MessageService {
    private final MessageDAO messageDAO = new MessageDAO();

    public void saveAll(List<Message> list) {
        messageDAO.saveAll(list);
    }

    public void saveMessage(Message message) {
        messageDAO.saveMessage(message);
    }

    public void updateMessage(Message message) {
        messageDAO.updateMessage(message);
    }

    public Message getMessageById(Long id) {
        return messageDAO.getMessageById(id);
    }

    public List<Message> getAllMessages() {
        return messageDAO.getAllMessages();
    }

    public void deleteMessage(Long id) {
        messageDAO.deleteMessage(id);
    }

    public Message getUndeliveredMessageByClientId(Long id) {
        return messageDAO.getUndeliveredMessageByClientId(id);
    }
    public boolean hasUndeliveredMessages(Long clientId) {
        return messageDAO.hasUndeliveredMessages(clientId);
    }

    public int getMessageCount() {
        return messageDAO.getMessageCount();
    }
}
