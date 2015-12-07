package com.oxf1.myspider.data;

import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by cxu on 2015/6/21.
 */
public class DataItem {
    private Map<String, String> dataItem;

    public DataItem() {
        dataItem = new HashMap<>();
    }

    public DataItem put(String key, String value) {
        if (StringUtils.isNotBlank(value)) {
            dataItem.put(key.trim(), value.trim());
        }
        return this;
    }

    public Map<String, String> getDataItem() {
        return dataItem;
    }

    /**
     * 是否为空
     * @return
     */
    public boolean isEmpty(){
        return dataItem==null || dataItem.size()==0;
    }
}
