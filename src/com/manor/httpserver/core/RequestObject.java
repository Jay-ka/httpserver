package com.manor.httpserver.core;

import javax.servlet.ServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * @author kanghuajie
 * @date 2021/7/25
 * @version 1.0
 * @since 1.0
 */
public class RequestObject implements ServletRequest {
    public Map<String, String[]> params = new HashMap<>();

    public  RequestObject(String requestURI) {
        if (requestURI.contains("?")) {
            String[] urlAndData = requestURI.split("[?]");
            if (urlAndData.length > 1) {
                String data = urlAndData[1];
                if (data.contains("&")) {
                    String[] nameAndValues = data.split("&");
                    for (String nameAndValue: nameAndValues) {
                        String[] nameAndValueAttr = nameAndValue.split("=");
                        if (params.containsKey(nameAndValueAttr[0])) {
                            String[] values = params.get(nameAndValueAttr[0]);
                            String[] newValues = new String[values.length + 1];
                            System.arraycopy(values, 0, newValues, 0, values.length);
                            if (nameAndValueAttr.length > 1) {
                                newValues[values.length] = nameAndValueAttr[1];
                            }else {
                                newValues[values.length] = "";
                            }
                            params.put(nameAndValueAttr[0], newValues);
                        }else {
                            if (nameAndValueAttr.length > 1) {
                                params.put(nameAndValueAttr[0], new String[]{nameAndValueAttr[1]});
                            }else {
                                params.put(nameAndValueAttr[0], new String[]{""});
                            }
                        }
                    }
                }else {
                    String[] nameAndValue = data.split("=");
                    if (nameAndValue.length > 1) {
                        params.put(nameAndValue[0], new String[]{nameAndValue[1]});
                    }else {
                        params.put(nameAndValue[0], new String[]{""});
                    }
                }
            }
        }
    }

    public String getParameterValue(String key) {
        String[] value = params.get(key);
        return (value != null && value.length != 0) ? value[0] : null;
    }

    public String[] getParameterValues(String key) {
        return params.get(key);
    }
}
