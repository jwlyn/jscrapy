package com.oxf1.myspider.common.datetime;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by cxu on 2015/11/14.
 */
public class DatetimeUtil {

    public static String getTime(String format) {
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.format(new Date());
    }
}
