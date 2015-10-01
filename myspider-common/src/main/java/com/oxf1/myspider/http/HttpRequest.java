package com.oxf1.myspider.http;

import java.util.ArrayList;
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
    private Map<String,String> header = new HashMap<String, String>();
    private List<Integer> acceptCode;


    public HttpRequest() {
        acceptCode = new ArrayList<Integer>();
        acceptCode.add(200);
        header.put(HttpHeaderName.USER_AGENT, "myspider@github");
    }

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
        return header.get(HttpHeaderName.USER_AGENT);
    }

    public void setUserAgent(String userAgent) {
        header.put(HttpHeaderName.USER_AGENT, userAgent);
    }

    public void setIsAjax(boolean isAjax) {
        if (isAjax) {
            header.put(HttpHeaderName.AJAX, HttpHeaderValue.XMLHTTP_REQUEST);
        }
        else {
            header.remove(HttpHeaderName.AJAX);
        }
    }

    public String getReferer() {
        return header.get(HttpHeaderName.REFERER);
    }

    public void setReferer(String referer) {
        header.put(HttpHeaderName.REFERER, referer);
    }

    public String getHeader(String name) {
        return this.header.get(name);
    }

    public void setHeader(String name, String value) {
        this.header.put(name, value);
    }

    public final Map<String, String> getHeader() {
        return header;
    }

    public void setCookie(String cookie) {
        this.header.put(HttpHeaderName.COOKIE, cookie);
    }

    public String getCookie() {
        return this.header.get(HttpHeaderName.COOKIE);
    }

    public void setAcceptCode(List<Integer> acceptCode) {
        this.acceptCode = acceptCode;
    }

    public void addAcceptCode(int httpStatusCode) {
        acceptCode.add(httpStatusCode);
    }

    /**
     * 接受哪些http状态码
     * @param httpStatusCode
     * @return
     */
    public boolean isAccept(int httpStatusCode) {
        return this.acceptCode.contains(httpStatusCode);
    }
}
