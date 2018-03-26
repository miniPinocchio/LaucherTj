package com.anrongtec.laucher.bean.photo;

/**
 * Created by huiliu on 2017/weather12/30.
 *
 * @email liu594545591@126.com
 * @introduce
 */
public class UploadPhotos {
    private int status;

    private UploadPhoto data;

    private String msg;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public UploadPhoto getData() {
        return data;
    }

    public void setData(UploadPhoto data) {
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
        return "UploadPhotos{" +
                "status=" + status +
                ", data=" + data +
                ", msg='" + msg + '\'' +
                '}';
    }
}
