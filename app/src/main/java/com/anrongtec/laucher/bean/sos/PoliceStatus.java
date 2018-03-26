package com.anrongtec.laucher.bean.sos;

/**
 * @author huiliu
 * @date 2018/1/11
 * @email liu594545591@126.com
 * @introduce  人员状态
 */
public class PoliceStatus {
    private int status;
    private String data;
    private String msg;

    public void setStatus(int status) {
        this.status = status;
    }

    public int getStatus() {
        return status;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getData() {
        return data;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getMsg() {
        return msg;
    }

}
