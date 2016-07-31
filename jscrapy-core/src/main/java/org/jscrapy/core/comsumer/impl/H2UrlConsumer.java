package org.jscrapy.core.comsumer.impl;

import org.jscrapy.core.comsumer.UrlConsumer;
import org.jscrapy.core.request.Request;

import java.util.List;

/**
 * Created by cxu on 2016/7/30.
 */
public class H2UrlConsumer extends UrlConsumer {

    @Override
    public List<Request> poll(int n) {
        return null;
    }

    @Override
    public int delete(List<Request> requests) {
        return 0;
    }
}
