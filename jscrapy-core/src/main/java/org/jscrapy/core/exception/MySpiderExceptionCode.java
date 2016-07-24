package org.jscrapy.core.exception;

/**
 * Created by cxu on 2015/6/22.
 */
public enum  MySpiderExceptionCode {
    LOCAL_QUEUE_SCHEDULE_ENQUEUE_ERROR("LocalQueueScheduler.java: Request进入队列时出错"),
    LOCAL_QUEUE_SCHEDULE_DEQUEUE_ERROR("LocalQueueScheduler.java: Request出队列时出错"),
    LOCAL_QUEUE_SCHEDULE_DEJSON_ERROR("LocalQueueScheduler.java: Request出队列后反解析json成为Request时出错"),
    LOCAL_QUEUE_SCHEDULE_INIT_ERROR("LocalQueueScheduler.java: 构造本地的bigQueue时出错"),
    CLASS_LOAD_ERROR("构造对象{}时发生错误"),
    LOCAL_PIPLINE_DEL_DIR_ERROR("删除本地目录失败"),
    LOCAL_PIPLINE_MK_DIR_ERROR("创建目录失败"),
    LOCAL_PIPLINE_WRITE_FILE_ERROR("写文件失败"),
    YAML_WRITE_FILE_ERROR("写yaml文件失败"),
    YAML_READ_FILE_ERROR("yaml读出错"),
    DISK_CACHER_DEL_DIR_ERROR("disk pagecache 删除文件失败"),
    DISK_CACHER_CACHE_FILE_ERROR("disk cacher缓存文件失败"),
    DISK_CACHER_READ_ERROR("disk pagecache 读文件错误"),
    DISK_CACHER_MK_DIR_ERROR("disk pagecache 创建目录失败"),

    GROOVY_PARSER_NOT_FOUND("没有找到groovy解析文件"),

    ;


    private String errorReason;
    private MySpiderExceptionCode(String errorReason){
        this.errorReason = errorReason;
    }

    public String getErrorReason(){
        return this.errorReason;
    }

}
