package com.oxf1.myspider.http;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by cxu on 2015/9/29.
 */
public class HttpRequest {

    private String url;
    private HttpMethod httpMethod = HttpMethod.GET; //请求方法
    private HashMap<String, String> requestParameters = new HashMap<String, String>();//请求参数

    private WatchableSpiderProxy proxy;
    private String userAgent = "userAgent/github.com/oxf1/myspider";
    private boolean isAjax = false; //是否是ajax请求
    private String referer;
    private Map<String,String> header = new HashMap<String, String>();
    private List<Integer> acceptCode;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public HttpMethod getHttpMethod() {
        return httpMethod;
    }

    public void setHttpMethod(HttpMethod httpMethod) {
        this.httpMethod = httpMethod;
    }

    public HashMap<String, String> getRequestParameters() {
        return requestParameters;
    }

    public void setRequestParameters(HashMap<String, String> requestParameters) {
        this.requestParameters = requestParameters;
    }

    public WatchableSpiderProxy getProxy() {
        return proxy;
    }

    public void setProxy(WatchableSpiderProxy proxy) {
        this.proxy = proxy;
    }

    public String getUserAgent() {
        return userAgent;
    }

    public void setUserAgent(String userAgent) {
        this.userAgent = userAgent;
    }

    public boolean isAjax() {
        return isAjax;
    }

    public void setIsAjax(boolean isAjax) {
        this.isAjax = isAjax;
    }

    public String getReferer() {
        return referer;
    }

    public void setReferer(String referer) {
        this.referer = referer;
    }

    public String getHeader(String name) {
        return this.header.get(name);
    }

    public void setHeader(String name, String value) {
        this.header.put(name, value);
    }

    public void setCookie(String cookie) {
        this.header.put("Cookie", cookie);
    }

    public String getCookie() {
        return this.header.get("Cookie");
    }
}
