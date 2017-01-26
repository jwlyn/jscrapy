package org.jscrapy.core.dal.pg;

import org.jscrapy.core.dal.UrlQueueDo;
import org.jscrapy.core.request.UrlStatus;
import org.jscrapy.core.request.UrlType;

import java.util.Date;

/**
 * Created by cxu on 2016/8/10.
 */
public class PgUrlQueueDo extends UrlQueueDo {

    public PgUrlQueueDo(Long id, String schedId, String url, UrlStatus urlStatus, Integer retryTimes, UrlType urlType, String siteId, Date gmtCreated, Date gmtAccess, String errorCode, String errorMsg) {
        super(id, schedId, url, urlStatus, retryTimes, urlType, siteId, gmtCreated, gmtAccess, errorCode, errorMsg);
    }

    public PgUrlQueueDo() {

    }
}
