package com.anrongtec.laucher.bean.favorite;

import java.sql.Timestamp;

/**
 * 收藏bean
 */
public class AdmWatch {
    /**
     * 主键
     */
    protected Long watchId;

    /**
     * 表名
     */
    protected String tableName;

    /**
     * 表id
     */
    protected String tableId;

    /**
     * 类型
     */
    protected String type;

    /**
     * 收藏人警号
     */
    protected String watchUserCode;

    /**
     * 收藏人姓名
     */
    protected String watchUserName;

    /**
     * 收藏时间
     */
    protected Timestamp watchTime;

    /**
     * adm_watch.watch_id
     */
    public Long getWatchId() {
        return watchId;
    }

    /**
     * adm_watch.watch_id
     */
    public void setWatchId(Long watchId) {
        this.watchId = watchId;
    }

    /**
     * adm_watch.table_name
     */
    public String getTableName() {
        return tableName;
    }

    /**
     * adm_watch.table_name
     */
    public void setTableName(String tableName) {
        this.tableName = tableName == null ? null : tableName.trim();
    }

    /**
     * adm_watch.table_id
     */
    public String getTableId() {
        return tableId;
    }

    /**
     * adm_watch.table_id
     */
    public void setTableId(String tableId) {
        this.tableId = tableId == null ? null : tableId.trim();
    }

    /**
     * adm_watch.type
     */
    public String getType() {
        return type;
    }

    /**
     * adm_watch.type
     */
    public void setType(String type) {
        this.type = type == null ? null : type.trim();
    }

    /**
     * adm_watch.watch_user_code
     */
    public String getWatchUserCode() {
        return watchUserCode;
    }

    /**
     * adm_watch.watch_user_code
     */
    public void setWatchUserCode(String watchUserCode) {
        this.watchUserCode = watchUserCode == null ? null : watchUserCode.trim();
    }

    /**
     * adm_watch.watch_user_name
     */
    public String getWatchUserName() {
        return watchUserName;
    }

    /**
     * adm_watch.watch_user_name
     */
    public void setWatchUserName(String watchUserName) {
        this.watchUserName = watchUserName == null ? null : watchUserName.trim();
    }

    /**
     * adm_watch.watch_time
     */
    public Timestamp getWatchTime() {
        return watchTime;
    }

    /**
     * adm_watch.watch_time
     */
    public void setWatchTime(Timestamp watchTime) {
        this.watchTime = watchTime;
    }
}