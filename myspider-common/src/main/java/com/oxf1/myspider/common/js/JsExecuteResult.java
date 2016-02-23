package com.oxf1.myspider.common.js;

/**
 * Created by cxu on 2016/2/23.
 */
public class JsExecuteResult {

    private  Object		result;//返回的结果数据
    private  Boolean	isSuccess = Boolean.FALSE;//是否成功
    private  String		message;//错误原因等

    /**
     * @return the result
     */
    public Object getResult() {
        return result;
    }
    /**
     * @param result the result to set
     */
    public void setResult(Object result) {
        this.result = result;
    }
    /**
     * @return the isSuccess
     */
    public Boolean getIsSuccess() {
        return isSuccess;
    }
    /**
     * @param isSuccess the isSuccess to set
     */
    public void setIsSuccess(Boolean isSuccess) {
        this.isSuccess = isSuccess;
    }
    /**
     * @return the message
     */
    public String getMessage() {
        return message;
    }
    /**
     * @param message the message to set
     */
    public void setMessage(String message) {
        this.message = message;
    }
}
