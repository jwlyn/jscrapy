package org.jscrapy.core.pipline.plugin.before;

import org.jscrapy.core.plugin.OrderValue;
import org.jscrapy.core.plugin.Plugin;
import org.jscrapy.core.plugin.PluginOrder;

/**
 * Created by cxu on 2018/2/11.
 */
@PluginOrder(OrderValue.AF_PIP_DEF_FIELD_ADD)
public class DefaultFieldsAddPlugin extends Plugin{
    @Override
    public boolean doAction(Object context) {
        return false;
    }
}
