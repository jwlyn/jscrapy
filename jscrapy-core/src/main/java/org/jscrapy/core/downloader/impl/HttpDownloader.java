package org.jscrapy.core.downloader.impl;

import org.jscrapy.common.http.FetchRequest;
import org.jscrapy.common.http.FetchResponse;
import org.jscrapy.common.http.HttpMethod;
import org.jscrapy.common.http.fetcher.HttpFetcher;
import org.jscrapy.common.http.fetcher.impl.JoddHttpFetcher;
import org.jscrapy.core.ConfigDriver;
import org.jscrapy.core.config.JscrapyConfig;
import org.jscrapy.core.downloader.Downloader;
import org.jscrapy.core.page.Page;
import org.jscrapy.core.request.Request;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.SocketTimeoutException;
import java.net.URISyntaxException;

/**
 * Created by cxu on 2014/11/21.
 */
public class HttpDownloader extends ConfigDriver implements Downloader {
    private HttpFetcher fetcher;

    public HttpDownloader(JscrapyConfig JscrapyConfig) {
        super(JscrapyConfig);
        fetcher = new JoddHttpFetcher();//TODO 配置化
    }

    @Override
    public Page download(Request request) {
        FetchRequest req = new FetchRequest();
        req.setUrl(request.getUrl());
        req.setHttpMethod(HttpMethod.valueOf(request.getHttpMethod().name()));
        req.setRequestParameters(request.getParameters());

        try {
            FetchResponse response = fetcher.download(req);
            return getPage(request, response);
        } catch (URISyntaxException e) {
            e.printStackTrace();//TODO
        } catch (SocketTimeoutException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;//TODO 这里换成异常好点
    }

    /**
     * @param response
     * @return
     */
    private Page getPage(Request request, FetchResponse response) throws UnsupportedEncodingException {
        Page page = new Page();
        String utf8Content = new String(response.getContent(), response.getCharset());
        page.setRawText(utf8Content);
        page.setIsFromCache(false);
        page.setRequest(request);
        return page;
    }
}
