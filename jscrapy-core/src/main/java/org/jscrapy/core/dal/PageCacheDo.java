package org.jscrapy.core.dal;

import java.util.Date;

/**
 * Created by cxu on 2017/1/8.
 */
public class PageCacheDo {
    private Long id;// record id
    private String pageId; //去重ID
    private Date gmtCreated;
    private Date gmtAccess;//服务器返回的最后修改时间
    private String etag;//服务器返回的etag
    private String pageContent;//网页内容

    public PageCacheDo() {

    }

    public PageCacheDo(Long id, String pageId, Date gmtCreated, Date gmtAccess, String etag, String pageContent) {
        this.id = id;
        this.pageId = pageId;
        this.gmtCreated = gmtCreated;
        this.gmtAccess = gmtAccess;
        this.etag = etag;
        this.pageContent = pageContent;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getPageId() {
        return pageId;
    }

    public void setPageId(String pageId) {
        this.pageId = pageId;
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

    public String getEtag() {
        return etag;
    }

    public void setEtag(String etag) {
        this.etag = etag;
    }

    public String getPageContent() {
        return pageContent;
    }

    public void setPageContent(String pageContent) {
        this.pageContent = pageContent;
    }
}
