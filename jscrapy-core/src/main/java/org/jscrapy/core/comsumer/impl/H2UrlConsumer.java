package org.jscrapy.core.comsumer.impl;

import org.jscrapy.core.comsumer.UrlConsumer;
import org.jscrapy.core.config.JscrapyConfig;
import org.jscrapy.core.config.modulecfg.H2QueueConfig;
import org.jscrapy.core.dal.UrlQueueDo;
import org.jscrapy.core.dal.UrlQueueMapper;
import org.jscrapy.core.request.RequestContext;
import org.jscrapy.core.request.UrlStatus;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by cxu on 2016/7/30.
 */
public class H2UrlConsumer extends UrlConsumer {

    private UrlQueueMapper urlQueueMapper;

    /**
     * 从队列里拿出来n个
     *
     * @param n 每次拿出多少个Request
     * @return
     */
    @Override
    public List<RequestContext> poll(int n) {
        List<RequestContext> requestContexts = null;
        H2QueueConfig h2ComponentConfig = null;//(H2QueueConfig)getJscrapyConfig().get(ComponentName.QUEUE_H2);
        ReentrantLock taskQueueLock = h2ComponentConfig.getH2QueueLock();
        try {
            taskQueueLock.lock(); //lock
            List<UrlQueueDo> urls = selectUrl(n);
            requestContexts = toRequestContext(urls);
        } catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            taskQueueLock.unlock();
        }
        return requestContexts;
    }

    /**
     * 删除
     *
     * @param requests
     * @return
     */
    @Override
    public int delete(List<RequestContext> requests) {
        List<UrlQueueDo> urlQueueDos = toUrlQueueDo(requests);

        if (urlQueueDos.size() > 0) {
            String queueName = getJscrapyConfig().getTaskFp();
            urlQueueMapper.batchUpdate(queueName, urlQueueDos);
        }

        return 0;
    }

    /**
     *
     * @param n
     * @return
     */
    private List<UrlQueueDo> selectUrl(int n) {
        String queueName = getJscrapyConfig().getTaskFp();
        List<UrlQueueDo> urls = urlQueueMapper.selectUrlByStatus(queueName,
                UrlStatus.NEW,
                n);

        int m = n - urls.size();
        if (m > 0) {//找已经出队列的类型补全N个
            List<UrlQueueDo> urlsOutQueue = urlQueueMapper.selectUrlByStatus(queueName,
                    UrlStatus.OUT_QUEUE, n);
            urls.addAll(urlsOutQueue);
        }
        // Update {gmt_access,url_status}
        if (urls.size() > 0) {
            urlQueueMapper.batchUpdateUrlStatus(queueName, UrlStatus.OUT_QUEUE, urls);
        }

        return urls;
    }

    /**
     * @return
     */
    private List<RequestContext> toRequestContext(List<UrlQueueDo> urlQueueDos) {
        List<RequestContext> requestContexts = new ArrayList<>();
        for (UrlQueueDo urlQueueDo : urlQueueDos) {
            requestContexts.add(new RequestContext(urlQueueDo));
        }

        return requestContexts;
    }

    /**
     *
     * @param requestContexts
     * @return
     */
    private List<UrlQueueDo> toUrlQueueDo(List<RequestContext> requestContexts) {
        List<UrlQueueDo> urlQueueDos = new ArrayList<>();
        for (RequestContext rcx : requestContexts) {
            urlQueueDos.add(rcx.toUrlQueueDo());
        }

        return  urlQueueDos;
    }

    /**
     *
     * @param jscrapyConfig
     */
    public void setJscrapyConfig(JscrapyConfig jscrapyConfig) {
        super.setJscrapyConfig(jscrapyConfig);
        urlQueueMapper.createNewQueue(jscrapyConfig.getTaskFp());
    }
}
