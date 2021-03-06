package com.manor.httpserver.core;

import javax.servlet.Servlet;
import java.util.HashMap;
import java.util.Map;

/**
 * Servlet对象缓存池
 * @author kanghuajie
 * @date 2021/7/26
 */
public class ServletCache {
    private static Map<String, Servlet> servletMap = new HashMap<>();
    public static void put(String urlPattern, Servlet servlet) {
        servletMap.put(urlPattern, servlet);
    }

    public static Servlet get(String urlPattern) {
        return servletMap.get(urlPattern);
    }
}
