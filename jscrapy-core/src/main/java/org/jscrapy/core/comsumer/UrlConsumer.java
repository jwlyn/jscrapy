package org.jscrapy.core.comsumer;

import org.jscrapy.core.JscrapyComponent;
import org.jscrapy.core.memqueue.MemQueue;
import org.jscrapy.core.request.RequestContext;

import java.util.List;

/**
 * Created by cxu on 2016/7/27.
 */
public abstract class UrlConsumer extends JscrapyComponent {

    protected MemQueue memQueue;//用于缓存从集中队列里取出来的request

    /**
     * 从集中队列中拿出Request
     *
     * @param n 每次拿出多少个Request
     * @return 没有则返回空的对象而不是null
     */
    public abstract List<RequestContext> poll(int n);

    /**
     * 从队列中删除Request
     *
     * @param requests
     * @return
     */
    public abstract int delete(List<RequestContext> requests);

    public void setMemQueue(MemQueue memQueue){
        this.memQueue = memQueue;
    }

    public MemQueue getMemQueue() {
        return memQueue;
    }
}
