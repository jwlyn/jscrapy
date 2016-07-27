package org.jscrapy.contrib.dedup;

import org.jscrapy.core.config.JscrapyConfig;
import org.jscrapy.core.dedup.DeDup;
import org.jscrapy.core.request.Request;

import java.util.concurrent.ConcurrentHashMap;

/**
 * 使用jdk Set完成去重
 * Created by cxu on 2015/9/18.
 */
public class MemoryDedup extends DeDup {

    private ConcurrentHashMap<String, Character> existUrl = null;

    /**
     * 构造函数
     *
     * @param JscrapyConfig
     */
    public MemoryDedup(JscrapyConfig JscrapyConfig) {
        super(JscrapyConfig);
    }

    @Override
    protected boolean isDup(Request request) {
        String id = request.fp();
        Character ret = existUrl.putIfAbsent(id, '1');
        return ret != null;
    }
}
