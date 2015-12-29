package com.oxf1.myspider.common.http;

import org.apache.commons.collections4.MultiMap;
import org.apache.commons.collections4.MultiValuedMap;
import org.apache.commons.collections4.multimap.ArrayListValuedHashMap;

import java.util.Collection;

/**
 * Created by cxu on 2015/9/30.
 */
public class FetchResponse {
    private MultiValuedMap<String, String> headers = new ArrayListValuedHashMap();
    private boolean success = true;
    private int statusCode;
    private String charset;
    private byte[] content;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public String getCharset() {
        return charset;
    }

    public void setCharset(String charset) {
        this.charset = charset;
    }

    public byte[] getContent() {
        return content;
    }

    public void setContent(byte[] content) {
        this.content = content;
    }

    public void addHeader(String key, String value) {
        headers.put(key, value);
    }

    public Collection<String> getHeaders(String key) {
        Collection<String> values = headers.get(key);
        return values;
    }

    public String getHeader(String key) {
        String value = null;

        Collection<String> values = getHeaders(key);
        if (values != null && values.size() > 0) {
            value = values.iterator().next();
        }

        return value;
    }
}
