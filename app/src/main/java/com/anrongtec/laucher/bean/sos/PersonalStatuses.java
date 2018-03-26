package com.anrongtec.laucher.bean.sos;

/**
 *
 * @author huiliu
 * @date 2018/1/14
 *
 * @email liu594545591@126.com
 * @introduce
 */
public class PersonalStatuses {
    private String msg;

    private PersonalStatus data;

    private int status;

    public void setMsg(String msg){
        this.msg = msg;
    }
    public String getMsg(){
        return this.msg;
    }
    public void setData(PersonalStatus data){
        this.data = data;
    }
    public PersonalStatus getData(){
        return this.data;
    }
    public void setStatus(int status){
        this.status = status;
    }
    public int getStatus(){
        return this.status;
    }
}
