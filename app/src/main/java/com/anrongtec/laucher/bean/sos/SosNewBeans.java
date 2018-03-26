package com.anrongtec.laucher.bean.sos;

/**
 * Created by huiliu on 2018/1/23.
 *
 * @email liu594545591@126.com
 * @introduce
 */
public class SosNewBeans {
    private int status;

    private SosNewBean data;

    private String msg;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public SosNewBean getData() {
        return data;
    }

    public void setData(SosNewBean data) {
        this.data = data;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
