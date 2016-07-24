package org.jscrapy.core.exception;

/**
 * Created by cxu on 2015/6/22.
 */
public class MySpiderFetalException extends MySpiderException {
    public MySpiderFetalException(MySpiderExceptionCode errorCode) {
        super(errorCode);
    }
}
