package com.oxf1.spider.downloader.impl;

import com.oxf1.myspider.common.http.FetchRequest;
import com.oxf1.myspider.common.http.FetchResponse;
import com.oxf1.myspider.common.http.HttpMethod;
import com.oxf1.myspider.common.http.fetcher.HttpFetcher;
import com.oxf1.myspider.common.http.fetcher.impl.JoddHttpFetcher;
import com.oxf1.spider.TaskConfig;
import com.oxf1.spider.component.MyspiderComponent;
import com.oxf1.spider.downloader.Downloader;
import com.oxf1.spider.page.Page;
import com.oxf1.spider.request.Request;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.SocketTimeoutException;
import java.net.URISyntaxException;

/**
 * Created by cxu on 2014/11/21.
 */
public class HttpDownloader extends MyspiderComponent implements Downloader {
    private HttpFetcher fetcher;

    public HttpDownloader(TaskConfig taskConfig) {
        super(taskConfig);
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
        }catch(IOException e) {
            e.printStackTrace();
        }
        return null;//TODO 这里换成异常好点
    }

    @Override
    public void close() {

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
