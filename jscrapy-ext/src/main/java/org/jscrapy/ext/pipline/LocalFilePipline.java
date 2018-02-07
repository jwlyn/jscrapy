package org.jscrapy.ext.pipline;

import com.alibaba.fastjson.JSON;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.jscrapy.core.config.JscrapyConfig;
import org.jscrapy.core.config.SysDefaultConfig;
import org.jscrapy.core.data.DataItem;
import org.jscrapy.core.exception.MySpiderExceptionCode;
import org.jscrapy.core.exception.MySpiderFetalException;
import org.jscrapy.core.pipline.Pipline;
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
    private final static Logger logger = LoggerFactory.getLogger(LocalFilePipline.class);
    private String dataFilePath;//物理的数据文件位置path+file

    /**
     * @param jscrapyConfig
     * @throws IOException
     */
    public LocalFilePipline(JscrapyConfig jscrapyConfig) throws MySpiderFetalException {

        super(jscrapyConfig);
        String taskWorkDir = jscrapyConfig.getTaskWorkDir();

        this.dataFilePath = taskWorkDir + "pipline" + SysDefaultConfig.FILE_PATH_SEPERATOR + jscrapyConfig.getTaskName() + ".json";//完整的目录+文件名字。解析之后的数据保存的位置
        String baseDir = FilenameUtils.getFullPath(dataFilePath);
        try {
            FileUtils.forceMkdir(new File(baseDir));
        } catch (IOException e) {

            MySpiderFetalException exp = new MySpiderFetalException(MySpiderExceptionCode.LOCAL_PIPLINE_MK_DIR_ERROR);
            exp.setErrorMessage(e.getLocalizedMessage());
            throw exp;
        }
    }

    @Override
    public void save(List<DataItem> dataItems) throws MySpiderFetalException {
        if (dataItems != null && dataItems.size()>0) {
            for (DataItem dataItem : dataItems) {
                try {
                    File dataFile = new File(dataFilePath);
                    String data = JSON.toJSONString(dataItem.getDataItem());
                    synchronized (super.getJscrapyConfig()) {//任务级别的锁，只锁住同一个任务的多个线程
                        FileUtils.writeStringToFile(dataFile, data + "\n", StandardCharsets.UTF_8.name(), true);
                    }
                } catch (IOException e) {

                    MySpiderFetalException exp = new MySpiderFetalException(MySpiderExceptionCode.LOCAL_PIPLINE_WRITE_FILE_ERROR);
                    exp.setErrorMessage(e.getLocalizedMessage());
                    throw exp;
                }
            }
        }
    }
}
