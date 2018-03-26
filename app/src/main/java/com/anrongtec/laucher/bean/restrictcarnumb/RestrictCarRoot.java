package com.anrongtec.laucher.bean.restrictcarnumb;

import java.util.List;
/**
 * Created by wangyingying on 2017/12/14.
 */

public class RestrictCarRoot {
    private String msg;

    private List<RestrictCarData> data ;

    private int status;

    public void setMsg(String msg){
        this.msg = msg;
    }
    public String getMsg(){
        return this.msg;
    }
    public void setData(List<RestrictCarData> data){
        this.data = data;
    }
    public List<RestrictCarData> getData(){
        return this.data;
    }
    public void setStatus(int status){
        this.status = status;
    }
    public int getStatus(){
        return this.status;
    }
}
