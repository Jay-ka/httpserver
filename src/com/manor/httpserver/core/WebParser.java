package com.manor.httpserver.core;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 解析服务器中的web.xml配置文件
 * @author manor
 * @version 1.0
 * @since 1.0
 */
public class WebParser {
    public static Map<String, Map<String, String>> servletMaps = new HashMap<>();

    /**
     * 解析服务器中所有应用的web.xml
     * @param webAppNames 服务器中所有应用的名称
     */
    public static void parser(String[] webAppNames) throws DocumentException, FileNotFoundException {
        for (String webAppName: webAppNames) {
            Map<String, String> servletMap = parser(webAppName);
            servletMaps.put(webAppName, servletMap) ;
        }
    }

    /**
     * 解析单个应用的web.xml配置文件
     * @param webAppName 应用名称
     * @return servletMap<String, String>
     */
    private static Map<String, String> parser(String webAppName) throws DocumentException, FileNotFoundException {
        String webPath = webAppName + "/WEB-INF/web.xml";
        SAXReader saxReader = new SAXReader();
        Document document = saxReader.read(new FileReader(webPath));
        List<Element> servletNodes = document.selectNodes("/web-app/servlet");
        Map<String, String> servletInfoMap = new HashMap<>();
        for (Element servletNode: servletNodes) {
            Element servletNameElt = (Element) servletNode.selectSingleNode("servlet-name");
            String servletName = servletNameElt.getStringValue();
            Element servletClassElt = (Element) servletNode.selectSingleNode("servlet-class");
            String servletClassName = servletClassElt.getStringValue();
            servletInfoMap.put(servletName, servletClassName);
        }
        List<Element> servletMappingNodes = document.selectNodes("/web-app/servlet-mapping");
        Map<String, String> servletMappingInfoMap = new HashMap<>();
        for (Element servletMappingNode: servletMappingNodes) {
            Element servletNameElt = (Element) servletMappingNode.selectSingleNode("servlet-name");
            String servletName = servletNameElt.getStringValue();
            Element servletUtlElt = (Element) servletMappingNode.selectSingleNode("url-pattern");
            String servletUtlName = servletUtlElt.getStringValue();
            servletMappingInfoMap.put(servletName, servletUtlName);
        }

        Set<String> servletNames = servletInfoMap.keySet();
        Map<String, String> servletMap = new HashMap<>();
        for (String servletName: servletNames) {
            String urlPattern = servletMappingInfoMap.get(servletName);
            String servletClass = servletInfoMap.get(servletName);
            servletMap.put(urlPattern, servletClass);
        }
        return servletMap;
    }
}
