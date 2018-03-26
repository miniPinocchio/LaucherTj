package com.anrongtec.laucher.bean.upgrade;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by wangyingying on 2017/weather12/13.
 */

public class VersionData implements Parcelable{
    private boolean update;

    private String fileUrl;

    public void setUpdate(boolean update){
        this.update = update;
    }
    public boolean getUpdate(){
        return this.update;
    }
    public void setFileUrl(String fileUrl){
        this.fileUrl = fileUrl;
    }
    public String getFileUrl(){
        return this.fileUrl;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeByte(this.update ? (byte) 1 : (byte) 0);
        dest.writeString(this.fileUrl);
    }

    public VersionData() {
    }

    protected VersionData(Parcel in) {
        this.update = in.readByte() != 0;
        this.fileUrl = in.readString();
    }

    public static final Creator<VersionData> CREATOR = new Creator<VersionData>() {
        @Override
        public VersionData createFromParcel(Parcel source) {
            return new VersionData(source);
        }

        @Override
        public VersionData[] newArray(int size) {
            return new VersionData[size];
        }
    };
}
