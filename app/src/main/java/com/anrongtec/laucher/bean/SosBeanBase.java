package com.anrongtec.laucher.bean;

import com.anrongtec.laucher.bean.sos.SosBeans;

/**
 * Created by huiliu on 2017/11/30.
 *
 * @email liu594545591@126.com
 * @introduce
 */
public class SosBeanBase {
    private int status;

    private SosBeans data;

    private String msg;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public SosBeans getData() {
        return data;
    }

    public void setData(SosBeans data) {
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
        return "SosBeanBase{" +
                "status=" + status +
                ", data=" + data +
                ", msg='" + msg + '\'' +
                '}';
    }
}
