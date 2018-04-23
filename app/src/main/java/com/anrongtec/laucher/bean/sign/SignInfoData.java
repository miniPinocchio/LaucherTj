package com.anrongtec.laucher.bean.sign;

/**
 * @author huiliu
 * @date 2018/4/23
 * @email liu594545591@126.com
 * @introduce
 */
public class SignInfoData {
    /**
     * {"id":1,"latitudinal":"36.910747","address":"山东省德州市禹城市人民路389号靠近济南铁路局换轨大修基地",
     * "status":"1","org_code":"120000991000","del":false,"longitude":"116.639451",
     * "code":"810000","type":1,"accuracy":"550","note":"","clock_in_time":1524414591000}
     */
    private int id;
    private double latitudinal;
    private double longitude;
    private String address;
    private String status;
    private String org_code;
    private boolean del;
    private String code;
    private int type;
    private String accuracy;
    private String note;
    private String clock_in_time;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getLatitudinal() {
        return latitudinal;
    }

    public void setLatitudinal(double latitudinal) {
        this.latitudinal = latitudinal;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getOrg_code() {
        return org_code;
    }

    public void setOrg_code(String org_code) {
        this.org_code = org_code;
    }

    public boolean isDel() {
        return del;
    }

    public void setDel(boolean del) {
        this.del = del;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getAccuracy() {
        return accuracy;
    }

    public void setAccuracy(String accuracy) {
        this.accuracy = accuracy;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getClock_in_time() {
        return clock_in_time;
    }

    public void setClock_in_time(String clock_in_time) {
        this.clock_in_time = clock_in_time;
    }

    @Override
    public String toString() {
        return "SignInfoData{" +
                "id=" + id +
                ", latitudinal=" + latitudinal +
                ", longitude=" + longitude +
                ", address='" + address + '\'' +
                ", status='" + status + '\'' +
                ", org_code='" + org_code + '\'' +
                ", del=" + del +
                ", code='" + code + '\'' +
                ", type=" + type +
                ", accuracy='" + accuracy + '\'' +
                ", note='" + note + '\'' +
                ", clock_in_time='" + clock_in_time + '\'' +
                '}';
    }
}
