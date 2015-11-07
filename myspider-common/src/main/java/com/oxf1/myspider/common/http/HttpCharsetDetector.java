package com.oxf1.myspider.common.http;

import org.apache.commons.lang3.StringUtils;
import us.codecraft.webmagic.downloader.HttpClientDownloader;

import java.io.IOException;
import java.nio.charset.Charset;

/**
 * 探测具体实现的封装
 */
public class HttpCharsetDetector {

    private static Detector DETECTOR = new Detector();

    public static String detectEncode(org.apache.http.HttpResponse httpResponse, byte[] contentBytes)
            throws IOException {
        String charset = DETECTOR.detectEncode(httpResponse, contentBytes);
        if (StringUtils.isBlank(charset)) {//TODO webmagic的bug?会返回null, https://baidu.com的时候
            charset = Charset.defaultCharset().name();
        }

        return charset;
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
