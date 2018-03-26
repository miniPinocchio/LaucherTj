package com.anrongtec.laucher.bean.favorite;

/**
 *
 * @author huiliu
 * @date 2017/12/6
 *
 * @email liu594545591@126.com
 * @introduce
 */
public class FavoritePeoples {
    private int status;

    private FavoritePeople data;

    private String msg;

    public void setStatus(int status){
        this.status = status;
    }
    public int getStatus(){
        return this.status;
    }

    public FavoritePeople getData() {
        return data;
    }

    public void setData(FavoritePeople data) {
        this.data = data;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
