package com.anrongtec.laucher.bean;

import android.graphics.drawable.Drawable;

import com.google.gson.annotations.Expose;
import com.litesuits.orm.db.annotation.PrimaryKey;
import com.litesuits.orm.db.enums.AssignType;

import java.util.Arrays;

/**
 *
 * @author huiliu
 * @date 2017/9/30
 *
 * @email liu594545591@126.com
 * @introduce
 */
public class AppModel {
    @Expose
    private String appName;// 应用名称
    @PrimaryKey(AssignType.BY_MYSELF)
    private String appPackName;// 包名
    /** 本地版本号 */
    @Expose
    private int localVersionCode;
    /** 本地版本名称 */
    @Expose
    private String localVersionName;
    @Expose
    private String versionDesc;//版本描述
    @Expose
    private long size;// 大小
    @Expose
    private String iconUrl;// 图标路径
    /**
     * 是否是系统应用  true or false
     */
    @Expose
    private boolean isSystem;

    @Expose
    private boolean isCheck = false;
    /**
     * 以下属性不做存储
     */
    @Expose
    private Drawable localIcon;// 图标
    private long firstInstallTime;// 安装时间
    private long lastUpdateTime;//上次更新时间
    public String[] requestedPermissions;// 应用权限
    private String installPath;// 安装路径

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public String getAppPackName() {
        return appPackName;
    }

    public void setAppPackName(String appPackName) {
        this.appPackName = appPackName;
    }

    public int getLocalVersionCode() {
        return localVersionCode;
    }

    public void setLocalVersionCode(int localVersionCode) {
        this.localVersionCode = localVersionCode;
    }

    public String getLocalVersionName() {
        return localVersionName;
    }

    public void setLocalVersionName(String localVersionName) {
        this.localVersionName = localVersionName;
    }

    public String getVersionDesc() {
        return versionDesc;
    }

    public void setVersionDesc(String versionDesc) {
        this.versionDesc = versionDesc;
    }

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }

    public String getIconUrl() {
        return iconUrl;
    }

    public void setIconUrl(String iconUrl) {
        this.iconUrl = iconUrl;
    }

    public Drawable getLocalIcon() {
        return localIcon;
    }

    public void setLocalIcon(Drawable localIcon) {
        this.localIcon = localIcon;
    }

    public long getFirstInstallTime() {
        return firstInstallTime;
    }

    public void setFirstInstallTime(long firstInstallTime) {
        this.firstInstallTime = firstInstallTime;
    }

    public long getLastUpdateTime() {
        return lastUpdateTime;
    }

    public void setLastUpdateTime(long lastUpdateTime) {
        this.lastUpdateTime = lastUpdateTime;
    }

    public String[] getRequestedPermissions() {
        return requestedPermissions;
    }

    public void setRequestedPermissions(String[] requestedPermissions) {
        this.requestedPermissions = requestedPermissions;
    }

    public String getInstallPath() {
        return installPath;
    }

    public void setInstallPath(String installPath) {
        this.installPath = installPath;
    }

    public boolean isSystem() {
        return isSystem;
    }

    public void setSystem(boolean system) {
        isSystem = system;
    }

    public boolean isCheck() {
        return isCheck;
    }

    public void setCheck(boolean check) {
        isCheck = check;
    }

    @Override
    public String toString() {
        return "AppModel{" +
                "appName='" + appName + '\'' +
                ", appPackName='" + appPackName + '\'' +
                ", localVersionCode=" + localVersionCode +
                ", localVersionName='" + localVersionName + '\'' +
                ", versionDesc='" + versionDesc + '\'' +
                ", size=" + size +
                ", iconUrl='" + iconUrl + '\'' +
                ", isSystem=" + isSystem +
                ", isCheck=" + isCheck +
                ", localIcon=" + localIcon +
                ", firstInstallTime=" + firstInstallTime +
                ", lastUpdateTime=" + lastUpdateTime +
                ", requestedPermissions=" + Arrays.toString(requestedPermissions) +
                ", installPath='" + installPath + '\'' +
                '}';
    }
}
