package com.manor.httpserver.util;

/**
 * 日志记录器
 * @author manor
 * @version 1.0
 * @since 1.0
 */
public class Logger {
    private Logger() {}

    /**
     * 普通日志记录器
     * @param msg
     */
    public static void log(String msg) {
        String currentTime = DateUtil.getCurrentTime();
        System.out.println("[INFO] " + currentTime + " " + msg);
    }
}
