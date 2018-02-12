package org.jscrapy.core.plugin;

import cn.hutool.core.util.ClassUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

import java.util.*;

/**
 * Created by cxu on 2018/2/11.
 */
public class PluginChain<Plugin> {
    //切点前方法链
    protected List<Plugin> beforePluginChain;
    //切点后方法
    protected List<Plugin> afterPluginChain;

    @Autowired
    private static ApplicationContext applicationContext;

    protected PluginChain(String basePkgName){
        initBeforePluginChain(basePkgName);
        initAfterPluginChain(basePkgName);
    }

    /**
     *
     * @param basePkgName
     */
    private void initBeforePluginChain(String basePkgName) {
        String fullPkgName = basePkgName + ".before";
        beforePluginChain = scanOrderedPlugin(fullPkgName);
    }

    /**
     *
     * @param basePkgName
     */
    private void initAfterPluginChain(String basePkgName) {
        String fullPkgName = basePkgName + ".after";
        afterPluginChain = scanOrderedPlugin(fullPkgName);
    }

    /**
     * 扫描pkgName里的全部非抽象类,并按照PluginChainOrder从小到大排列
     * @param pkgName
     * @return
     */
    private List<Plugin> scanOrderedPlugin(String pkgName) {
        Map<Integer, Plugin> orderedPluginMap = new TreeMap<Integer, Plugin>();
        Set<Class<?>> plugins = ClassUtil.scanPackage(pkgName);
        for (Class c : plugins) {//探测每一个Plugin在Chain中的顺序，方便后面排序
            PluginOrder pluginOrder = (PluginOrder)c.getAnnotation(PluginOrder.class);
            int order = pluginOrder.value();
            String className = c.getName();//full package name
            Plugin plugin = (Plugin)applicationContext.getBean(className);
            orderedPluginMap.put(order, plugin);
        }

        /**
         * 对Plugin进行排序
         */
        List resultMethodList = new ArrayList<Plugin>();
        for (Integer order : orderedPluginMap.keySet()) {
            resultMethodList.add(orderedPluginMap.get(order));
        }

        return resultMethodList;
    }
}
