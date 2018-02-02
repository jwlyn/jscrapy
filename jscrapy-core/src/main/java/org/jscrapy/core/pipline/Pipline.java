package org.jscrapy.core.pipline;

import org.jscrapy.core.ConfigDriver;
import org.jscrapy.core.config.JscrapyConfig;
import org.jscrapy.core.data.DataItem;
import org.jscrapy.core.exception.MySpiderFetalException;

import java.util.List;

/**
 * Created by cxu on 2014/11/21.
 */
public abstract class Pipline extends ConfigDriver {

    public Pipline(JscrapyConfig JscrapyConfig) {
        setJscrapyConfig(JscrapyConfig);
    }

    public Pipline() {

    }

    /**
     * 保存解析之后的数据
     *
     * @param dataItems 要保存的数据
     */
    public abstract void save(List<DataItem> dataItems) throws MySpiderFetalException;

}
