package javax.servlet;

import com.manor.httpserver.core.RequestObject;
import com.manor.httpserver.core.ResponseObject;

/**
 * 由SUN公司制定的Servlet接口,该接口由web服务器开发人员来调用, 由webApp开发人员来实现
 * @author SUN公司
 * @version 1.0
 * @since 1.0
 */
public interface Servlet {
    //处理业务的核心方法
    void service(ServletRequest request, ServletResponse out);
}
