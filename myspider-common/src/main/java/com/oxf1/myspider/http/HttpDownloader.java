package com.oxf1.myspider.http;

import org.apache.commons.io.IOUtils;
import org.apache.http.HttpHost;
import org.apache.http.NameValuePair;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.config.CookieSpecs;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.config.SocketConfig;
import org.apache.http.conn.HttpClientConnectionManager;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.BasicHttpClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.SocketTimeoutException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by cxu on 2015/9/29.
 */
public class HttpDownloader {
    //TODO 增加httpClient的缓存功能，每次构造一个连接是比较费时的

    public FetchResponse download(HttpRequest httpRequest) throws SocketTimeoutException, URISyntaxException, UnsupportedEncodingException, IOException {
        FetchResponse finalResponse = new FetchResponse();

        HttpRequestBase req = this.buildRequest(httpRequest);
        CloseableHttpClient httpClient = this.buildHttpClient(httpRequest);
        CloseableHttpResponse response = null;
        try {
            response = httpClient.execute(req);
            int statusCode = response.getStatusLine().getStatusCode();
            if (!httpRequest.isAccept(statusCode)) {
                //TODO log it
                finalResponse.setSuccess(false);
                finalResponse.setStatusCode(statusCode);
                return finalResponse;
            }
            else{
                finalResponse.setSuccess(true);
                finalResponse.setStatusCode(statusCode);
                byte[] fetchBytes = IOUtils.toByteArray(response.getEntity().getContent());
                finalResponse.setContent(fetchBytes);

                String httpCharset = HttpCharsetDetector.detectEncode(response, fetchBytes);
                if (httpCharset != null) {
                    finalResponse.setCharset(httpCharset);
                }
                //TODO log it
                return finalResponse;
            }

        }
        finally {
            response.close();
            httpClient.close();
        }
    }

    /**
     * 组装http请求
     * @param httpRequest
     * @return
     * @throws URISyntaxException
     * @throws UnsupportedEncodingException
     */
    private HttpRequestBase buildRequest(HttpRequest httpRequest) throws URISyntaxException, UnsupportedEncodingException{

        HttpRequestBase httpMethod = null;

        if (httpRequest.getHttpMethod() == HttpMethod.GET) {
            URIBuilder bd = new URIBuilder(httpRequest.getUrl());

            //设置GET的请求参数
            final Map<String, String> parameters = httpRequest.getRequestParameters();
            Set<String> parameterNames = parameters.keySet();
            for (String pName : parameterNames) {
                bd.addParameter(pName, parameters.get(pName));
            }

            URI uri = bd.build();
            httpMethod = new HttpGet(uri);
        }
        else if (httpRequest.getHttpMethod() == HttpMethod.POST) {
            httpMethod = new HttpPost(httpRequest.getUrl());
            //设置POST参数
            List<NameValuePair> nvps = new ArrayList<NameValuePair>();
            final Map<String, String> parameters = httpRequest.getRequestParameters();
            Set<String> parameterNames = parameters.keySet();
            for (String pName : parameterNames) {
                nvps.add(new BasicNameValuePair(pName, parameters.get(pName)));
            }
            ((HttpPost)httpMethod).setEntity(new UrlEncodedFormEntity(nvps));
        }

        //设置头：cookie, isAjax, referer, userAgent等
        final Map<String, String> headers = httpRequest.getHeader();
        Set<String> headerNames = headers.keySet();
        for (String hdName : headerNames) {
            httpMethod.setHeader(hdName, headers.get(hdName));
        }

        //设置代理
        WatchableSpiderProxy proxy = httpRequest.getProxy();
        RequestConfig.Builder requestConfigBuilder = RequestConfig.custom()
                /*.setConnectionRequestTimeout(this.taskConfig.getHttpTimeoutMs())
                .setSocketTimeout(this.taskConfig.getHttpTimeoutMs())
                .setConnectTimeout(this.taskConfig.getHttpTimeoutMs())*/
                .setMaxRedirects(3)//最大跳转次数
                .setCookieSpec(CookieSpecs.BEST_MATCH);
        if (proxy != null) {
            HttpHost httpProxy = new HttpHost(proxy.getHost(), proxy.getPort());
            if (proxy != null) {
                requestConfigBuilder.setProxy(httpProxy);
            }
        }
        httpMethod.setConfig(requestConfigBuilder.build());

        return httpMethod;
    }

    /**
     * 下载客户端
     * @param httpRequest
     * @return
     */
    private CloseableHttpClient buildHttpClient(HttpRequest httpRequest) {

        HttpClientBuilder httpClientBuilder = HttpClients.custom();
        WatchableSpiderProxy proxy = httpRequest.getProxy();

        if (proxy != null) {//代理认证
            CredentialsProvider credsProvider = new BasicCredentialsProvider();
            credsProvider.setCredentials(new AuthScope(AuthScope.ANY_HOST, AuthScope.ANY_PORT),
                    new UsernamePasswordCredentials(proxy.getUser(), proxy.getPassword()));
            httpClientBuilder.setDefaultCredentialsProvider(credsProvider);
        }

        Registry<ConnectionSocketFactory> reg = RegistryBuilder.<ConnectionSocketFactory> create()
                .register(HttpHeaderValue.HTTP, PlainConnectionSocketFactory.INSTANCE)
                .register(HttpHeaderValue.HTTPS, SSLConnectionSocketFactory.getSocketFactory()).build();
        HttpClientConnectionManager connectionManager = new BasicHttpClientConnectionManager(reg);
        httpClientBuilder.setConnectionManager(connectionManager);

        SocketConfig socketConfig = SocketConfig.custom().setSoKeepAlive(true).setTcpNoDelay(true)
                .build();
        httpClientBuilder.setDefaultSocketConfig(socketConfig);

        return httpClientBuilder.build();
    }
}
