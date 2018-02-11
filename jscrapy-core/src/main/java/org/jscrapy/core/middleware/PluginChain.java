package org.jscrapy.core.middleware;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * Created by cxu on 2018/2/11.
 */
public class PluginChain<T> {
    //切点前方法链
    protected List<T> beforePluginChain;
    //切点后方法
    protected List<T> afterPluginChain;

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
    private List<T> scanOrderedPlugin(String pkgName) {
        Map<Integer, T> methodSortMap = new TreeMap<Integer, T>();
        for (String className : beforeClassNameList) {
            T beforeAction = (T) SpiderCoreSpringContextUtil.getBean(className);
            Class beforeSuperClass = beforeAction.getClass().getSuperclass();
            ActionOrder ActionOrderAnno = (ActionOrder) beforeSuperClass.getAnnotation(ActionOrder.class);
            int order = ActionOrderAnno.order();
            methodSortMap.put(order, beforeAction);
        }
        List<T> resultMethodList = new ArrayList<T>();
        for (int key : methodSortMap.keySet()) {
            resultMethodList.add(methodSortMap.get(key));
        }

        return resultMethodList;
    }
}
