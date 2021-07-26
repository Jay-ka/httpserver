package com.codejie.oa.servlet;

import javax.servlet.Servlet;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.io.PrintWriter;

/**
 * 处理登录业务的java程序，该java程序由webApp开发人员开发，由web服务器开发人员调用
 * @author webApp开发人员
 * @version 1.0
 * @since 1.0
 */
public class LoginServlet implements Servlet {

    //处理业务的核心类
    public void service(ServletRequest request, ServletResponse response) {
        System.out.println("正在验证身份，请稍等......");
        PrintWriter out = response.getWriter();
        out.print("<html>");
        out.print("<head>");
        out.print("<title>正在验证</title>");
        out.print("</head>");
        out.print("<body>");
        out.print("<h1>正在验证身份</h1>");
        out.print("</body>");
        out.print("</html>");
    }
}
