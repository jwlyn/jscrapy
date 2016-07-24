package org.jscrapy.common.http;

import java.util.concurrent.atomic.AtomicLong;

/**
 * 给SpiderProxy做监控
 * Created by cxu on 2015/9/29.
 */
public class WatchableSpiderProxy extends SpiderProxy{
    private static final long FAILED_THREAD_HOLE = 10;//允许的最大失败次数
    AtomicLong failedCount = new AtomicLong(0);//连续失败次数，如果中间有正常就恢复为0

    public WatchableSpiderProxy(String user, String password, String host, int port) {
        super(user, password, host, port);
    }

    /**
     * 代理是否可用
     * @return
     */
    public boolean isUsable() {
        return failedCount.longValue() <= FAILED_THREAD_HOLE;
    }

    public void incFailedCount(long failedCount) {
        this.failedCount.addAndGet(failedCount);
    }
}
