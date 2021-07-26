package com.manor.httpserver.core;

import com.manor.httpserver.util.Logger;
import java.io.BufferedReader;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * httpserver程序主入口
 * @author  manor
 * @version 1.0
 * @since 1.0
 */
public class Bootstrap {
    /**
     * 主程序
     * @param args
     */
    public static void main(String[] args) {
        //程序入口
        start();
    }

    /**
     * 主程序入口
     */
    public static void start() {
        ServerSocket serverSocket = null;
        Socket clientSocket = null;
        BufferedReader br = null;
        try {
            Logger.log("httpserver start");
            long start = System.currentTimeMillis();
            String[] webAppNames = {"oa"};
            WebParser.parser(webAppNames);
            int port = ServerParser.getPort();
            Logger.log("httpserver-port: " + port);
            serverSocket = new ServerSocket(port);
            long end = System.currentTimeMillis();
            Logger.log("httpserver started: " + (end - start) + "ms");

            while (true) {
                clientSocket = serverSocket.accept();
                new Thread(new HandlerRequest(clientSocket)).start();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                serverSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
