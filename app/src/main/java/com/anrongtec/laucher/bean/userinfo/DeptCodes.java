package com.anrongtec.laucher.bean.userinfo;

/**
 * Created by huiliu on 2017/12/13.
 *
 * @email liu594545591@126.com
 * @introduce
 */
public class DeptCodes {
    private int status;

    private DeptCode data;

    private String msg;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public DeptCode getData() {
        return data;
    }

    public void setData(DeptCode data) {
        this.data = data;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
