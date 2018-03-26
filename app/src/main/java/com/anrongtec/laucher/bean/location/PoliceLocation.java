package com.anrongtec.laucher.bean.location;

/**
 *
 * @author huiliu
 * @date 2017/12/7
 *
 * @email liu594545591@126.com
 * @introduce
 */
public class PoliceLocation {
    private String address;

    private String name;

    private long create_time;

    private boolean del;

    private String longitude;

    private String latitude;

    private String code;

    private String creator_code;

    private String accuracy;

    private String phone;

    private long crelocal_time;

    private String personnel_status;

    private String status;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public boolean isDel() {
        return del;
    }

    public void setDel(boolean del) {
        this.del = del;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getCreator_code() {
        return creator_code;
    }

    public void setCreator_code(String creator_code) {
        this.creator_code = creator_code;
    }

    public String getAccuracy() {
        return accuracy;
    }

    public void setAccuracy(String accuracy) {
        this.accuracy = accuracy;
    }

    public long getCreate_time() {
        return create_time;
    }

    public void setCreate_time(long create_time) {
        this.create_time = create_time;
    }

    public long getCrelocal_time() {
        return crelocal_time;
    }

    public void setCrelocal_time(long crelocal_time) {
        this.crelocal_time = crelocal_time;
    }

    public String getPersonnel_status() {
        return personnel_status;
    }

    public void setPersonnel_status(String personnel_status) {
        this.personnel_status = personnel_status;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
