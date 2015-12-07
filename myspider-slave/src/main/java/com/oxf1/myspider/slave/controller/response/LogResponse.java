package com.oxf1.myspider.slave.controller.response;

/**
 * Created by cxu on 2015/11/7.
 */
public class LogResponse extends ResponseBase {

    private long curOffset;
    private String logContent;//日志内容

    public LogResponse(String logContent) {
        this.logContent = logContent;
    }

    public String getLogContent() {
        return logContent;
    }

    public void setLogContent(String logContent) {
        this.logContent = logContent;
    }

    public long getCurOffset() {
        return curOffset;
    }

    public void setCurOffset(long curOffset) {
        this.curOffset = curOffset;
    }
}
