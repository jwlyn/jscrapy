package org.jscrapy.core.spider;

import org.jscrapy.core.ConfigDriver;
import org.jscrapy.core.cacher.Cacher;
import org.jscrapy.core.config.JscrapyConfig;
import org.jscrapy.core.data.ProcessResult;
import org.jscrapy.core.dedup.DeDup;
import org.jscrapy.core.downloader.Downloader;
import org.jscrapy.core.exception.MySpiderFetalException;
import org.jscrapy.core.page.Page;
import org.jscrapy.core.pipline.Pipline;
import org.jscrapy.core.processor.Processor;
import org.jscrapy.core.producer.UrlProducer;
import org.jscrapy.core.request.Request;
import org.jscrapy.core.status.TaskStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * 一个线程要做的事情
 * Created by cxu on 2015/6/20.
 */
public class Spider extends ConfigDriver implements Runnable {
    final static Logger logger = LoggerFactory.getLogger(Spider.class);

    private DeDup dedup;
    private Cacher cacher;
    private Downloader downloader;
    private List<Pipline> piplines;
    private Processor processor;
    private UrlProducer urlProducer;

    public Spider(JscrapyConfig JscrapyConfig) {
        setJscrapyConfig(JscrapyConfig);
        piplines = new ArrayList<>(5);
        //scheduler = JscrapyConfig.getSchedulerObject();//共用scheduler减少竞争和去重误差
    }

    @Override
    public void run() {

        while (!Thread.currentThread().isInterrupted()) {

            //TaskStatus status = getJscrapyConfig().getTaskStatusObject();
            List<Request> requests = null;

                //requests = scheduler.poll(1);


            for (Request req : requests) {//处理每一个请求
                Page pg = null;
                try {
                    pg = cacher.loadPage(req);
                } catch (Throwable e) {
                    logger.error("读取缓存页面文件失败{}", e);
                }
                if (pg != null) {//缓存命中了
                    if (pg.isFromCache()) {
//                        status.addCacheUrl(1);
//                        status.addPageSizeKb(pg.sizeInKb());
                    }
                } else {//缓存没有命中，从网络下载
                    pg = downloader.download(req);
                    if (pg == null) {
//                        status.addFailedUrl(1);
                    } else {
//                        status.addNetUrl(1);
//                        status.addPageSizeKb(pg.sizeInKb());
                    }
                }
                if (pg == null) {
                    logger.error("页面下载失败 page=null");
                    continue;
                }
                ProcessResult result = processor.process(pg);
                //处理链接
                List<Request> newLinks = result.getLinks();
                newLinks = dedup.deDup(newLinks);

                    urlProducer.push(null);


                //存储数据
                for (Pipline pipline : piplines) {
                    try {
                        pipline.save(result.getData());
//                        status.addDataItemCount(result.getData().size());
                    } catch (MySpiderFetalException e) {
                        logger.error("保存文件时出错 {}", e);
                        //TODO 数据保存出错统计
                    }
                }

                if (!pg.isFromCache()) {//sleep
                    try {
                        TimeUnit.MILLISECONDS.sleep(1000);//TODO 参数化
                    } catch (InterruptedException e) {
                        logger.info("等待新的URL过程中发生InterruptedException");
                        break;
                    }
                }
            }

            if (requests.size() == 0) {//睡眠一段等待命令或者新的url到来
                int sleepTimeMs = 1;//getJscrapyConfig().getWaitUrlSleepTimeMs();
                try {
                    TimeUnit.MILLISECONDS.sleep(sleepTimeMs);
                } catch (InterruptedException e) {
                    logger.info("等待新的URL过程中发生InterruptedException");
                    break;
                }
                logger.info("睡眠{}秒，等待新的URL", sleepTimeMs);
            }
        }

        String taskStatus = null;//getJscrapyConfig().getTaskStatus();
        if (TaskStatus.Status.CANCEL.name().equalsIgnoreCase(taskStatus)) {
            //这种情况下，任务取消，删除：dedup队列，网页缓存，存储的数据，请求队列
            logger.info("任务{}被取消", getJscrapyConfig().getTaskFp());
//            try {
//                this.cleanTaskEnv();
//            } catch (MySpiderRecoverableException e) {
//                logger.info("清除任务相关文件失败 {}", e);
//            }
        } else if (TaskStatus.Status.PAUSE.name().equalsIgnoreCase(taskStatus)) {
            logger.info("任务{}暂停", getJscrapyConfig().getTaskFp());
        } else if (!TaskStatus.Status.RUN.name().equalsIgnoreCase(taskStatus)) {
            logger.error("任务{}状态{}不能被识别", getJscrapyConfig().getTaskFp(), taskStatus);
        }
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
