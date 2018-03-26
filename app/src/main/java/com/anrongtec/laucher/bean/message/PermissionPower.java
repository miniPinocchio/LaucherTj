package com.anrongtec.laucher.bean.message;

/**
 * Created by huiliu on 2018/3/6.
 *
 * @email liu594545591@126.com
 * @introduce
 */
public class PermissionPower {

    private String p_name;

    private int p_id;

    public void setP_name(String p_name){
        this.p_name = p_name;
    }
    public String getP_name(){
        return this.p_name;
    }
    public void setP_id(int p_id){
        this.p_id = p_id;
    }
    public int getP_id(){
        return this.p_id;
    }

    @Override
    public String toString() {
        return "PermissionPower{" +
                "p_name='" + p_name + '\'' +
                ", p_id=" + p_id +
                '}';
    }
}
