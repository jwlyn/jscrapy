package org.jscrapy.core.dal;

import org.jscrapy.core.request.UrlStatus;
import org.jscrapy.core.request.UrlType;

import java.util.Date;

/**
 * Created by cxu on 2016/8/10.
 */
public class UrlQueueDo {
    private Long id;
    private String url;
    private String schedId;
    private UrlStatus urlStatus;
    private Integer retryTimes;
    private UrlType urlType;
    private String siteId;
    private Date gmtCreated;
    private Date gmtAccess;
    private String errorCode;
    private String errorMsg;

    public UrlQueueDo(Long id, String schedId,String url,  UrlStatus urlStatus, Integer retryTimes, UrlType urlType, String siteId, Date gmtCreated, Date gmtAccess, String errorCode, String errorMsg) {
        this.id = id;
        this.url = url;
        this.schedId = schedId;
        this.urlStatus = urlStatus;
        this.retryTimes = retryTimes;
        this.urlType = urlType;
        this.siteId = siteId;
        this.gmtCreated = gmtCreated;
        this.gmtAccess = gmtAccess;
        this.errorCode = errorCode;
        this.errorMsg = errorMsg;
    }

    public UrlQueueDo() {

    }


    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
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

    public String getSchedId() {
        return schedId;
    }

    public void setSchedId(String schedId) {
        this.schedId = schedId;
    }

    public UrlStatus getUrlStatus() {
        return urlStatus;
    }

    public void setUrlStatus(UrlStatus urlStatus) {
        this.urlStatus = urlStatus;
    }

    public Integer getRetryTimes() {
        return retryTimes;
    }

    public void setRetryTimes(Integer retryTimes) {
        this.retryTimes = retryTimes;
    }

    public UrlType getUrlType() {
        return urlType;
    }

    public void setUrlType(UrlType urlType) {
        this.urlType = urlType;
    }

    public String getSiteId() {
        return siteId;
    }

    public void setSiteId(String siteId) {
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
