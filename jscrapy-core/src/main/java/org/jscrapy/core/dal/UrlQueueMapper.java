package org.jscrapy.core.dal;

import org.apache.ibatis.annotations.Param;
import org.jscrapy.core.request.UrlStatus;

import java.util.List;

/**
 * Created by cxu on 2016/8/5.
 */
public interface UrlQueueMapper {
    /**
     * @param queueName
     */
    public int createNewQueue(@Param("queue_name") String queueName);

    /**
     * 删除队列
     *
     * @param queueName
     */
    public void dropQueue(@Param("queue_name") String queueName);

    /**
     * 批量插入队列
     *
     * @return
     */
    public int batchInsert(@Param("queue_name") String queueName,
                           @Param("urls") List<UrlQueueDo> urls);

    /**
     * @return
     */
    public List<UrlQueueDo> selectUrlByStatus(@Param("queue_name") String tableName,
                                              @Param("url_status") UrlStatus urlStatus,
                                              @Param("limit") int limit);

    /**
     * 批量更新队列
     */
    public int batchUpdate(@Param("queue_name") String queueName,
                           @Param("urls") List<UrlQueueDo> urls);

    /**
     * 批量更新队列
     */
    public int batchUpdateUrlStatus(@Param("queue_name") String queueName,
                                    @Param("url_status") UrlStatus urlStatus,
                                    @Param("urls") List<UrlQueueDo> urls);

    /**
     * 批量删除
     */
    public int batchDelete(@Param("queue_name") String queueName,
                           @Param("urls") List<UrlQueueDo> urls);

}
