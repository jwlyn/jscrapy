package org.jscrapy.core.config.modulecfg;

/**
 * 任务的主要组件配置
 * Created by cxu on 2016/7/26.
 */
public class TaskComponentConfig {
    private String spiderClass; //任务，控制流程
    private String downloaderClass;//下载
    private String processorClass;//用脚本、规则处理网页
    private String[] piplineClass;//存储
    private String urlConsumerClass;//从队列里取URL
    private String urlProducerClass;//将URL放入队列
    private String cacherClass;//缓存网页
    private String deduperClass;//URL去重

    public String getSpiderClass() {
        return spiderClass;
    }

    public void setSpiderClass(String spiderClass) {
        this.spiderClass = spiderClass;
    }

    public String getDownloaderClass() {
        return downloaderClass;
    }

    public void setDownloaderClass(String downloaderClass) {
        this.downloaderClass = downloaderClass;
    }

    public String getProcessorClass() {
        return processorClass;
    }

    public void setProcessorClass(String processorClass) {
        this.processorClass = processorClass;
    }

    public String[] getPiplineClass() {
        return piplineClass;
    }

    public void setPiplineClass(String[] piplineClass) {
        this.piplineClass = piplineClass;
    }

    public String getUrlConsumerClass() {
        return urlConsumerClass;
    }

    public void setUrlConsumerClass(String urlConsumerClass) {
        this.urlConsumerClass = urlConsumerClass;
    }

    public String getUrlProducerClass() {
        return urlProducerClass;
    }

    public void setUrlProducerClass(String urlProducerClass) {
        this.urlProducerClass = urlProducerClass;
    }

    public String getCacherClass() {
        return cacherClass;
    }

    public void setCacherClass(String cacherClass) {
        this.cacherClass = cacherClass;
    }

    public String getDeduperClass() {
        return deduperClass;
    }

    public void setDeduperClass(String deduperClass) {
        this.deduperClass = deduperClass;
    }
}
