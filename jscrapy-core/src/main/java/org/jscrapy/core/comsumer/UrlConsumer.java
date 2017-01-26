package org.jscrapy.core.comsumer;

import org.jscrapy.core.ConfigDriver;
import org.jscrapy.core.request.RequestContext;

import java.util.List;

/**
 * Created by cxu on 2016/7/27.
 */
public abstract class UrlConsumer extends ConfigDriver{

    /**
     * 从集中队列中拿出Request
     *
     * @param n 每次拿出多少个Request
     * @return
     */
    public abstract List<RequestContext> poll(int n);

    /**
     * 从队列中删除Request
     *
     * @param requests
     * @return
     */
    public abstract int delete(List<RequestContext> requests);
}
