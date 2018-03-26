package com.anrongtec.laucher.bean.photo;

/**
 * Created by huiliu on 2017/weather12/30.
 *
 * @email liu594545591@126.com
 * @introduce
 */
public class UploadPhoto {
    private String attUrl;

    private long uploadTime;

    private String attFilename;

    public void setAttUrl(String attUrl){
        this.attUrl = attUrl;
    }
    public String getAttUrl(){
        return this.attUrl;
    }

    public long getUploadTime() {
        return uploadTime;
    }

    public void setUploadTime(long uploadTime) {
        this.uploadTime = uploadTime;
    }

    public String getAttFilename() {
        return attFilename;
    }

    public void setAttFilename(String attFilename) {
        this.attFilename = attFilename;
    }

    @Override
    public String toString() {
        return "UploadPhoto{" +
                "attUrl='" + attUrl + '\'' +
                ", uploadTime=" + uploadTime +
                ", attFilename='" + attFilename + '\'' +
                '}';
    }
}
