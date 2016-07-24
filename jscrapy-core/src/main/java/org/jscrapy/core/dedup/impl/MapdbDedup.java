package org.jscrapy.core.dedup.impl;

import org.jscrapy.core.TaskConfig;
import org.jscrapy.core.config.cfgkey.ConfigKeys;
import org.jscrapy.core.dedup.DeDup;
import org.jscrapy.core.exception.MySpiderFetalException;
import org.jscrapy.core.exception.MySpiderRecoverableException;
import org.jscrapy.core.request.Request;
import org.mapdb.BTreeKeySerializer;
import org.mapdb.BTreeMap;
import org.mapdb.DB;
import org.mapdb.DBMaker;

import java.io.File;


/**
 * 基于mapdb的大容量单机去重
 * Created by cxu on 2015/12/8.
 */
public class MapdbDedup extends DeDup {

    public MapdbDedup(TaskConfig taskConfig) throws MySpiderFetalException {

        super(taskConfig);
        if(taskConfig.getTaskSharedObject(ConfigKeys._DEDUP_DISK_SET_OBJ)==null){
            synchronized (taskConfig){
                if(taskConfig.getTaskSharedObject(ConfigKeys._DEDUP_DISK_SET_OBJ)==null){
                    String setFilePath = getDiskSetPath();
                    taskConfig.put(ConfigKeys.RT_EXT_RT_LOCAL_QUEUE_DIR, setFilePath);

                    DB db = DBMaker.fileDB(new File(setFilePath))
                            //.cacheSize(100000)
                            .make();
                    BTreeMap<String, Character> existUrl = db.treeMapCreate("map")
                            .keySerializer(BTreeKeySerializer.STRING)
                            .nodeSize(64)
                            .makeOrGet();

                    taskConfig.addTaskSharedObject(ConfigKeys._DEDUP_DISK_SET_OBJ, existUrl);
                }
            }
        }
    }

    @Override
    protected boolean isDup(Request request) {
        BTreeMap<String, Character> existUrl = (BTreeMap<String, Character>)getTaskConfig().getTaskSharedObject(ConfigKeys._DEDUP_DISK_SET_OBJ);
        String id = request.fp();
        Character ret = existUrl.putIfAbsent(id, '1');
        return ret!=null;
    }

    @Override
    public void close() throws MySpiderRecoverableException {

    }

    /**
     * 获取到mapdb的物理文件存储地址
     * @return
     */
    private String getDiskSetPath() {
        TaskConfig taskConfig = getTaskConfig();
        String spiderWorkDir = taskConfig.getSpiderWorkDir();
        String setFilePath = spiderWorkDir + taskConfig.getTaskFp() + File.separator + "dedup" + File.separator + "dedup_set.dump";
        return setFilePath;
    }
}
