package com.oxf1.spider;

import com.alibaba.fastjson.JSON;
import com.oxf1.myspider.common.http.FetchResponse;
import com.oxf1.myspider.common.http.HttpDownloader;
import com.oxf1.myspider.common.http.HttpMethod;
import com.oxf1.myspider.common.http.HttpRequest;
import com.oxf1.spider.controller.response.ResponseBase;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.concurrent.TimeUnit;

/**
 * Created by cxu on 2015/10/2.
 */

@SpringBootApplication
public class Application {
    final static Logger logger = LoggerFactory.getLogger(Application.class);

    public static void main(String[]args) throws IOException, InterruptedException {

        SpringApplication.run(Application.class, args);//启动

        /**
         * 如果有同时指明了任务就提交任务
         */
        String taskConfigFile = parseTaskConfigFile();
        if (StringUtils.isNotBlank(taskConfigFile)) {
            File f = new File(taskConfigFile);
            if(!f.exists()) {
                logger.error("文件 {} 不存在！", taskConfigFile);
            }
            else{//提交任务到本地
                String yamlFileContent = FileUtils.readFileToString(f);
                String url = "http://localhost:8080/task/start";
                HttpRequest req = new HttpRequest();
                req.setUrl(url);
                req.setHttpMethod(HttpMethod.POST);
                req.addRequestParameters("taskConfig", yamlFileContent);
                TimeUnit.MILLISECONDS.sleep(4000);//等待启动完成
                HttpDownloader dl = new HttpDownloader();
                try {
                    FetchResponse resp = dl.download(req);
                    String json = new String(resp.getContent(), resp.getCharset());
                    ResponseBase result = JSON.parseObject(json, ResponseBase.class);
                    if (result.isSuccess()) {
                        logger.info("任务启动成功！");
                    }else{
                        logger.error(result.getErrorReason());
                        logger.error("任务启动失败！");
                    }
                } catch (URISyntaxException e) {
                    logger.error("URISyntaxException {}", url);
                }
            }
        }
    }

    /**
     *
     * @return
     */
    public static String parseTaskConfigFile() {
        String taskConfigFile = System.getProperty("task.config");
        return taskConfigFile;
    }
}
