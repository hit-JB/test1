package hit_jb;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;

import java.io.IOException;

public class HelloServlet implements Servlet {
    public HelloServlet(){
        System.out.println("1------- construct");
    }
    @Override
    public void init(ServletConfig servletConfig) throws ServletException {
        System.out.println("2------- init");
    }

    @Override
    public ServletConfig getServletConfig() {
        return null;
    }

    @Override
    public void service(ServletRequest servletRequest, ServletResponse servletResponse) throws ServletException, IOException {
        HttpServletRequest httpServletRequest= (HttpServletRequest) servletRequest;
        String method = httpServletRequest.getMethod();
        System.out.println("3---Welcome to servlet");
    }

    @Override
    public String getServletInfo() {
        return null;
    }

    @Override
    public void destroy() {
        System.out.println("4------- destroy");
    }
}
