package com.anrongtec.laucher.bean.userinfo;

/**
 * Created by huiliu on 2018/1/23.
 *
 * @email liu594545591@126.com
 * @introduce
 */
public class LoginOtherData {
    private String completeTask;

    private String onlineTime;

    public void setCompleteTask(String completeTask){
        this.completeTask = completeTask;
    }
    public String getCompleteTask(){
        return this.completeTask;
    }
    public void setOnlineTime(String onlineTime){
        this.onlineTime = onlineTime;
    }
    public String getOnlineTime(){
        return this.onlineTime;
    }
}
