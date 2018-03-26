package com.anrongtec.laucher.bean.favorite;

import android.os.Parcel;
import android.os.Parcelable;

/**
 *
 * @author huiliu
 * @date 2017/12/6
 *
 * @email liu594545591@126.com
 * @introduce
 */
public class Rows implements Parcelable{

    private String watch_user_code;

    private String type;

    private long watch_time;

    private String content;

    private String title;

    public String getWatch_user_code() {
        return watch_user_code;
    }

    public void setWatch_user_code(String watch_user_code) {
        this.watch_user_code = watch_user_code;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public long getWatch_time() {
        return watch_time;
    }

    public void setWatch_time(long watch_time) {
        this.watch_time = watch_time;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.watch_user_code);
        dest.writeString(this.type);
        dest.writeLong(this.watch_time);
        dest.writeString(this.content);
        dest.writeString(this.title);
    }

    public Rows() {
    }

    protected Rows(Parcel in) {
        this.watch_user_code = in.readString();
        this.type = in.readString();
        this.watch_time = in.readLong();
        this.content = in.readString();
        this.title = in.readString();
    }

    public static final Creator<Rows> CREATOR = new Creator<Rows>() {
        @Override
        public Rows createFromParcel(Parcel source) {
            return new Rows(source);
        }

        @Override
        public Rows[] newArray(int size) {
            return new Rows[size];
        }
    };
}
