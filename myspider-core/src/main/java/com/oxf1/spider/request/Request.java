package com.oxf1.spider.request;

import java.util.Map;

/**
 * 代表了一个Queue里的url请求
 * Created by cxu on 2014/11/21.
 */
public interface Request {

    /**
     * 请求地址
     * @return
     */
    public String getUrl();

    /**
     * 获取url的请求方法：GET|POST|DELETE|TRACE|HEAD..
     * @return
     */
    public HttpRequestMethod getHttpMethod();

    /**
     * 如果是POST请求，获取请求的参数
     * @return
     */
    public Map<String, String> getParameters();

    public String asJson();

    /**
     * 对象的一个md5标识，用于去重
     * @return
     */
    public String fp();

}
