package com.oxf1.spider;

import com.oxf1.spider.component.MyspiderComponent;
import com.oxf1.spider.downloader.Downloader;
import com.oxf1.spider.dedup.DeDup;
import com.oxf1.spider.pipline.Pipline;
import com.oxf1.spider.processor.Processor;
import com.oxf1.spider.scheduler.Scheduler;

/**
 * 一个线程要做的事情
 * Created by cxu on 2015/6/20.
 */
public class Spider extends MyspiderComponent implements Runnable{
    private DeDup dedup;
    private Downloader downloader;
    private Pipline pipline;//TODO 多个pipline
    private Processor processor;
    private Scheduler scheduler;

    public Spider(TaskConfig taskConfig){
        super(taskConfig);
    }

    @Override
    public void close() {

    }

    public void setDedup(DeDup dedup) {
        this.dedup = dedup;
    }

    public void setDownloader(Downloader downloader) {
        this.downloader = downloader;
    }

    public void setPipline(Pipline pipline) {
        this.pipline = pipline;
    }

    public void setProcessor(Processor processor) {
        this.processor = processor;
    }

    public void setScheduler(Scheduler scheduler) {
        this.scheduler = scheduler;
    }

    @Override
    public void run() {
        //TODO
        System.out.println("running...");
    }
}
