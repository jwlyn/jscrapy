package org.jscrapy.core.dal;

import java.util.Date;

/**
 * Created by cxu on 2018/2/12.
 */
public class QueueLockDo {
    private Long id;
    private Date gmtCreate;
    private Date gmtModified;
    private String taskId;
    private String description;

    /**
     *
     * @param id
     * @param gmtCreate
     * @param gmtModified
     * @param taskId
     * @param description
     */
    public QueueLockDo(Long id, Date gmtCreate, Date gmtModified, String taskId, String description) {
        this.id = id;
        this.gmtCreate = gmtCreate;
        this.gmtModified = gmtModified;
        this.taskId = taskId;
        this.description = description;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getGmtCreate() {
        return gmtCreate;
    }

    public void setGmtCreate(Date gmtCreate) {
        this.gmtCreate = gmtCreate;
    }

    public Date getGmtModified() {
        return gmtModified;
    }

    public void setGmtModified(Date gmtModified) {
        this.gmtModified = gmtModified;
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
