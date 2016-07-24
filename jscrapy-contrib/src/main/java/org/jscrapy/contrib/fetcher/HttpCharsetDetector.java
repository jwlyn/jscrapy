package org.jscrapy.contrib.fetcher;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpResponse;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import us.codecraft.webmagic.utils.UrlUtils;//TODO 去掉webmagic的依赖

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;

/**
 * 探测网页返回的编码
 */
public class HttpCharsetDetector {

    final static Logger logger = LoggerFactory.getLogger(HttpCharsetDetector.class);

    public static String detectEncode(HttpResponse httpResponse, byte[] contentBytes)
            throws IOException {
        String contentType = httpResponse.getEntity().getContentType().getValue();
        String charset = detectEncode(contentType, contentBytes);
        if (StringUtils.isBlank(charset)) {//TODO webmagic的bug?会返回null, https://baidu.com的时候
            charset = Charset.defaultCharset().name();
        }

        return charset;
    }

    private static String detectEncode(String contentTypeValue, byte[] contentBytes) throws UnsupportedEncodingException {
        String charset;
        // charset
        // 1、encoding in http header Content-Type
        charset = UrlUtils.getCharset(contentTypeValue);
        if (org.apache.commons.lang.StringUtils.isNotBlank(charset)) {
            logger.debug("Auto get charset: {}", charset);
            return charset;
        }
        // use default charset to decode first time
        Charset defaultCharset = Charset.defaultCharset();
        String content = new String(contentBytes, defaultCharset.name());
        // 2、charset in meta
        if (org.apache.commons.lang.StringUtils.isNotEmpty(content)) {
            Document document = Jsoup.parse(content);
            Elements links = document.select("meta");
            for (Element link : links) {
                // 2.1、html4.01 <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
                String metaContent = link.attr("content");
                String metaCharset = link.attr("charset");
                if (metaContent.indexOf("charset") != -1) {
                    metaContent = metaContent.substring(metaContent.indexOf("charset"), metaContent.length());
                    charset = metaContent.split("=")[1];
                    break;
                }
                // 2.2、html5 <meta charset="UTF-8" />
                else if (org.apache.commons.lang.StringUtils.isNotEmpty(metaCharset)) {
                    charset = metaCharset;
                    break;
                }
            }
        }
        logger.debug("Auto get charset: {}", charset);
        // 3、todo use tools as cpdetector for content decode
        return charset;
    }
}


