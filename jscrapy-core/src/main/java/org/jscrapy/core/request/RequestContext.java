package org.jscrapy.core.request;

import org.jscrapy.core.dal.UrlQueueDo;

import java.util.Date;
import java.util.Map;

/**
 * Created by cxu on 2017/1/19.
 */
public class RequestContext {
    private HttpRequest request;
    private UrlQueueDo urlQueueDo;

    public RequestContext() {
        request = new HttpRequest();
        urlQueueDo = new UrlQueueDo();
    }

    public RequestContext(UrlQueueDo urlQueueDo) {
        this.urlQueueDo = urlQueueDo;
        request = HttpRequest.build(urlQueueDo.getUrl());
    }

    public RequestContext(HttpRequest request, UrlQueueDo urlQueueDo) {
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
        return request.uniqId();
    }
    public void setUrl(String url) {
        request.setUrl(url);
    }



    public Long getId() {
        return urlQueueDo.getId();
    }

    public void setId(long id) {
        urlQueueDo.setId(id);
    }

    public void setUrlStatus(UrlStatus urlStatus) {
        urlQueueDo.setUrlStatus(urlStatus);
    }

    public UrlStatus getUrlStatus() {
        return urlQueueDo.getUrlStatus();
    }

    public int getRetryTimes() {
        return urlQueueDo.getRetryTimes();
    }

    public void setRetryTimes(Integer retryTimes) {
        urlQueueDo.setRetryTimes(retryTimes);
    }

    public UrlType getUrlType() {
        return urlQueueDo.getUrlType();
    }

    public void setUrlType(UrlType urlType) {
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

    public HttpRequest toHttpRequest() {
        //TODO
        return null;
    }

    public UrlQueueDo toUrlQueueDo() {
        //TODO
        return null;
    }
}
