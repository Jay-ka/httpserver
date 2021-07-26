package com.manor.httpserver.core;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

/**
 * 解析server.xml配置文件
 * @author manor
 * @version 1.0
 * @since 1.0
 */
public class ServerParser {

    /**
     * 获取服务器的端口
     * @return
     */
    public static int getPort() {
        int port = 8080;
        try {
            SAXReader saxReader = new SAXReader();
            Document document = saxReader.read("config/server.xml");
            Element connectorElt = (Element) document.selectSingleNode("//connector");
            port = Integer.parseInt(connectorElt.attributeValue("port"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return port;
    }
}
