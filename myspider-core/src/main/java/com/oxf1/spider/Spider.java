package com.oxf1.spider;

import com.oxf1.spider.cacher.Cacher;
import com.oxf1.spider.component.MyspiderComponent;
import com.oxf1.spider.dedup.DeDup;
import com.oxf1.spider.downloader.Downloader;
import com.oxf1.spider.exception.MySpiderException;
import com.oxf1.spider.exception.MySpiderFetalException;
import com.oxf1.spider.exception.MySpiderRecoverableException;
import com.oxf1.spider.page.Page;
import com.oxf1.spider.pipline.Pipline;
import com.oxf1.spider.data.ProcessResult;
import com.oxf1.spider.processor.Processor;
import com.oxf1.spider.request.Request;
import com.oxf1.spider.scheduler.Scheduler;
import com.oxf1.spider.status.TaskStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * 一个线程要做的事情
 * Created by cxu on 2015/6/20.
 */
public class Spider extends MyspiderComponent implements Runnable{
    final static Logger logger = LoggerFactory.getLogger(Spider.class);

    private DeDup dedup;
    private Cacher cacher;
    private Downloader downloader;
    private List<Pipline> piplines;
    private Processor processor;
    private Scheduler scheduler;

    public Spider(TaskConfig taskConfig){
        super(taskConfig);
        piplines = new ArrayList<>(5);
        scheduler = taskConfig.getSchedulerObject();//共用scheduler减少竞争和去重误差
    }

    @Override
    public void run() {

        while(!Thread.currentThread().isInterrupted() &&
                getTaskConfig().getTaskStatus().equalsIgnoreCase(TaskStatus.Status.RUN.name())){
            TaskStatus status = getTaskConfig().getTaskStatusObject();
            List<Request> requests = null;
            try {
                requests = scheduler.poll(getTaskConfig().getSchedulerBatchSize());
            } catch (MySpiderException e) {
                e.printStackTrace();
                //TODO exp
                logger.error("Scheduler取url异常 {}", e);
            }

            for (Request req : requests) {//处理每一个请求
                Page pg = null;
                try {
                    pg = cacher.loadPage(req);
                } catch (MySpiderRecoverableException e) {
                    logger.error("读取缓存页面文件失败{}", e);
                }
                if (pg == null) {//缓存没有命中，从网络下载
                    pg = downloader.download(req);
                    if (pg == null) {
                        status.addFailedUrl(1);
                    }else{
                        status.addNetUrl(1);
                        status.addPageSizeKb(pg.sizeInKb());
                    }
                }else{//看一下是否缓存命中
                    if(pg.isFromCache()){
                        status.addCacheUrl(1);
                        status.addPageSizeKb(pg.sizeInKb());
                    }
                }
                if (pg == null) {
                    continue;
                }
                ProcessResult result = processor.process(pg);
                //处理链接
                List<Request> newLinks = result.getLinks();
                newLinks = dedup.deDup(newLinks);
                try {
                    scheduler.push(newLinks);
                } catch (MySpiderException e) {
                    e.printStackTrace();
                    //TODO exp
                    logger.error("Scheduler放url入队列异常 {}", e);
                }

                //存储数据
                for (Pipline pipline : piplines) {
                    try {
                        pipline.save(result.getData());
                        status.addDataItemCount(result.getData().size());
                    } catch (MySpiderFetalException e) {
                        logger.error("保存文件时出错 {}", e);
                        //TODO 数据保存出错统计
                    }
                }
            }
            if (requests.size() == 0) {//睡眠一段等待命令或者新的url到来
                int sleepTimeMs = getTaskConfig().getWaitUrlSleepTimeMs();
                try {
                    TimeUnit.MILLISECONDS.sleep(sleepTimeMs);
                } catch (InterruptedException e) {
                    logger.info("等待新的URL过程中发生InterruptedException");
                    break;
                }
                logger.info("睡眠{}秒，等待新的URL", sleepTimeMs);
            }
        }

        String taskStatus = getTaskConfig().getTaskStatus();
        if(TaskStatus.Status.CANCEL.name().equalsIgnoreCase(taskStatus)){
            //这种情况下，任务取消，删除：dedup队列，网页缓存，存储的数据，请求队列
            logger.info("任务{}被取消", getTaskConfig().getTaskFp());
            try {
                this.cleanTaskEnv();
            } catch (MySpiderRecoverableException e) {
                logger.info("清除任务相关文件失败 {}", e);
            }
        }
        else if(TaskStatus.Status.PAUSE.name().equalsIgnoreCase(taskStatus)){
            logger.info("任务{}暂停", getTaskConfig().getTaskFp());
        }
        else if(!TaskStatus.Status.RUN.name().equalsIgnoreCase(taskStatus)){
            logger.error("任务{}状态{}不能被识别", getTaskConfig().getTaskFp(), taskStatus);
        }
    }

    @Override
    public void close() {

    }

    /**
     * 情况任务占用的资源和产生的数据
     */
    private void cleanTaskEnv() throws MySpiderRecoverableException {
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
