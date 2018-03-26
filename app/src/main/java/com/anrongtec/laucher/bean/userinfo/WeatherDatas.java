package com.anrongtec.laucher.bean.userinfo;

/**
 * Created by huiliu on 2018/1/23.
 *
 * @email liu594545591@126.com
 * @introduce
 */
public class WeatherDatas {
    private String msg;

    private WeatherData data;

    private int status;

    public void setMsg(String msg){
        this.msg = msg;
    }
    public String getMsg(){
        return this.msg;
    }
    public void setStatus(int status){
        this.status = status;
    }
    public int getStatus(){
        return this.status;
    }

    public WeatherData getData() {
        return data;
    }

    public void setData(WeatherData data) {
        this.data = data;
    }
}
