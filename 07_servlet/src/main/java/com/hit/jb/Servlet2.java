package com.hit.jb;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


import java.io.IOException;

public class Servlet2 extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String username = req.getParameter("username");
        System.out.println("The material at servlet2->"+username);
        Object key = req.getAttribute("key");
        System.out.println("servlet2: "+key);
        System.out.println("servlet2 do nothing");
    }
}
