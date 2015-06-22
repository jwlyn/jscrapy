package com.oxf1.spider.dudup;

import com.oxf1.spider.TaskId;
import com.oxf1.spider.component.MyspiderComponent;
import com.oxf1.spider.request.Request;

import java.util.List;

/**
 * Request去重
 * Created by cxu on 2015/6/22.
 */
public abstract class DeDup extends MyspiderComponent{

    public DeDup(TaskId taskid) {
        super(taskid);
    }

    /**
     * 测试是否是重复的
     * @param request
     * @return
     */
    public abstract boolean isDup(Request request);

    /**
     * 返回非重复的
     * @param request
     * @return
     */
    public abstract List<Request> deDup(List<Request> request);
}
