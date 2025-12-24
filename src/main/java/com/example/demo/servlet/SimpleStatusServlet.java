// src/main/java/com/example/demo/servlet/SimpleStatusServlet.java
package com.example.demo.servlet;

import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;

public class SimpleStatusServlet extends HttpServlet {

    // added-by-you: must be PUBLIC because tests call simpleStatusServlet.doGet(...)
    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        // added-by-you: plain text content type
        resp.setContentType("text/plain");

        // added-by-you: write exact text expected by tests
        try (PrintWriter writer = resp.getWriter()) {
            writer.print("Credit Card Reward Maximizer is running");
            writer.flush(); // added-by-you: test t04 checks flush()
        }
    }
}
