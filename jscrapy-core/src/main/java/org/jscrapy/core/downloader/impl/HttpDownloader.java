package org.jscrapy.core.downloader.impl;

import org.jscrapy.core.config.JscrapyConfig;
import org.jscrapy.core.downloader.DownloadResponse;
import org.jscrapy.core.downloader.Downloader;
import org.jscrapy.core.page.Page;
import org.jscrapy.core.request.HttpRequest;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

/**
 * Created by cxu on 2014/11/21.
 */
public class HttpDownloader  extends Downloader {

    private OkHttpDownloaderImpl okHttpDownloader;

    public HttpDownloader(JscrapyConfig JscrapyConfig) {
        setJscrapyConfig(JscrapyConfig);
    }

    @Override
    public Page download(HttpRequest request) {

        try {
            DownloadResponse response = okHttpDownloader.doDownload(request);
            return getPage(request, response);
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
    private Page getPage(HttpRequest request, DownloadResponse response) throws UnsupportedEncodingException {
        Page page = new Page();
        String utf8Content = new String(response.getContent(), response.getCharset());
        page.setRawText(utf8Content);
        page.setIsFromCache(false);
        page.setRequest(request);
        return page;
    }
}
