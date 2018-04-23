package com.anrongtec.laucher.bean.sign;

/**
 *
 * @author huiliu
 * @date 2018/4/23
 *
 * @email liu594545591@126.com
 * @introduce
 */
public class SignInfo {
    private int status;
    private String msg;
    private SignData data;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public SignData getData() {
        return data;
    }

    public void setData(SignData data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "SignInfo{" +
                "status=" + status +
                ", msg='" + msg + '\'' +
                ", data=" + data +
                '}';
    }
}
