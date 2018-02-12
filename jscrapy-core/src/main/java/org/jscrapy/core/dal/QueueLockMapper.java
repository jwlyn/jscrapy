package org.jscrapy.core.dal;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * Created by cxu on 2018/2/12.
 */
@Mapper
public interface QueueLockMapper {

    QueueLockDo selectForUpdate(String taskId);

    int deleteByTaskid(String taskId);

    int deleteByPrimaryKey(Long id);

    int insert(QueueLockDo record);

    QueueLockDo selectByPrimaryKey(Long id);

    List<QueueLockDo> selectAll();

    int updateByPrimaryKey(QueueLockDo record);
}
