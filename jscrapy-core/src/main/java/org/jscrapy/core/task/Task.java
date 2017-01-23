package org.jscrapy.core.task;

import org.jscrapy.core.ConfigDriver;
import org.jscrapy.core.config.JscrapyConfig;
import org.jscrapy.core.exception.MySpiderFetalException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 对线程池的抽象
 * Created by cxu on 2015/6/20.
 */
public class Task extends ConfigDriver {
    final static Logger logger = LoggerFactory.getLogger(Task.class);
    private ExecutorService threads;

    public Task(JscrapyConfig jscrapyConfig) {
        setJscrapyConfig(jscrapyConfig);
        int threadCount = jscrapyConfig.getThreadCount();
        threads = Executors.newFixedThreadPool(threadCount);
    }

    public void cancel() throws MySpiderFetalException {

    }

    public void run() throws MySpiderFetalException {
//        JscrapyConfig cfg = getJscrapyConfig();
//        cfg.setTaskStatus(TaskStatus.Status.RUN);
//
//        String schedulerClass = cfg.getSchedulerClassName();
//        String dedupClass = cfg.getDedupClassName();
//        String downloaderClass = cfg.getDownloaderClassName();
//
//        String processorClass = cfg.getProcessorClassName();
//        String cacherClass = cfg.getCacherClassName();
//        String[] piplineClass = cfg.getPiplineClassName().split(",");
//
//        Scheduler scheduler = ClassLoadUtil.loadScheduler(schedulerClass, cfg);
//        cfg.setSchedulerObject(scheduler);
//
//        String logPath = cfg.getTaskLogDir();
//        org.apache.log4j.Logger logger = MyLoggerFactory.getModuleLogger(cfg.getTaskFp(), logPath);
//        cfg.setTaskLogger(logger);
//        int threadCount = getJscrapyConfig().getThreadCount();
//        for (int i = 0; i < threadCount; i++) {
//            Spider sp = new Spider(getJscrapyConfig());
//            DeDup dedup = ClassLoadUtil.loadDedup(dedupClass, cfg);
//            Downloader downloader = ClassLoadUtil.loadDownloader(downloaderClass, cfg);
//            Processor processor = ClassLoadUtil.loadProcessor(processorClass, cfg);
//            Cacher cacher = ClassLoadUtil.loadCacher(cacherClass, cfg);
//            sp.setDedup(dedup);
//            sp.setDownloader(downloader);
//            sp.setProcessor(processor);
//            sp.setCacher(cacher);
//            for (String p : piplineClass) {
//                Pipline pipline = ClassLoadUtil.loadPipline(p, cfg);
//                sp.addPipline(pipline);
//            }
//
//            threads.submit(sp);
//        }
    }

    public void pause() throws MySpiderFetalException {
//        getJscrapyConfig().setTaskStatus(TaskStatus.Status.PAUSE);
//        threads.shutdown();
    }

    public JscrapyConfig getJscrapyConfig() {
        return getJscrapyConfig();
    }

    @Override
    public String toString() {
        JscrapyConfig cfg = getJscrapyConfig();
        String taskName = cfg.getTaskName();
        String taskFp = cfg.getTaskFp();
        return "Task{" +
                "tasiName=" + taskName +
                "taskFp=" + taskFp +
                '}';
    }
}
