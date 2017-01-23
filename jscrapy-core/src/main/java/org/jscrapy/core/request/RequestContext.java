package org.jscrapy.core.request;

import org.jscrapy.core.dal.UrlQueueDo;

import java.util.Date;
import java.util.Map;

/**
 * Created by cxu on 2017/1/19.
 */
public class RequestContext {
    private Request request;
    private UrlQueueDo urlQueueDo;

    public RequestContext() {
    }

    public RequestContext(Request request, UrlQueueDo urlQueueDo) {
        this.request = request;
        this.urlQueueDo = urlQueueDo;
    }

    public String getUrl() {
        return request.getUrl();
    }

    public String getFullUrl() {
        return request.asJson();
    }

    public HttpRequestMethod getHttpMethod() {
        return request.getHttpMethod();
    }

    public Map<String, String> getParameters() {
        return request.getParameters();
    }

    public String asJson() {
        return request.asJson();
    }

    public String fp() {
        return request.fp();
    }
    public void setUrl(String url) {
        request.setUrl(url);
    }

    public String getUrlStatus() {
        return urlQueueDo.getUrlStatus();
    }

    public long getId() {
        return urlQueueDo.getId();
    }

    public void setId(long id) {
        urlQueueDo.setId(id);
    }

    public void setUrlStatus(String urlStatus) {
        urlQueueDo.setUrlStatus(urlStatus);
    }

    public int getRetryTimes() {
        return urlQueueDo.getRetryTimes();
    }

    public void setRetryTimes(int retryTimes) {
        urlQueueDo.setRetryTimes(retryTimes);
    }

    public String getUrlType() {
        return urlQueueDo.getUrlType();
    }

    public void setUrlType(String urlType) {
        urlQueueDo.setUrlType(urlType);
    }

    public String getSiteId() {
        return urlQueueDo.getSiteId();
    }

    public void setSiteId(String siteId) {
        urlQueueDo.setSiteId(siteId);
    }

    public Date getGmtCreated() {
        return urlQueueDo.getGmtCreated();
    }

    public void setGmtCreated(Date gmtCreated) {
        urlQueueDo.setGmtCreated(gmtCreated);
    }

    public Date getGmtAccess() {
        return urlQueueDo.getGmtAccess();
    }

    public void setGmtAccess(Date gmtAccess) {
        urlQueueDo.setGmtAccess(gmtAccess);
    }

    public String getErrorCode() {
        return urlQueueDo.getErrorCode();
    }

    public void setErrorCode(String errorCode) {
        urlQueueDo.setErrorCode(errorCode);
    }

    public String getErrorMsg() {
        return urlQueueDo.getErrorMsg();
    }

    public void setErrorMsg(String errorMsg) {
        urlQueueDo.setErrorMsg(errorMsg);
    }
}
