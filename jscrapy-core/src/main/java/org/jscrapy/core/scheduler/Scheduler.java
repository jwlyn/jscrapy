package org.jscrapy.core.scheduler;

import org.jscrapy.core.config.ConfigDriver;
import org.jscrapy.core.config.JscrapyConfig;
import org.jscrapy.core.exception.MySpiderException;
import org.jscrapy.core.request.Request;

import java.util.List;

/**
 * Created by cxu on 2014/11/21.
 */
public abstract class Scheduler extends ConfigDriver {

    public Scheduler(JscrapyConfig jscrapyConfig) {
        super(jscrapyConfig);
    }

    /**
     * 将Request插入到请求队列中
     *
     * @param requests
     * @return 插入队列的实际Request数目
     */
    public abstract int push(List<Request> requests) throws MySpiderException;

    /**
     * 从队列中拿出Request
     *
     * @param n 每次拿出多少个Request
     * @return
     */
    public abstract List<Request> poll(int n) throws MySpiderException;

    /**
     * 从队列中删除Request
     *
     * @param requests
     * @return
     */
    public abstract int remove(List<Request> requests);

}
