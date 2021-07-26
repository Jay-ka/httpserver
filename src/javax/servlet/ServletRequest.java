package javax.servlet;


/**
 * 负责封装请求参数对象
 * @author SUN
 * @version 1.0
 * @since 1.0
 */
public interface ServletRequest {
    String getParameterValue(String key);
    String[] getParameterValues(String key);
}
