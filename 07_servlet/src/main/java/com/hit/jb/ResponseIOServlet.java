package com.hit.jb;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;

public class ResponseIOServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        PrintWriter writer = resp.getWriter();//字节流，和输出流只能取一个。同时使用两个流会报错
        resp.setContentType("text/html; charset=UTF-8");
        writer.println("this is response test->"+"深圳");
        //set the response head to change the encoding of the browser
        System.out.println(resp.getCharacterEncoding());





    }
}
