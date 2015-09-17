package com.oxf1.spider.pipline.impl;

import com.oxf1.spider.TaskConfig;
import com.oxf1.spider.config.ConfigKeys;
import com.oxf1.spider.data.DataItem;
import com.oxf1.spider.pipline.Pipline;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * Created by cxu on 2015/6/21.
 */
public class LocalFilePipline extends Pipline {

    private ReentrantReadWriteLock lock;
    private String dataFilePath;//物理的数据文件位置path+file
    private String baseDir;//放数据文件的目录

    /**
     * @param taskConfig
     * @throws IOException
     */
    public LocalFilePipline(TaskConfig taskConfig) {
        super(taskConfig);
        this.dataFilePath = taskConfig.loadString(taskConfig, ConfigKeys.LOCAL_FILE_PIPLINE_DATA_SAVE_PATH);//完整的目录+文件名字。解析之后的数据保存的位置
        this.baseDir = FilenameUtils.getFullPath(dataFilePath);
        lock = new ReentrantReadWriteLock();
    }

    @Override
    public void save(DataItem dataItem) {
        if (dataItem != null) {
            try{
                FileUtils.forceMkdir(new File(baseDir));
                File f = new File(dataFilePath);
                lock.writeLock().lock();
                FileUtils.writeLines(new File(dataFilePath), dataItem.getData(), StandardCharsets.UTF_8.name(), true);
                lock.writeLock().unlock();
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
