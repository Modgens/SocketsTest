package org.example.dao;

import org.example.util.HibernateUtil;
import org.example.model.Message;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.List;

public class MessageDAO {

    public void saveAll(List<Message> messages) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;

        try {
            transaction = session.beginTransaction();

            for (Message message : messages) {
                session.save(message);
            }

            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        } finally {
            session.close();
        }
    }

    public void saveMessage(Message message) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.save(message);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    public void updateMessage(Message message) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.update(message);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    public Message getMessageById(Long id) {
        Transaction transaction = null;
        Message message = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            message = session.get(Message.class, id);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
        return message;
    }

    public List<Message> getAllMessages() {
        Transaction transaction = null;
        List<Message> messages = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            messages = session.createQuery("from Message", Message.class).list();
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
        return messages;
    }

    public void deleteMessage(Long id) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            Message message = session.get(Message.class, id);
            if (message != null) {
                session.delete(message);
            }
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    public int getMessageCount() {
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        try (Session session = sessionFactory.openSession()) {
            Query<Long> query = session.createQuery("select count(*) from Message", Long.class);
            Long count = query.uniqueResult();
            return count != null ? count.intValue() : 0;
        } catch (Exception ex) {
            ex.printStackTrace();
            return 0;
        }
    }

    public Message getUndeliveredMessageByClientId(Long clientId) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<Message> query = session.createQuery("from Message where client_id = :clientId and deliveredTime is null", Message.class);
            query.setParameter("clientId", clientId);
            query.setMaxResults(1);
            return query.uniqueResult();
        }
    }

    public boolean hasUndeliveredMessages(Long clientId) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<Long> countQuery = session.createQuery("select count(*) from Message where client_id = :clientId and deliveredTime is null", Long.class);
            countQuery.setParameter("clientId", clientId);
            Long count = countQuery.uniqueResult();
            return count != null && count > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
