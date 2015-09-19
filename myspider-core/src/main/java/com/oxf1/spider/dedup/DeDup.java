package com.oxf1.spider.dedup;

import com.oxf1.spider.TaskConfig;
import com.oxf1.spider.component.MyspiderComponent;
import com.oxf1.spider.request.Request;

import java.util.ArrayList;
import java.util.List;

/**
 * Request去重
 * Created by cxu on 2015/6/22.
 */
public abstract class DeDup extends MyspiderComponent{

    public DeDup(TaskConfig taskConfig) {
        super(taskConfig);
    }

    /**
     * 测试是否是重复的
     * @param request
     * @return
     */
    protected abstract boolean isDup(Request request);

    /**
     * 返回非重复的
     * @param request
     * @return
     */
    public List<Request> deDup(List<Request> request){
        List<Request> req = new ArrayList<Request>(request.size());
        for(Request url : request){
            if(!this.isDup(url)){
                req.add(url);
            }
        }
        return req;
    }
}
