package org.jscrapy.core.config.modulecfg;

import org.apache.commons.lang3.StringUtils;
import org.jscrapy.core.config.SysDefaultConfig;

/**
 * 任务最近本的配置:线程数目，名称，id等
 * Created by cxu on 2016/7/26.
 */
public class TaskBaseConfig {

    private String taskId;//任务id,元数据里的唯一标示
    private String taskName;//任务名称
    private int urlFetchFromQueueSize;//任务每次从集中队列里取出多少个URL
    private int threadCount;//每个节点上并发的线程数目
    private int waitOnQueueEmptyMs;//当队列空的时候睡眠等待多少毫秒

    private String virtualId;//虚拟的ID，用于集群分组，伪分布式

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public int getUrlFetchFromQueueSize() {
        return urlFetchFromQueueSize;
    }

    public void setUrlFetchFromQueueSize(int urlFetchFromQueueSize) {
        this.urlFetchFromQueueSize = urlFetchFromQueueSize;
    }

    public int getThreadCount() {
        return threadCount;
    }

    public void setThreadCount(int threadCount) {
        this.threadCount = threadCount;
    }

    public int getWaitOnQueueEmptyMs() {
        return waitOnQueueEmptyMs;
    }

    public void setWaitOnQueueEmptyMs(int waitOnQueueEmptyMs) {
        this.waitOnQueueEmptyMs = waitOnQueueEmptyMs;
    }

    public String getVirtualId() {
        if (StringUtils.isBlank(virtualId)) {
            virtualId = SysDefaultConfig.VIRTUAL_ID;
        }
        return virtualId;
    }

    /**
     * 本机唯一任务标示
     *
     * @return
     */
    public String getTaskFp() {
        StringBuffer buf = new StringBuffer(50);
        buf.append(SysDefaultConfig.HOST)
                .append("@")
                .append(getTaskName())
                .append("@")
                .append(getTaskId())
                .append("@")
                .append(getVirtualId());//使用jvm进程Id可以在一台机器上模拟分布式

        return buf.toString();
    }
}
