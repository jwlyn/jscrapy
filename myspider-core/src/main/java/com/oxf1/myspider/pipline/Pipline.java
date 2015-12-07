package com.oxf1.myspider.pipline;

import com.oxf1.myspider.TaskConfig;
import com.oxf1.myspider.component.MyspiderComponent;
import com.oxf1.myspider.data.DataItem;
import com.oxf1.myspider.exception.MySpiderFetalException;

import java.util.List;

/**
 * Created by cxu on 2014/11/21.
 */
public abstract class Pipline extends MyspiderComponent{

    public Pipline(TaskConfig taskConfig){
        super(taskConfig);
    }

    /**
     * 保存解析之后的数据
     * @param dataItems 要保存的数据
     */
    public abstract void save(List<DataItem> dataItems) throws MySpiderFetalException;

}
