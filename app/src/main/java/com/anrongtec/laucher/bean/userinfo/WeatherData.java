package com.anrongtec.laucher.bean.userinfo;

import com.anrongtec.laucher.bean.message.PermissionPower;

import java.util.List;

/**
 * Created by huiliu on 2018/1/23.
 *
 * @email liu594545591@126.com
 * @introduce
 */
public class WeatherData {

    private String personStatus;

    private String depName;

    private String att_url;

    private boolean attentionPower;

    private Now now;

    private String limit;

    private List<PermissionPower> permissionPower;

    public void setPersonStatus(String personStatus) {
        this.personStatus = personStatus;
    }

    public String getPersonStatus() {
        return this.personStatus;
    }

    public void setDepName(String depName) {
        this.depName = depName;
    }

    public String getDepName() {
        return this.depName;
    }

    public void setAtt_url(String att_url) {
        this.att_url = att_url;
    }

    public String getAtt_url() {
        return this.att_url;
    }

    public void setAttentionPower(boolean attentionPower) {
        this.attentionPower = attentionPower;
    }

    public boolean getAttentionPower() {
        return this.attentionPower;
    }

    public void setNow(Now now) {
        this.now = now;
    }

    public Now getNow() {
        return this.now;
    }

    public void setLimit(String limit) {
        this.limit = limit;
    }

    public String getLimit() {
        return this.limit;
    }

    public boolean isAttentionPower() {
        return attentionPower;
    }

    public List<PermissionPower> getPermissionPower() {
        return permissionPower;
    }

    public void setPermissionPower(List<PermissionPower> permissionPower) {
        this.permissionPower = permissionPower;
    }
}
