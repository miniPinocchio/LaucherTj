package com.anrongtec.laucher.bean.favorite;

/**
 * Created by huiliu on 2017/12/6.
 *
 * @email liu594545591@126.com
 * @introduce
 */
public class Favorites {
    private int status;

    private Favorite data;

    private String msg;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Favorite getData() {
        return data;
    }

    public void setData(Favorite data) {
        this.data = data;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
