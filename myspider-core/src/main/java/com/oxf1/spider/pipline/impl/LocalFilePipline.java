package com.oxf1.spider.pipline.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.oxf1.spider.TaskConfig;
import com.oxf1.spider.config.ConfigKeys;
import com.oxf1.spider.config.SysDefaultConfig;
import com.oxf1.spider.data.DataItem;
import com.oxf1.spider.pipline.Pipline;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;

/**
 * Created by cxu on 2015/6/21.
 */
public class LocalFilePipline extends Pipline {
    final static Logger logger = LoggerFactory.getLogger(LocalFilePipline.class);
    private String dataFilePath;//物理的数据文件位置path+file

    /**
     * @param taskConfig
     * @throws IOException
     */
    public LocalFilePipline(TaskConfig taskConfig){

        super(taskConfig);
        String spiderWorkDir = taskConfig.getTaskWorkDir();
        if(StringUtils.isBlank(spiderWorkDir)){
            spiderWorkDir = SysDefaultConfig.DEFAULT_SPIDER_WORK_DIR;
        }
        this.dataFilePath = spiderWorkDir + taskConfig.getTaskFp() + File.separator + "pipline" + File.separator + taskConfig.getTaskName() + ".json";//完整的目录+文件名字。解析之后的数据保存的位置
        taskConfig.put(ConfigKeys.RT_LOCAL_FILE_PIPLINE_DATA_FILE, this.dataFilePath);
        String baseDir = FilenameUtils.getFullPath(dataFilePath);
        try {
            FileUtils.forceMkdir(new File(baseDir));
        } catch (IOException e) {
            //TODO exp
            e.printStackTrace();
            logger.error("创建目录{}失败 {}", baseDir, e);
        }
    }

    @Override
    public void save(List<DataItem> dataItems) {
        if (dataItems != null && dataItems.size()>0) {
            for (DataItem dataItem : dataItems) {
                try {
                    File dataFile = new File(dataFilePath);
                    ObjectMapper mapper = new ObjectMapper();
                    String data = mapper.writeValueAsString(dataItem.getDataItem());
                    synchronized (super.getTaskConfig()) {//任务级别的锁，只锁住同一个任务的多个线程
                        FileUtils.writeStringToFile(dataFile, data + "\n", StandardCharsets.UTF_8.name(), true);
                    }
                } catch (IOException e) {
                    //TODO
                    e.printStackTrace();
                    logger.error("写文件{}失败{}", dataFilePath, e);
                }
            }
        }
    }

    @Override
    public void close() {
        String baseDir = FilenameUtils.getFullPath(dataFilePath);
        try {
            FileUtils.deleteDirectory(new File(baseDir));
        } catch (IOException e) {
            e.printStackTrace();
            //TODO log it
            logger.error("删除目录{}时失败{}", baseDir, e);
        }
    }
}
