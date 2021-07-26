package com.manor.httpserver.core;

import com.manor.httpserver.util.Logger;

import javax.servlet.Servlet;
import java.io.*;
import java.net.Socket;
import java.util.Map;

/**
 * 处理客户端请求
 * @author manor
 * @version 1.0
 * @since 1.0
 */
public class HandlerRequest implements Runnable{
    private Socket clientSocket;
    public HandlerRequest(Socket clientSocket) {
        this.clientSocket = clientSocket;
    }

    @Override
    public void run() {
        BufferedReader br = null;
        PrintWriter out = null;
        Logger.log("httpserver thread: " + Thread.currentThread().getName());
        try {
            br = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            out = new PrintWriter(clientSocket.getOutputStream());
            String requestLine = br.readLine();
            String requestURI = requestLine.split(" ")[1];
            if (requestURI.endsWith(".html") || requestURI.endsWith(".htm")) {
                responseStaticPage(requestURI, out);
            }else {
                String servletPath = requestURI;
                if (servletPath.contains("?")) {
                    servletPath = servletPath.split("[?]")[0];
                }
                String webAppName = servletPath.split("/")[1];
                Map<String, String> servletMap = WebParser.servletMaps.get(webAppName);
                String urlPattern = servletPath.substring(1 + webAppName.length());
                String servletClassName = servletMap.get(urlPattern);
                if (servletClassName != null) {
                    RequestObject requestObject = new RequestObject(requestURI);
                    ResponseObject responseObject = new ResponseObject();
                    responseObject.setWriter(out);
                    StringBuilder htmlheader = new StringBuilder();
                    htmlheader.append("HTTP/1.1 200 ok\n");
                    htmlheader.append("Content-Type:text/html;charset=utf-8\n\n");
                    out.print(htmlheader);
                    Servlet servlet = ServletCache.get(urlPattern);
                    if (servlet == null) {
                        Class c = Class.forName(servletClassName);
                        Object obj = c.newInstance();
                        servlet = (Servlet) obj;
                        ServletCache.put(urlPattern, servlet);
                    }
                    servlet.service(requestObject, responseObject);
                }else {//找不到该业务处理类
                    StringBuilder html = new StringBuilder();
                    html.append("HTTP/1.1 404 NotFound\n");
                    html.append("Content-Type:text/html;charset=utf-8\n\n");
                    html.append("<html>");
                    html.append("<head>");
                    html.append("<title>404-错误</title>");
                    html.append("<meta content='text/html;charset=utf-8'></meta>");
                    html.append("</head>");
                    html.append("<body>");
                    html.append("<h1>404-Not Found</h1>");
                    html.append("</body>");
                    html.append("</html>");
                    out.print(html);
                }
            }
            out.flush();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                br.close();
                clientSocket.close();
            }catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 处理静态页面
     * @param requestURI 请求URI
     * @param out 响应流对象
     */
    private void responseStaticPage(String requestURI, PrintWriter out) {
        String htmlPath = requestURI.substring(1);
        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader(htmlPath));
            StringBuilder html = new StringBuilder();
            html.append("HTTP/1.1 200 ok\n");
            html.append("Content-Type:text/html;charset=utf-8\n\n");
            String temp = null;
            while ((temp = br.readLine()) != null) {
                html.append(temp);
            }
            out.print(html);
        } catch (FileNotFoundException e) {
            StringBuilder html = new StringBuilder();
            html.append("HTTP/1.1 404 NotFound\n");
            html.append("Content-Type:text/html;charset=utf-8\n\n");
            html.append("<html>");
            html.append("<head>");
            html.append("<title>404-错误</title>");
            html.append("<meta content='text/html;charset=utf-8'></meta>");
            html.append("</head>");
            html.append("<body>");
            html.append("<h1>404-Not Found</h1>");
            html.append("</body>");
            html.append("</html>");
            out.print(html);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
