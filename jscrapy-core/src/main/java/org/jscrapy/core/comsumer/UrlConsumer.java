package org.jscrapy.core.comsumer;

import org.jscrapy.core.exception.MySpiderException;
import org.jscrapy.core.request.Request;

import java.util.List;

/**
 * Created by cxu on 2016/7/27.
 */
public abstract class UrlConsumer {

    /**
     * 从集中队列中拿出Request
     *
     * @param n 每次拿出多少个Request
     * @return
     */
    public abstract List<Request> poll(int n);

    /**
     * 从队列中删除Request
     *
     * @param requests
     * @return
     */
    public abstract int delete(List<Request> requests);
}
