package org.jscrapy.core.producer.impl;

import org.jscrapy.core.config.JscrapyConfig;
import org.jscrapy.core.dal.UrlQueueDo;
import org.jscrapy.core.dal.h2.H2UrlQueueDo;
import org.jscrapy.core.dal.h2.H2UrlQueueMapper;
import org.jscrapy.core.producer.UrlProducer;
import org.jscrapy.core.request.RequestContext;
import org.jscrapy.core.request.UrlStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by cxu on 2017/1/19.
 */
@Component("h2UrlProducer")
public class H2UrlProducer extends UrlProducer {

    @Autowired
    private H2UrlQueueMapper h2UrlQueueMapper;

    @Override
    public int push(List<RequestContext> requests) {
        int inserted = 0;
        if (requests != null) {
            String tableName = getJscrapyConfig().getTaskFp();
            List<UrlQueueDo> newUrl = getNew(requests);
            h2UrlQueueMapper.batchInsert(tableName, newUrl);

            inserted = requests.size();
        }else{
            inserted = 0;
        }

        return inserted;
    }

    @Override
    public void update(List<RequestContext> requests) {

    }

    /**
     * 入队列的时候有的url是因为错误原因再次被放入到队列里，
     * 这个函数只要找出来新产生的
     * @param requests
     * @return
     */
    private List<UrlQueueDo> getNew(List<RequestContext> requests) {
        List<UrlQueueDo> newReq = new ArrayList<>();
        for (RequestContext requestContext : requests) {
            UrlQueueDo urlQueueDo = new H2UrlQueueDo();
            Long id = requestContext.getId();
            if (id == null) {
                urlQueueDo.setUrl(requestContext.getFullUrl());
                urlQueueDo.setUrlStatus(UrlStatus.NEW);
                urlQueueDo.setRetryTimes(0);
                urlQueueDo.setUrlType(requestContext.getUrlType());
                urlQueueDo.setSiteId(requestContext.getSiteId());
                newReq.add(urlQueueDo);
            }
        }

        return newReq;
    }

    @Override
    public void setJscrapyConfig(JscrapyConfig jscrapyConfig) {
        super.setJscrapyConfig(jscrapyConfig);
        h2UrlQueueMapper.createNewQueue(jscrapyConfig.getTaskFp());
    }
}
