package com.codejie.oa.servlet;

import com.manor.httpserver.core.RequestObject;

import javax.servlet.Servlet;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.io.PrintWriter;

/**
 * @author kanghuajie
 * @date 2021/7/26
 */
public class UserSaveServlet implements Servlet  {
    @Override
    public void service(ServletRequest request, ServletResponse response) {
        String username = request.getParameterValue("username");
        String gender = request.getParameterValue("gender");
        String[] interest = request.getParameterValues("interest");
        StringBuilder interests = new StringBuilder();
        if (interests != null && interests.length() != 0) {
            for (String interestValue: interest) {
                interests.append(interestValue).append(" ");
            }
        }
        PrintWriter out = response.getWriter();
        out.print("<html>");
        out.print("<head>");
        out.print("<title>用户信息</title>");
        out.print("</head>");
        out.print("<body>");
        out.print("用户名: " + username + "<br>");
        out.print("性别: " + gender + "<br>");
        out.print("兴趣: " + interests);
        out.print("</body>");
        out.print("</html>");
    }
}
