package org.jscrapy.core.producer;

import org.jscrapy.core.request.Request;

import java.util.List;

/**
 * Created by cxu on 2016/7/27.
 */
public abstract class UrlProducer {
    /**
     * 将Request插入到请求队列中
     *
     * @param requests
     * @return 插入队列的实际Request数目
     */
    public abstract int push(List<Request> requests);

    /**
     * 更新某些字段
     * @param requests
     */
    public abstract void update(List<Request> requests);
}
