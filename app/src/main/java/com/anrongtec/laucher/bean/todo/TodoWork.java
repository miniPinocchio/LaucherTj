package com.anrongtec.laucher.bean.todo;

/**
 *
 * @author huiliu
 * @date 2017/11/22
 *
 * @email liu594545591@126.com
 * @introduce
 */
public class TodoWork {
    private String code;

    private TodoWorks data;

    private String msg;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public TodoWorks getData() {
        return data;
    }

    public void setData(TodoWorks data) {
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
        return "TodoWork{" +
                "code='" + code + '\'' +
                ", data=" + data +
                ", msg='" + msg + '\'' +
                '}';
    }
}
