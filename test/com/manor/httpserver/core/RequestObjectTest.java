package com.manor.httpserver.core;

import org.junit.Test;

/**
 * @author kanghuajie
 * @date 2021/7/25
 */
public class RequestObjectTest {

    @Test
    public void testGetParameter() {
        RequestObject requestObject = new RequestObject("/oa/user/save?username=zhangsan");
        System.out.println(requestObject.getParameterValue("username"));
    }


}
