package com.anrongtec.laucher.bean.userinfo;

/**
 * Created by huiliu on 2018/1/23.
 *
 * @email liu594545591@126.com
 * @introduce
 */
public class LoginOtherDatas {
    private String msg;

    private LoginOtherData data;

    private int status;

    public void setMsg(String msg){
        this.msg = msg;
    }
    public String getMsg(){
        return this.msg;
    }
    public void setData(LoginOtherData data){
        this.data = data;
    }
    public LoginOtherData getData(){
        return this.data;
    }
    public void setStatus(int status){
        this.status = status;
    }
    public int getStatus(){
        return this.status;
    }
}
