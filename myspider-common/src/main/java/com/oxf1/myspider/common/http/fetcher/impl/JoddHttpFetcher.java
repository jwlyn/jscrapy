package com.oxf1.myspider.common.http.fetcher.impl;

import com.oxf1.myspider.common.http.FetchRequest;
import com.oxf1.myspider.common.http.FetchResponse;
import com.oxf1.myspider.common.http.WatchableSpiderProxy;
import com.oxf1.myspider.common.http.fetcher.HttpFetcher;
import jodd.http.*;
import jodd.http.net.SocketHttpConnectionProvider;
import jodd.util.StringUtil;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.SocketTimeoutException;
import java.net.URISyntaxException;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by cxu on 2015/11/28.
 */
public class JoddHttpFetcher implements HttpFetcher {

    public FetchResponse download(FetchRequest fetchRequest) throws SocketTimeoutException, URISyntaxException, UnsupportedEncodingException, IOException {
        FetchResponse fetchResponse = new FetchResponse();

        HttpConnectionProvider connectionProvider = new JoddConnectionProvider();
        ProxyInfo proxyInfo = getProxyInfo(fetchRequest);
        if (proxyInfo != null) {
            connectionProvider.useProxy(proxyInfo);
        }

        HttpRequest request = this.rendRequest(fetchRequest);
        HttpResponse response = request.open(connectionProvider).send();

        fetchResponse = getFetchResponse(fetchRequest, response);
        return fetchResponse;
    }

    /**
     *
     * @param fetchRequest
     * @return
     */
    private HttpRequest rendRequest(FetchRequest fetchRequest) {
        HttpRequest request = null;
        switch(fetchRequest.getHttpMethod().name()) {
            case "POST":{
                request = HttpRequest.post(fetchRequest.getUrl());//请求方法
                if (fetchRequest.getRequestParameters() != null) {//请求参数
                    for (Map.Entry<String, String> parameter : fetchRequest.getRequestParameters().entrySet()) {
                        request.form(parameter.getKey(), parameter.getValue());
                    }
                }
                break;
            }
            case "GET":
            default:{
                request = HttpRequest.get(fetchRequest.getUrl());//请求方法
                if (fetchRequest.getRequestParameters() != null) {//请求参数
                    request.query(fetchRequest.getRequestParameters());
                }
                break;
            }
        }

        if (fetchRequest.getHeader() != null) {//set header
            for (Map.Entry<String, String> hd : fetchRequest.getHeader().entrySet()) {
                request.header(hd.getKey(), hd.getValue());
            }
        }
        return request;
    }

    /**
     *
     * @param fetchRequest
     * @return
     */
    private ProxyInfo getProxyInfo(FetchRequest fetchRequest) {
        ProxyInfo proxyInfo = null;

        WatchableSpiderProxy proxy = fetchRequest.getProxy();
        if (proxy != null) {
            String host = proxy.getHost();
            int port = proxy.getPort();
            String user = proxy.getUser();
            String psw = proxy.getPassword();
            proxyInfo = new ProxyInfo(ProxyInfo.ProxyType.HTTP, host, port, user, psw);
        }

        return proxyInfo;
    }

    /**
     *
     * @param fetchRequest
     * @param response
     * @return
     */
    private FetchResponse getFetchResponse(FetchRequest fetchRequest, HttpResponse response) {
        FetchResponse res = new FetchResponse();
        String charset = fetchRequest.getCharset();
        if (StringUtil.isBlank(charset)) {
            charset = response.charset();
        }
        res.setCharset(charset);
        res.setStatusCode(response.statusCode());

        HttpMultiMap<String> headers = response.headers();//other headers
        Iterator<Map.Entry<String, String>> itr = headers.iterator();
        while (itr != null && itr.hasNext()) {
            Map.Entry<String, String> hdr = itr.next();
            res.addHeader(hdr.getKey(), hdr.getValue());
        }

        res.setContent(response.bodyBytes());
        res.setSuccess(true);

        return res;
    }
}

class JoddConnectionProvider extends SocketHttpConnectionProvider {
    //TODO 链接超时等设置

}