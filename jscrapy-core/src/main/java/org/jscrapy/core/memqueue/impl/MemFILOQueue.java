package org.jscrapy.core.memqueue.impl;

import org.jscrapy.core.memqueue.MemQueue;
import org.jscrapy.core.request.HttpRequest;

import java.util.List;

/**
 * Created by cxu on 2018/2/9.
 */
public class MemFILOQueue extends MemQueue {
    @Override
    public int push(List<HttpRequest> requests) {
        return 0;
    }

    @Override
    public HttpRequest poll() {
        return null;
    }
}
