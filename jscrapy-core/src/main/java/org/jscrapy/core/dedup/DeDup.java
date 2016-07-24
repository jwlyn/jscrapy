package org.jscrapy.core.dedup;

import org.jscrapy.core.TaskConfig;
import org.jscrapy.core.component.MyspiderComponent;
import org.jscrapy.core.request.Request;

import java.util.ArrayList;
import java.util.List;

/**
 * Request去重
 * Created by cxu on 2015/6/22.
 */
public abstract class DeDup extends MyspiderComponent {

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
