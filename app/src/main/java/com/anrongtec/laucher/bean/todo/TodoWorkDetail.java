package com.anrongtec.laucher.bean.todo;

import android.os.Parcel;
import android.os.Parcelable;

/**
 *
 * @author huiliu
 * @date 2017/11/22
 *
 * @email liu594545591@126.com
 * @introduce
 */
public class TodoWorkDetail implements Parcelable{
    private int type;//类型

    private String id;//主键id

    private String title;//标题

    private String name;  //名字

    private String showTime;

    private String remark;//内容描述


    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getShowTime() {
        return showTime;
    }

    public void setShowTime(String showTime) {
        this.showTime = showTime;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @Override
    public String toString() {
        return "TodoWorkDetail{" +
                "type=" + type +
                ", id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", name='" + name + '\'' +
                ", showTime='" + showTime + '\'' +
                ", remark='" + remark + '\'' +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.type);
        dest.writeString(this.id);
        dest.writeString(this.title);
        dest.writeString(this.name);
        dest.writeString(this.showTime);
        dest.writeString(this.remark);
    }

    public TodoWorkDetail() {
    }

    protected TodoWorkDetail(Parcel in) {
        this.type = in.readInt();
        this.id = in.readString();
        this.title = in.readString();
        this.name = in.readString();
        this.showTime = in.readString();
        this.remark = in.readString();
    }

    public static final Creator<TodoWorkDetail> CREATOR = new Creator<TodoWorkDetail>() {
        @Override
        public TodoWorkDetail createFromParcel(Parcel source) {
            return new TodoWorkDetail(source);
        }

        @Override
        public TodoWorkDetail[] newArray(int size) {
            return new TodoWorkDetail[size];
        }
    };
}
