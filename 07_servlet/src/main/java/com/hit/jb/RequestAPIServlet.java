package com.hit.jb;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public class RequestAPIServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doGet(req, resp);
        System.out.println("URI-> "+req.getRequestURI());
        System.out.println("URL->"+ req.getRequestURL());
        System.out.println("IP->"+req.getRemoteHost());//得到的是127.0.0.1<br/>
        //如果使用真实的ip地址则在访问时得到的是真实的ip地址
        //获取请求头
        System.out.println("request header->"+req.getHeader("User-Agent"));
        System.out.println("request method->"+req.getMethod());
    }
}
