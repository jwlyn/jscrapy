package org.jscrapy.core.memqueue;

import org.jscrapy.core.request.HttpRequest;

import java.util.List;

/**
 * Created by cxu on 2018/2/9.
 */
public abstract class MemQueue {

    public abstract int push(List<HttpRequest> requests);

    public abstract HttpRequest poll();
}
