package org.jscrapy.core.producer;

import org.jscrapy.core.JscrapyComponent;
import org.jscrapy.core.request.RequestContext;

import java.util.List;

/**
 * Created by cxu on 2016/7/27.
 */
public abstract class UrlProducer extends JscrapyComponent {
    /**
     * 将Request插入到请求队列中
     *
     * @param requests
     * @return 插入队列的实际Request数目
     */
    public abstract int push(List<RequestContext> requests);

    /**
     * 更新某些字段
     * @param requests
     */
    public abstract void update(List<RequestContext> requests);
}
