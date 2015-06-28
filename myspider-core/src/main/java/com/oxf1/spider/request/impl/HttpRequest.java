package com.oxf1.spider.request.impl;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.oxf1.spider.request.HttpRequestMethod;
import com.oxf1.spider.request.Request;
import org.apache.commons.codec.digest.DigestUtils;

import java.util.Map;

/**
 * Created by cxu on 2014/11/21.
 */
public class HttpRequest extends Request{

    private String url;//请求的url
    private HttpRequestMethod httpMethod;//请求的http方法，GET|POST等
    private Map<String, String> parameters;//如果是post请求，这里存放请求参数

    /**
     * 构造函数
     * @param url
     * @param httpMethod
     * @param parameters
     */
    public HttpRequest(String url, HttpRequestMethod httpMethod, Map<String, String> parameters) {
        this.url = url;
        this.httpMethod = httpMethod;
        this.parameters = parameters;
    }

    private HttpRequest(){}

    @Override
    public String getUrl() {
        return this.url;
    }

    @Override
    public HttpRequestMethod getHttpMethod() {
        return this.httpMethod;
    }

    @Override
    public Map<String, String> getParameters() {
        return this.parameters;
    }

    @Override
    public String asJson() {
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.writeValueAsString(this);
        } catch (JsonProcessingException e) {
            e.printStackTrace();//TODO
        }

        return null;
    }

    @Override
    public String fp() {
        String s = this.asJson();
        return DigestUtils.sha1Hex(s);
    }

    @Override
    public String toString(){
        return this.asJson();
    }

    @JsonProperty("url")
    public void setUrl(String url){
        this.url = url;
    }

    @JsonProperty("http_method")
    public void setHttpMethod(String method)
    {
        this.httpMethod = HttpRequestMethod.valueOf(method);
    }

    @JsonProperty("post_parms")
    public void SetFormParameters(Map<String,String> params){
        this.parameters = params;
    }
}
