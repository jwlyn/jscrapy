package org.jscrapy.core.dal.cache;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by cxu on 2017/1/8.
 */
@Mapper
public interface PageCacheMapper {

    /**
     * @param tableName
     */
    public void createCacherTable(@Param("table_name") String tableName);

    /**
     * 批量缓存网页入队列
     *
     * @return
     */
    public int batchInsert(@Param("table_name") String cacherTable,
                           @Param("pages") List<PageCacheDo> pages);

    /**
     *
     * @param cacherTable
     * @param page
     * @return
     */
    public int insert(@Param("table_name") String cacherTable,
                      @Param("page") PageCacheDo page);

    /**
     * 寻找缓存表里的网页
     *
     * @param cacherTable
     * @param pageId
     */
    public PageCacheDo find(@Param("table_name") String cacherTable,
                            @Param("page_id") String pageId);

}
