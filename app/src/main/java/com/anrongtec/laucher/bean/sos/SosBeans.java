package com.anrongtec.laucher.bean.sos;

/**
 * @author huiliu
 * @date 2017/10/10
 * @email liu594545591@126.com
 * @introduce 信大统一认证返回信息
 */
public class SosBeans {
    private String status;

    private String message;

    private String id;

    private SosBean data;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public SosBean getData() {
        return data;
    }

    public void setData(SosBean data) {
        this.data = data;
    }
}
