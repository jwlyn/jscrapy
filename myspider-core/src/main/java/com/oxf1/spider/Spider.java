package com.oxf1.spider;

import com.oxf1.spider.cacher.Cacher;
import com.oxf1.spider.component.MyspiderComponent;
import com.oxf1.spider.dedup.DeDup;
import com.oxf1.spider.downloader.Downloader;
import com.oxf1.spider.exception.MySpiderException;
import com.oxf1.spider.page.Page;
import com.oxf1.spider.pipline.Pipline;
import com.oxf1.spider.data.ProcessResult;
import com.oxf1.spider.processor.Processor;
import com.oxf1.spider.request.Request;
import com.oxf1.spider.scheduler.Scheduler;
import com.oxf1.spider.status.TaskStatus;

import java.util.ArrayList;
import java.util.List;

/**
 * 一个线程要做的事情
 * Created by cxu on 2015/6/20.
 */
public class Spider extends MyspiderComponent implements Runnable{
    private DeDup dedup;
    private Cacher cacher;
    private Downloader downloader;
    private List<Pipline> piplines;
    private Processor processor;
    private Scheduler scheduler;

    public Spider(TaskConfig taskConfig){
        super(taskConfig);
        piplines = new ArrayList<>(5);
        scheduler = taskConfig.getScheduler();//共用scheduler减少竞争和去重误差
    }

    @Override
    public void run() {

        while(!Thread.currentThread().isInterrupted() &&
                getTaskConfig().getTaskStatus().equalsIgnoreCase(TaskStatus.Status.RUN.name())){

            List<Request> requests = null;
            try {
                requests = scheduler.poll(getTaskConfig().getSchedulerBatchSize());
            } catch (MySpiderException e) {
                e.printStackTrace();
                //TODO log it
            }

            for (Request req : requests) {//处理每一个请求
                Page pg = cacher.loadPage(req);
                if (pg == null) {//缓存没有命中，从网络下载
                    pg = downloader.download(req);
                }

                ProcessResult result = processor.process(pg);
                //处理链接
                List<Request> newLinks = result.getLinks();
                newLinks = dedup.deDup(newLinks);
                try {
                    scheduler.push(newLinks);
                } catch (MySpiderException e) {
                    e.printStackTrace();
                    //TODO log
                }

                //存储数据
                for (Pipline pipline : piplines) {
                    pipline.save(result.getData());
                }
            }
            if (requests.size() == 0) {//睡眠一段等待命令或者新的url到来
                int sleepTimeMs = getTaskConfig().getWaitUrlSleepTimeMs();
                //TODO
            }

        }

        String taskStatus = getTaskConfig().getTaskStatus();
        if(TaskStatus.Status.CANCEL.name().equalsIgnoreCase(taskStatus)){
            //这种情况下，任务取消，删除：dedup队列，网页缓存，存储的数据，请求队列
            //TODO log it
            this.cleanTaskEnv();
        }
        else if(TaskStatus.Status.PAUSE.name().equalsIgnoreCase(taskStatus)){
            //TODO
        }
        else if(!TaskStatus.Status.RUN.name().equalsIgnoreCase(taskStatus)){
            //TODO log it
        }
    }

    @Override
    public void close() {

    }

    /**
     * 情况任务占用的资源和产生的数据
     */
    private void cleanTaskEnv() {
        dedup.close();
        cacher.close();
        for (Pipline p : piplines) {
            p.close();
        }
        scheduler.close();
    }

    public void setDedup(DeDup dedup) {
        this.dedup = dedup;
    }

    public void setDownloader(Downloader downloader) {
        this.downloader = downloader;
    }

    public void addPipline(Pipline pipline) {
        this.piplines.add(pipline);
    }

    public void setProcessor(Processor processor) {
        this.processor = processor;
    }

    public void setCacher(Cacher cacher) {
        this.cacher = cacher;
    }
}
