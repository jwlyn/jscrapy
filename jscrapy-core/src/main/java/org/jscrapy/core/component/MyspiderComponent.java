package org.jscrapy.core.component;

import org.jscrapy.common.log.TaskLogUtil;
import org.jscrapy.core.TaskConfig;
import org.jscrapy.core.exception.MySpiderRecoverableException;
import org.slf4j.Logger;
import org.slf4j.helpers.MessageFormatter;

import java.util.ArrayList;


/**
 * Created by cxu on 2015/6/22.
 */
public abstract class MyspiderComponent {
    private final TaskConfig taskConfig;

    public MyspiderComponent(TaskConfig taskConfig){
        this.taskConfig = taskConfig;
    }
    public abstract void close() throws MySpiderRecoverableException;

    public void log(Logger logger, String method, String messagePattern, Object ... obj) {
        ArrayList<Object> objs = new ArrayList<>();
        for (Object o : obj) {
            objs.add(o);
        }
        String msg = MessageFormatter.arrayFormat(messagePattern, objs.toArray()).getMessage();

        TaskLogUtil.log(logger, method, msg);
        TaskLogUtil.log(taskConfig.getTaskLogger(), method, msg);
    }

    protected TaskConfig getTaskConfig(){
        return this.taskConfig;
    }

}