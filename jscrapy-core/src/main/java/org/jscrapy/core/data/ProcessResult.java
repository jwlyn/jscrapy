package org.jscrapy.core.data;

import org.apache.commons.lang3.StringUtils;
import org.jscrapy.common.datetime.DatetimeUtil;
import org.jscrapy.core.request.HttpRequest;
import org.jscrapy.core.request.HttpRequestMethod;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by cxu on 2015/10/1.
 */
public class ProcessResult {
    private static final String FIELD_CREATE_TIME = "_create_time";
    private static final String FIELD_URL = "_from_url";
    private static final String SCHEDULER_VERSION = "_scheduler_version";

    private HttpRequest request;
    private String schedulerVersion;
    private List<DataItem> data;
    private List<HttpRequest> links;

    public ProcessResult() {
        data = new LinkedList<>();
        links = new LinkedList<>();
    }

    public ProcessResult addDataItem(DataItem item) {
        if(item!=null && !item.isEmpty()) {
            item = addOptionField(item);
            data.add(item);
        }

        return this;
    }

    public ProcessResult addDataItem(List<DataItem> items) {
        if (items != null && items.size() > 0) {
            for (DataItem item : items) {
                if (!item.isEmpty()) {
                    item = addOptionField(item);
                    data.add(item);
                }
            }
        }
        return this;
    }

    public ProcessResult addLinks(String link) {
        if (StringUtils.isNotBlank(link)) {
            HttpRequest req = new HttpRequest(link, HttpRequestMethod.GET, null);
            links.add(req);
        }

        return this;
    }

    public ProcessResult addLinks(List<String> links) {
        if (links != null) {
            for (String s : links) {
                HttpRequest req = new HttpRequest(s, HttpRequestMethod.GET, null);
                this.links.add(req);
            }
        }
        return this;
    }

    public ProcessResult addRequest(HttpRequest link) {
        if (link != null) {
            links.add(link);
        }
        return this;
    }

    public ProcessResult addRequest(List<HttpRequest> links) {
        if (links != null && links.size() > 0) {
            this.links.addAll(links);
        }
        return this;
    }

    public List<DataItem> getData() {
        return data;
    }

    public List<HttpRequest> getLinks() {
        return links;
    }

    private String getTimeStrNow() {
        return DatetimeUtil.getTime("yyyy-MM-dd HH:mm:ss");
    }

    public void setRequest(HttpRequest request) {
        this.request = request;
    }

    public String getSchedulerVersion() {
        return schedulerVersion;
    }

    public void setSchedulerVersion(String schedulerVersion) {
        this.schedulerVersion = schedulerVersion;
    }

    private DataItem addOptionField(DataItem item) {
        item.put(FIELD_CREATE_TIME, getTimeStrNow());
        item.put(FIELD_URL, request.asJson());
        item.put(SCHEDULER_VERSION, schedulerVersion);
        return item;
    }
}
