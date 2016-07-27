package org.jscrapy.core.dedup.impl;

import org.jscrapy.core.config.JscrapyConfig;
import org.jscrapy.core.config.SysDefaultConfig;
import org.jscrapy.core.dedup.DeDup;
import org.jscrapy.core.exception.MySpiderFetalException;
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

    BTreeMap<String, Character> existUrl;

    public MapdbDedup(JscrapyConfig JscrapyConfig) throws MySpiderFetalException {

        super(JscrapyConfig);
        String setFilePath = getDiskSetPath();
        DB db = DBMaker.fileDB(new File(setFilePath))
                .make();
        existUrl = db.treeMapCreate("map")
                .keySerializer(BTreeKeySerializer.STRING)
                .nodeSize(64)
                .makeOrGet();
    }

    @Override
    protected boolean isDup(Request request) {
        String id = request.fp();
        Character ret = existUrl.putIfAbsent(id, '1');
        return ret != null;
    }

    /**
     * 获取到mapdb的物理文件存储地址
     *
     * @return
     */
    private String getDiskSetPath() {
        JscrapyConfig JscrapyConfig = getJscrapyConfig();
        String spiderWorkDir = JscrapyConfig.getSpiderWorkDir();
        String setFilePath = spiderWorkDir + JscrapyConfig.getTaskFp() + SysDefaultConfig.FILE_PATH_SEPERATOR + "dedup" + SysDefaultConfig.FILE_PATH_SEPERATOR + "dedup_set.dump";
        return setFilePath;
    }
}
