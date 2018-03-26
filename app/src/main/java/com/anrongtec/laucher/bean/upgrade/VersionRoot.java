package com.anrongtec.laucher.bean.upgrade;

/**
 * Created by wangyingying on 2017/12/13.
 */

public class VersionRoot {
    private String msg;

    private VersionData data;

    private int status;

    public void setMsg(String msg){
        this.msg = msg;
    }
    public String getMsg(){
        return this.msg;
    }
    public void setData(VersionData data){
        this.data = data;
    }
    public VersionData getData(){
        return this.data;
    }
    public void setStatus(int status){
        this.status = status;
    }
    public int getStatus(){
        return this.status;
    }
}
