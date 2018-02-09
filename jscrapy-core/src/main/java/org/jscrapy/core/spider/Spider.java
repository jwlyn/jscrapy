package org.jscrapy.core.spider;

import org.jscrapy.core.JscrapyComponent;
import org.jscrapy.core.cacher.Cacher;
import org.jscrapy.core.comsumer.UrlConsumer;
import org.jscrapy.core.config.JscrapyConfig;
import org.jscrapy.core.dedup.DeDup;
import org.jscrapy.core.downloader.Downloader;
import org.jscrapy.core.pipline.Pipline;
import org.jscrapy.core.processor.Processor;
import org.jscrapy.core.producer.UrlProducer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 一个线程要做的事情
 * Created by cxu on 2015/6/20.
 */
public abstract class Spider extends JscrapyComponent implements Runnable {
    final static Logger logger = LoggerFactory.getLogger(Spider.class);

    @Autowired
    protected DeDup dedup;
    @Autowired
    protected Cacher cacher;
    @Autowired
    protected Downloader downloader;
    /**
     * 这里为什么没有采用webmagic一样的存到多个存储里？
     * 1，作者认为一份数据存多个地方是多此一举，不是爬虫的核心功能，核心应该尽量
     * 简单易理解维护，因为爬虫面临的情况太多了。
     * 2，存储多个数据源可以利用其他工具进行同步。
     * 3，与其存储到多个地方，不如开发存储成功之后发消息的方式更实用
     */
    @Autowired
    protected Pipline pipline;
    @Autowired
    protected Processor processor;
    @Autowired
    protected UrlProducer urlProducer;
    @Autowired
    protected UrlConsumer urlConsumer;

    public Spider(JscrapyConfig jscrapyConfig) {
        setJscrapyConfig(jscrapyConfig);
    }

    @Override
    public void run() {

        while (!Thread.currentThread().isInterrupted() && canRun()) {

            mainLoop();

        }
    }

    private boolean canRun(){
        return true;//TODO
    }

    protected abstract void mainLoop();
}
