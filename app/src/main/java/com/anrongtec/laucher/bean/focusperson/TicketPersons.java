package com.anrongtec.laucher.bean.focusperson;

/**
 * Created by huiliu on 2018/2/2.
 *
 * @email liu594545591@126.com
 * @introduce
 */
public class TicketPersons {
    private int status;

    private TicketPerson data;

    private String msg;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public TicketPerson getData() {
        return data;
    }

    public void setData(TicketPerson data) {
        this.data = data;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
