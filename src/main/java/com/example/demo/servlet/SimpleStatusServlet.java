// src/main/java/com/example/demo/servlet/SimpleStatusServlet.java
package com.example.demo.servlet;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(urlPatterns = "/simple-status")
public class SimpleStatusServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("text/plain");
        PrintWriter writer = resp.getWriter();
        writer.write("Credit Card Reward Maximizer is running");
        writer.flush();
    }
}
