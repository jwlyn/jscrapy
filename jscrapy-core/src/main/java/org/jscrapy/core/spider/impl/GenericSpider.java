package org.jscrapy.core.spider.impl;

import org.jscrapy.core.config.JscrapyConfig;
import org.jscrapy.core.data.ProcessResult;
import org.jscrapy.core.exception.MySpiderFetalException;
import org.jscrapy.core.page.Page;
import org.jscrapy.core.request.HttpRequest;
import org.jscrapy.core.request.RequestContext;
import org.jscrapy.core.spider.Spider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Created by cxu on 2018/2/8.
 */
public class GenericSpider extends Spider{
    final static Logger logger = LoggerFactory.getLogger(Spider.class);

    public GenericSpider(JscrapyConfig jscrapyConfig) {
        super(jscrapyConfig);
    }

    /**
     *
     */
    @Override
    protected void mainLoop() {

        int n = poolBigQueue();
        if (n != 0) {//集中式队列里有URL
            processMemQueue();
        }else{ //没URL的情况下考虑睡眠，但不考虑停止，是否停止听从admin里的调度模块发送的指令
            int sleepTimeMs = 100;
            try {
                TimeUnit.MILLISECONDS.sleep(sleepTimeMs);
            } catch (InterruptedException e) {
                logger.info("等待新的URL过程中发生InterruptedException");
                //TODO
            }
            logger.info("睡眠{}秒，等待新的URL", sleepTimeMs);
            //睡然后等待，可能队列里的URL被处理完了 TODO
        }
    }

    /**
     *
     * @return
     */
    private int poolBigQueue() {
        int fetchSize = getJscrapyConfig().getUrlFetchSize();

        //从集中的队列取URL
        List<RequestContext> requestContexts = urlConsumer.poll(fetchSize);
        return requestContexts.size();
    }

    /**
     *
     */
    private void processMemQueue() {
        HttpRequest request = null;
        while ((request = getRequest()) != null) {//处理内存里的request，直到结束
            processOneRequest(request);
        }
    }

    /**
     *
     * @return
     */
    private HttpRequest getRequest() {
        return urlConsumer.getMemQueue().poll();
    }

    /**
     *
     * @param httpRequest
     */
    private void processOneRequest(HttpRequest httpRequest) {
        Page pg = null;
        try {
            pg = cacher.loadPage(httpRequest);
        } catch (Throwable e) {
            logger.error("读取缓存页面文件失败{}", e);
            //TODO 这个错误可以跳过，如果缓存没有直接网上下载也可以
        }
        if (pg == null) {//缓存么有命中或者缓存出错
            pg = downloader.download(httpRequest);
            if (pg != null) {
                logger.debug("网络下载成功{}", pg);
            } else {
                logger.info("网络下载失败{}", pg);
            }

        } else {//缓存命中了
            logger.debug("命中缓存{}", httpRequest);
        }
        if (pg == null) {
            logger.error("页面未命中缓存且下载失败 page=null");
            //TODO 这里出错时候考虑一下是否要调整速率
            return;
        }
        ProcessResult result = processor.process(pg);
        //处理链接
        List<HttpRequest> newLinks = result.getLinks();
        urlProducer.push(null);


        //存储数据
        try{
            pipline.save(result.getData());
        }catch (MySpiderFetalException e) {
            logger.error("保存文件时出错 {}", e);
            //TODO 数据保存出错统计
        }

        if (!pg.isFromCache()) {//sleep
            try {
                TimeUnit.MILLISECONDS.sleep(1000);//TODO 参数化
            } catch (InterruptedException e) {
                logger.info("等待新的URL过程中发生InterruptedException");
                return;
            }
        }
    }
}
