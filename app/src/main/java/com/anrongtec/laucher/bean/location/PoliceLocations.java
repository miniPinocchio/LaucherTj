package com.anrongtec.laucher.bean.location;

import java.util.List;

/**
 *
 * @author huiliu
 * @date 2017/12/7
 *
 * @email liu594545591@126.com
 * @introduce
 */
public class PoliceLocations {

    private int status;

    private List<PoliceLocation> data ;

    private String msg;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public List<PoliceLocation> getData() {
        return data;
    }

    public void setData(List<PoliceLocation> data) {
        this.data = data;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
