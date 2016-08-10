package org.jscrapy.core.dal;

import java.util.Date;

/**
 * Created by cxu on 2016/8/10.
 */
public class UrlQueueDo {
    private long id;
    private String url;
    private String urlStatus;
    private int retryTimes;
    private String urlType;
    private int siteId;
    private Date gmtCreated;
    private Date gmtAccess;
    private String errorCode;
    private String errorMsg;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUrlStatus() {
        return urlStatus;
    }

    public void setUrlStatus(String urlStatus) {
        this.urlStatus = urlStatus;
    }

    public int getRetryTimes() {
        return retryTimes;
    }

    public void setRetryTimes(int retryTimes) {
        this.retryTimes = retryTimes;
    }

    public String getUrlType() {
        return urlType;
    }

    public void setUrlType(String urlType) {
        this.urlType = urlType;
    }

    public int getSiteId() {
        return siteId;
    }

    public void setSiteId(int siteId) {
        this.siteId = siteId;
    }

    public Date getGmtCreated() {
        return gmtCreated;
    }

    public void setGmtCreated(Date gmtCreated) {
        this.gmtCreated = gmtCreated;
    }

    public Date getGmtAccess() {
        return gmtAccess;
    }

    public void setGmtAccess(Date gmtAccess) {
        this.gmtAccess = gmtAccess;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }
}
