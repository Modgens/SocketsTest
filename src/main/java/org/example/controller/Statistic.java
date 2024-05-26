package org.example.controller;

import org.example.service.MessageService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class Statistic extends HttpServlet {

    private MessageService service = new MessageService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("list", service.getAllMessages());
        req.getRequestDispatcher("/stat.jsp").forward(req, resp);
    }
}
