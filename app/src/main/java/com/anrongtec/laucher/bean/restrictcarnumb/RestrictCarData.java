package com.anrongtec.laucher.bean.restrictcarnumb;

import android.os.Parcel;
import android.os.Parcelable;

/**
 *
 * @author huiliu
 * @date 2017/12/15
 *
 * @email liu594545591@126.com
 * @introduce
 */
public class RestrictCarData implements Parcelable {
    private String week_name;
    private String xh_name;

    public void setWeek_name(String week_name) {
        this.week_name = week_name;
    }

    public String getWeek_name() {
        return this.week_name;
    }

    public void setXh_name(String xh_name) {
        this.xh_name = xh_name;
    }

    public String getXh_name() {
        return this.xh_name;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.week_name);
        dest.writeString(this.xh_name);
    }

    public RestrictCarData() {
    }

    protected RestrictCarData(Parcel in) {
        this.week_name = in.readString();
        this.xh_name = in.readString();
    }

    public static final Creator<RestrictCarData> CREATOR = new Creator<RestrictCarData>() {
        @Override
        public RestrictCarData createFromParcel(Parcel source) {
            return new RestrictCarData(source);
        }

        @Override
        public RestrictCarData[] newArray(int size) {
            return new RestrictCarData[size];
        }
    };
}

