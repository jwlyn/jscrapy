package org.jscrapy.core.middleware;

import java.lang.annotation.*;

/**
 * Created by cxu on 2018/2/11.
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface PluginChainOrder {

    /**
     * 插件顺序,序号越小在链路上处于最先被调用
     * @return
     */
    public int order() default 0;
}
