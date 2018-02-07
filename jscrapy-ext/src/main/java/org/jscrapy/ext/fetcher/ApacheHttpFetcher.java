package org.jscrapy.ext.fetcher;

/**
 * Created by cxu on 2015/9/29.
 */
public class ApacheHttpFetcher  {

//
//    public FetchResponse download(FetchRequest fetchRequest) throws SocketTimeoutException, URISyntaxException, UnsupportedEncodingException, IOException {
//        FetchResponse finalResponse = new FetchResponse();
//
//        HttpRequestBase req = this.buildRequest(fetchRequest);
//        CloseableHttpClient httpClient = this.buildHttpClient(fetchRequest);
//        CloseableHttpResponse response = null;
//        try {
//            response = httpClient.execute(req);
//            int statusCode = response.getStatusLine().getStatusCode();
//            if (!fetchRequest.isAccept(statusCode)) {
//                //TODO log it
//                finalResponse.setSuccess(false);
//                finalResponse.setStatusCode(statusCode);
//                return finalResponse;
//            }
//            else{
//                finalResponse.setSuccess(true);
//                finalResponse.setStatusCode(statusCode);
//                byte[] fetchBytes = IOUtils.toByteArray(response.getEntity().getContent());
//                finalResponse.setContent(fetchBytes);
//
//                String httpCharset = HttpCharsetDetector.detectEncode(response, fetchBytes);
//                if (httpCharset != null) {
//                    finalResponse.setCharset(httpCharset);
//                }
//                //TODO log it
//                return finalResponse;
//            }
//
//        }
//        finally {
//            response.close();
//            httpClient.close();
//        }
//    }
//
//    /**
//     * 组装http请求
//     * @param fetchRequest
//     * @return
//     * @throws URISyntaxException
//     * @throws UnsupportedEncodingException
//     */
//    private HttpRequestBase buildRequest(FetchRequest fetchRequest) throws URISyntaxException, UnsupportedEncodingException{
//
//        HttpRequestBase httpMethod = null;
//
//        if (fetchRequest.getHttpMethod() == HttpMethod.GET) {
//            URIBuilder bd = new URIBuilder(fetchRequest.getUrl());
//
//            //设置GET的请求参数
//            final Map<String, String> parameters = fetchRequest.getRequestParameters();
//            if (parameters != null) {
//                Set<String> parameterNames = parameters.keySet();
//                for (String pName : parameterNames) {
//                    bd.addParameter(pName, parameters.get(pName));
//                }
//            }
//
//            URI uri = bd.build();
//            httpMethod = new HttpGet(uri);
//        }
//        else if (fetchRequest.getHttpMethod() == HttpMethod.POST) {
//            httpMethod = new HttpPost(fetchRequest.getUrl());
//            //设置POST参数
//            List<NameValuePair> nvps = new ArrayList<NameValuePair>();
//            final Map<String, String> parameters = fetchRequest.getRequestParameters();
//            Set<String> parameterNames = parameters.keySet();
//            for (String pName : parameterNames) {
//                nvps.add(new BasicNameValuePair(pName, parameters.get(pName)));
//            }
//            ((HttpPost)httpMethod).setEntity(new UrlEncodedFormEntity(nvps));
//        }
//
//        //设置头：cookie, isAjax, referer, userAgent等
//        final Map<String, String> headers = fetchRequest.getHeader();
//        Set<String> headerNames = headers.keySet();
//        for (String hdName : headerNames) {
//            httpMethod.setHeader(hdName, headers.get(hdName));
//        }
//
//        //设置代理
//        WatchableSpiderProxy proxy = fetchRequest.getProxy();
//        RequestConfig.Builder requestConfigBuilder = RequestConfig.custom()
//                /*.setConnectionRequestTimeout(this.taskConfig.getHttpTimeoutMs())
//                .setSocketTimeout(this.taskConfig.getHttpTimeoutMs())
//                .setConnectTimeout(this.taskConfig.getHttpTimeoutMs())*/
//                .setMaxRedirects(3)//最大跳转次数
//                .setCookieSpec(CookieSpecs.BEST_MATCH);
//        if (proxy != null) {
//            HttpHost httpProxy = new HttpHost(proxy.getHost(), proxy.getPort());
//            if (proxy != null) {
//                requestConfigBuilder.setProxy(httpProxy);
//            }
//        }
//        httpMethod.setConfig(requestConfigBuilder.build());
//
//        return httpMethod;
//    }
//
//    /**
//     * 下载客户端
//     * @param fetchRequest
//     * @return
//     */
//    private CloseableHttpClient buildHttpClient(FetchRequest fetchRequest) {
//
//        HttpClientBuilder httpClientBuilder = HttpClients.custom();
//        WatchableSpiderProxy proxy = fetchRequest.getProxy();
//
//        if (proxy != null) {//代理认证
//            CredentialsProvider credsProvider = new BasicCredentialsProvider();
//            credsProvider.setCredentials(new AuthScope(AuthScope.ANY_HOST, AuthScope.ANY_PORT),
//                    new UsernamePasswordCredentials(proxy.getUserName(), proxy.getPassword()));
//            httpClientBuilder.setDefaultCredentialsProvider(credsProvider);
//        }
//
//        Registry<ConnectionSocketFactory> reg = RegistryBuilder.<ConnectionSocketFactory> create()
//                .register(HttpHeaderConstant.HTTP, PlainConnectionSocketFactory.INSTANCE)
//                .register(HttpHeaderConstant.HTTPS, SSLConnectionSocketFactory.getSocketFactory()).build();
//        HttpClientConnectionManager connectionManager = new BasicHttpClientConnectionManager(reg);
//        httpClientBuilder.setConnectionManager(connectionManager);
//
//        SocketConfig socketConfig = SocketConfig.custom().setSoKeepAlive(true).setTcpNoDelay(true)
//                .build();
//        httpClientBuilder.setDefaultSocketConfig(socketConfig);
//
//        return httpClientBuilder.build();
//    }
}
