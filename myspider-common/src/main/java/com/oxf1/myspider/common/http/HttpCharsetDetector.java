package com.oxf1.myspider.common.http;

import us.codecraft.webmagic.downloader.HttpClientDownloader;

import java.io.IOException;

/**
 * 探测具体实现的封装
 */
public class HttpCharsetDetector {

    private static Detector DETECTOR = new Detector();

    public static String detectEncode(org.apache.http.HttpResponse httpResponse, byte[] contentBytes)
            throws IOException {
        return DETECTOR.detectEncode(httpResponse, contentBytes);
    }
}

class Detector extends HttpClientDownloader {

    /**
     *
     * @param httpResponse
     * @param contentBytes
     * @return
     * @throws IOException
     */
    public String detectEncode(org.apache.http.HttpResponse httpResponse, byte[] contentBytes) throws IOException {
        return getHtmlCharset(httpResponse, contentBytes);
    }
}
