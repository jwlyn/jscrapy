package org.jscrapy.core.plugin;

/**
 * Created by cxu on 2018/2/12.
 */
public abstract class Plugin<T> {

    /**
     * pluginChain节点接口。
     * 每个plugin需要继承这个类，完成一个任务
     * @param context
     * @return false: 如果中断本次链条。true：如果要继续下一个
     */
    public abstract boolean doAction(T context);
}
