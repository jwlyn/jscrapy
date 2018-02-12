package org.jscrapy.core.plugin;

import java.lang.annotation.*;

/**
 * 在一条链条上的插件的执行顺序，序号越小越先执行
 * Created by cxu on 2018/2/11.
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface PluginOrder {

    /**
     * 插件顺序,序号越小在链路上处于最先被调用
     * @return
     */
    public int value() default 0;
}
