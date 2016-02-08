package com.oxf1.myspider.slave.controller.response;

/**
 * Created by cxu on 2015/10/31.
 */
public class ResponseBase {
    private boolean isSuccess;
    private String errorCode;
    private String errorReason;

    public ResponseBase() {

    }

    public ResponseBase(boolean isSuccess, String errorCode, String errorReason) {
        this.isSuccess = isSuccess;
        this.errorCode = errorCode;
        this.errorReason = errorReason;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorReason() {
        return errorReason;
    }

    public void setErrorReason(String errorReason) {
        this.errorReason = errorReason;
    }

    public boolean isSuccess() {
        return isSuccess;
    }

    public void setIsSuccess(boolean isSuccess) {
        this.isSuccess = isSuccess;
    }
}
