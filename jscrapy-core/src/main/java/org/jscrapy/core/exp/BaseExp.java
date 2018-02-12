package org.jscrapy.core.exp;

/**
 * Created by cxu on 2018/2/12.
 */
public class BaseExp extends RuntimeException {
    private String errCode;
    private String errMsg;

    public BaseExp() {

    }

    public BaseExp(String errCode, String errMsg) {
        this.errCode = errCode;
        this.errMsg = errMsg;
    }

    public String getErrCode() {
        return errCode;
    }

    public void setErrCode(String errCode) {
        this.errCode = errCode;
    }

    public String getErrMsg() {
        return errMsg;
    }

    public void setErrMsg(String errMsg) {
        this.errMsg = errMsg;
    }
}
