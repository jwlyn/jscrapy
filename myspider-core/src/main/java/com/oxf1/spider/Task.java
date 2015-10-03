package com.oxf1.spider;

import com.oxf1.spider.cacher.Cacher;
import com.oxf1.spider.component.MyspiderComponent;
import com.oxf1.spider.dedup.DeDup;
import com.oxf1.spider.downloader.Downloader;
import com.oxf1.spider.pipline.Pipline;
import com.oxf1.spider.processor.Processor;
import com.oxf1.spider.scheduler.Scheduler;
import com.oxf1.spider.status.TaskStatus;
import com.oxf1.spider.util.ClassLoadUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 对线程池的抽象
 * Created by cxu on 2015/6/20.
 */
public class Task extends MyspiderComponent {
    final static Logger logger = LoggerFactory.getLogger(Task.class);
    private ExecutorService threads;

    public Task(TaskConfig taskConfig) {
        super(taskConfig);
        int threadCount = taskConfig.getThreadCount();
        threads = Executors.newFixedThreadPool(threadCount);
    }

    public void cancel() {
        getTaskConfig().setTaskStatus(TaskStatus.Status.CANCEL);
        threads.shutdown();
    }

    public void run() {
        TaskConfig cfg = getTaskConfig();
        cfg.setTaskStatus(TaskStatus.Status.RUN);

        String schedulerClass = cfg.getSchedulerClassName();
        String dedupClass = cfg.getDedupClassName();
        String downloaderClass = cfg.getDownloaderClassName();

        String processorClass = cfg.getProcessorClassName();
        String cacherClass = cfg.getCacherClassName();
        String[] piplineClass = cfg.getPiplineClassName().split(",");

        Scheduler scheduler = ClassLoadUtil.loadScheduler(schedulerClass, cfg);
        cfg.setSchedulerObject(scheduler);

        int threadCount = getTaskConfig().getThreadCount();
        for (int i = 0; i < threadCount; i++) {
            Spider sp = new Spider(getTaskConfig());
            DeDup dedup = ClassLoadUtil.loadDedup(dedupClass, cfg);
            Downloader downloader = ClassLoadUtil.loadDownloader(downloaderClass, cfg);
            Processor processor = ClassLoadUtil.loadProcessor(processorClass, cfg);
            Cacher cacher = ClassLoadUtil.loadCacher(cacherClass, cfg);
            sp.setDedup(dedup);
            sp.setDownloader(downloader);
            sp.setProcessor(processor);
            sp.setCacher(cacher);
            for (String p : piplineClass) {
                Pipline pipline = ClassLoadUtil.loadPipline(p, cfg);
                sp.addPipline(pipline);
            }

            threads.submit(sp);
        }
    }

    public void pause() {
        getTaskConfig().setTaskStatus(TaskStatus.Status.PAUSE);
        threads.shutdown();
    }

    @Override
    public void close() {

    }

    @Override
    public String toString() {
        TaskConfig cfg = getTaskConfig();
        String taskName = cfg.getTaskName();
        String taskFp = cfg.getTaskFp();
        return "Task{" +
                "tasiName=" + taskName +
                "taskFp=" + taskFp +
                '}';
    }
}
