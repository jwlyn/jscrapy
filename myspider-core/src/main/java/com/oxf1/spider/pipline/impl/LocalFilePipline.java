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

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * Created by cxu on 2015/6/21.
 */
public class LocalFilePipline extends Pipline {

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
            //TODO
            e.printStackTrace();
        }
    }

    @Override
    public void save(DataItem dataItem) {
        if (dataItem != null) {
            try{
                File dataFile = new File(dataFilePath);
                ObjectMapper mapper = new ObjectMapper();
                String data = mapper.writeValueAsString(dataItem.getData());
                synchronized (super.getTaskConfig()){//任务级别的锁，只锁住同一个任务的多个线程
                    FileUtils.writeStringToFile(dataFile, data+"\n", StandardCharsets.UTF_8.name(), true);
                }
            }
            catch(IOException e){
                //TODO
                e.printStackTrace();
            }
        }
    }

    @Override
    public void close() {

    }
}
