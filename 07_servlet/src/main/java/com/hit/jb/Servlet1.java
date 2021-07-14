package com.hit.jb;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public class Servlet1 extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String username = req.getParameter("username");
        System.out.println("The material at servlet1->"+username);
        //make the id to the material
        req.setAttribute("key","the id of servlet1");
        //servlet2 the rout
        RequestDispatcher requestDispatcher = req.getRequestDispatcher("/WEB-INF/form.html");
        //get the route
        requestDispatcher.forward(req,resp);
    }
}
