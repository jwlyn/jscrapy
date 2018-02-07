package org.jscrapy.core.dedup.impl;

import org.jscrapy.core.dedup.DeDup;
import org.jscrapy.core.request.Request;

/**
 * Created by cxu on 2018/2/5.
 */
public class H2Dedup extends DeDup {
    @Override
    protected boolean isDup(Request request) {
        return false;
    }
}
