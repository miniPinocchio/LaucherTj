package com.anrongtec.laucher.bean.sign;

/**
 * Created by huiliu on 2018/4/23.
 *
 * @email liu594545591@126.com
 * @introduce
 */
public class SignResult {
    private int status;
    private String data;
    private String msg;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    @Override
    public String toString() {
        return "SignResult{" +
                "status=" + status +
                ", data='" + data + '\'' +
                ", msg='" + msg + '\'' +
                '}';
    }
}
