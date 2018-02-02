package org.jscrapy.core.processor;

import org.jscrapy.core.ConfigDriver;
import org.jscrapy.core.config.JscrapyConfig;
import org.jscrapy.core.data.ProcessResult;
import org.jscrapy.core.page.Page;
import org.jscrapy.core.processor.parser.Parser;

/**
 * Created by cxu on 2014/11/21.
 */
public abstract class Processor extends ConfigDriver {
    /**
     * [k, v] 列表
     * k: site_id
     * v: java 解析实例对象
     */
    protected ParsersTable parsersTable;

    public void setParsersTable(ParsersTable parsersTable) {
        this.parsersTable = parsersTable;
    }

    public Processor(JscrapyConfig jscrapyConfig) {
        setJscrapyConfig(jscrapyConfig);
    }

    public Processor() {

    }

    public ProcessResult process(Page page) {
        Parser parser = parsersTable.getProcessorRule();
        ProcessResult result = parser.parse(page);
        result.setRequest(page.getRequest());
        return result;
    }
}
