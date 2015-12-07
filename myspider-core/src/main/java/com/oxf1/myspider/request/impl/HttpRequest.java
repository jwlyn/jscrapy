package com.oxf1.myspider.request.impl;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.annotation.JSONField;
import com.oxf1.myspider.request.HttpRequestMethod;
import com.oxf1.myspider.request.Request;
import org.apache.commons.codec.digest.DigestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

/**
 * Created by cxu on 2014/11/21.
 */
public class HttpRequest extends Request{
    final static Logger logger = LoggerFactory.getLogger(HttpRequest.class);
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

    public HttpRequest(String url) {
        this.url = url;
        this.httpMethod = HttpRequestMethod.GET;
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
        JSONObject jsonObject = new JSONObject(true);
        jsonObject.put("url", this.url);
        jsonObject.put("http_method", this.httpMethod.name());

        /*Map里的key一定要排序之后，因为去重用的是md5*/
        if (parameters != null && !parameters.isEmpty()) {
            Map<String, Object> jsonParam = new TreeMap<String, Object>();
            Set<Map.Entry<String, String>> entrySet = this.parameters.entrySet();
            for (Map.Entry<String, String> entry : entrySet) {
                jsonParam.put(entry.getKey(), entry.getValue());
            }
            JSONObject params = new JSONObject(jsonParam);
            jsonObject.put("parameters", params);
        }

        return jsonObject.toJSONString();
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

    @JSONField(name="url")
    public void setUrl(String url){
        this.url = url;
    }

    @JSONField(name="http_method")
    public void setHttpMethod(String method)
    {
        this.httpMethod = HttpRequestMethod.valueOf(method);
    }

    @JSONField(name="parameters")
    public void SetFormParameters(Map<String,String> params){
        this.parameters = params;
    }

}
