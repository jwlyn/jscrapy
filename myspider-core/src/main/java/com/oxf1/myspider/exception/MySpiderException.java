package com.oxf1.myspider.exception;

/**
 * Created by cxu on 2015/6/20.
 */
public class MySpiderException extends Exception{

    private int errorCode;
    private String errorMessage;

    public MySpiderException(MySpiderExceptionCode errorCode)
    {
        this.errorCode = errorCode.ordinal();
        this.errorMessage = errorCode.getErrorReason();
    }

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

}
