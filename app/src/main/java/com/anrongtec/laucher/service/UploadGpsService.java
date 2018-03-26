package com.anrongtec.laucher.service;

import android.annotation.SuppressLint;
import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.location.AMapLocationQualityReport;
import com.anrongtec.laucher.TjApp;
import com.anrongtec.laucher.bean.sos.SosNewBean;
import com.anrongtec.laucher.netconfig.UserService;
import com.anrongtec.laucher.util.LogUtil;
import com.anrongtec.laucher.util.MapUtils;

import java.util.Timer;
import java.util.TimerTask;

import retrofit2.Call;
import retrofit2.Response;

/**
 * @author huiliu
 */
public class UploadGpsService extends Service {
    private boolean isUploading = false;

    /**
     * 上传时间间隔 10秒
     */
    private static final long UPLOAD_TIME = 300 * 1000;

    private Timer serviceTimer;
    private TimerTask task;

    private AMapLocationClient locationClient = null;
    private AMapLocationClientOption locationOption = null;
    @SuppressLint("HandlerLeak")
    public Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            final AMapLocation location = (AMapLocation) msg.obj;
            String longitude = String.valueOf(location.getLongitude());
            String latitude = String.valueOf(location.getLatitude());
            String address = location.getAddress();
            String formatUTC = MapUtils.formatUTC(location.getTime(), "yyyy-MM-dd HH:mm:ss");
            String accuracy = String.valueOf(location.getAccuracy());
            SosNewBean userInfo = UserService.getNewUserInfo(getApplicationContext());
            if (userInfo != null) {
                String code = userInfo.getCode();
                String name = userInfo.getName();
                String mobile = userInfo.getMobile();
                String filters = "code=" + code + "&name=" + name + "&creLocal_time=" + formatUTC
                        + "&accuracy=" + accuracy + "&address=" + address + "&longitude=" + longitude + "&latitude=" + latitude + "&phone=" + mobile;
                TjApp.getRetrofit().uploadLocation(filters).enqueue(new retrofit2.Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {
                        if (response.isSuccessful()) {
                            String body = response.body();
                            LogUtil.d("上传位置信息成功，处理返回" + body);
                        }
                        if (null != locationClient) {
                            /**
                             * 如果AMapLocationClient是在当前Activity实例化的，
                             * 在Activity的onDestroy中一定要执行AMapLocationClient的onDestroy
                             */
                            locationClient.onDestroy();
                            locationClient = null;
                            locationOption = null;
                        }
                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable t) {
                        if (null != locationClient) {
                            /**
                             * 如果AMapLocationClient是在当前Activity实例化的，
                             * 在Activity的onDestroy中一定要执行AMapLocationClient的onDestroy
                             */
                            locationClient.onDestroy();
                            locationClient = null;
                            locationOption = null;
                        }
                    }
                });
            }
        }
    };


    public UploadGpsService() {
    }

    @Override
    public void onCreate() {
        super.onCreate();
        resetTimer();
    }

    /**
     * 重置定时任务
     */
    public void resetTimer() {
        if (task != null) {
            task.cancel();
            task = null;
        }
        if (serviceTimer != null) {
            serviceTimer.cancel();
            serviceTimer = null;
        }
        task = new TimerTask() {
            @Override
            public void run() {
                initLocation();
            }
        };
        serviceTimer = new Timer();
        serviceTimer.schedule(task, 0, UPLOAD_TIME);
    }


    @Override
    public IBinder onBind(Intent intent) {

        throw new UnsupportedOperationException("Not yet implemented");
    }

    /**
     * 初始化定位
     */
    private void initLocation() {
        //初始化client
        locationClient = new AMapLocationClient(this.getApplicationContext());
        locationOption = getDefaultOption();
        //设置定位参数
        locationClient.setLocationOption(locationOption);
        // 设置定位监听
        locationClient.setLocationListener(locationListener);
        // 设置定位参数
        locationClient.setLocationOption(locationOption);
        // 启动定位
        locationClient.startLocation();
    }

    /**
     * 默认的定位参数
     *
     * @author hongming.wang
     * @since 2.8.0
     */
    private AMapLocationClientOption getDefaultOption() {
        AMapLocationClientOption mOption = new AMapLocationClientOption();
        //可选，设置定位模式，可选的模式有高精度、仅设备、仅网络。默认为高精度模式
        mOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
        //可选，设置是否gps优先，只在高精度模式下有效。默认关闭
        mOption.setGpsFirst(false);
        //可选，设置网络请求超时时间。默认为30秒。在仅设备模式下无效
        mOption.setHttpTimeOut(30000);
        //可选，设置定位间隔。默认为2秒
        mOption.setInterval(2000);
        //可选，设置是否返回逆地理地址信息。默认是true
        mOption.setNeedAddress(true);
        //可选，设置是否单次定位。默认是false
        mOption.setOnceLocation(true);
        //可选，设置是否等待wifi刷新，默认为false.如果设置为true,会自动变为单次定位，持续定位时不要使用
        mOption.setOnceLocationLatest(false);
        //可选， 设置网络请求的协议。可选HTTP或者HTTPS。默认为HTTP
        AMapLocationClientOption.setLocationProtocol(AMapLocationClientOption.AMapLocationProtocol.HTTP);
        //可选，设置是否使用传感器。默认是false
        mOption.setSensorEnable(false);
        //可选，设置是否开启wifi扫描。默认为true，如果设置为false会同时停止主动刷新，停止以后完全依赖于系统刷新，定位位置可能存在误差
        mOption.setWifiScan(false);
        //可选，设置是否使用缓存定位，默认为true
        mOption.setLocationCacheEnable(true);
        return mOption;
    }

    /**
     * 定位监听
     */
    AMapLocationListener locationListener = new AMapLocationListener() {
        @Override
        public void onLocationChanged(AMapLocation location) {
            if (null != location) {

                StringBuffer sb = new StringBuffer();
                //errCode等于0代表定位成功，其他的为定位失败，具体的可以参照官网定位错误码说明
                if (location.getErrorCode() == 0) {
                    LogUtil.d(location.getAddress());
                    Message message = new Message();
                    message.obj = location;
                    message.what = 99;
                    mHandler.sendMessage(message);
                } else {
                    //定位失败
                    sb.append("定位失败" + "\n");
                    sb.append("错误码:" + location.getErrorCode() + "\n");
                    sb.append("错误信息:" + location.getErrorInfo() + "\n");
                    sb.append("错误描述:" + location.getLocationDetail() + "\n");
                }
                //解析定位结果，
                String result = sb.toString();
            } else {
//                mTvMainSearch.setText("定位失败，loc is null");
            }
        }
    };

    /**
     * 获取GPS状态的字符串
     *
     * @param statusCode GPS状态码
     * @return
     */
    private String getGPSStatusString(int statusCode) {
        String str = "";
        switch (statusCode) {
            case AMapLocationQualityReport.GPS_STATUS_OK:
                str = "GPS状态正常";
                break;
            case AMapLocationQualityReport.GPS_STATUS_NOGPSPROVIDER:
                str = "手机中没有GPS Provider，无法进行GPS定位";
                break;
            case AMapLocationQualityReport.GPS_STATUS_OFF:
                str = "GPS关闭，建议开启GPS，提高定位质量";
                break;
            case AMapLocationQualityReport.GPS_STATUS_MODE_SAVING:
                str = "选择的定位模式中不包含GPS定位，建议选择包含GPS定位的模式，提高定位质量";
                break;
            case AMapLocationQualityReport.GPS_STATUS_NOGPSPERMISSION:
                str = "没有GPS定位权限，建议开启gps定位权限";
                break;
            default:
                break;
        }
        return str;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        destroyLocation();
    }

    /**
     * 销毁定位
     *
     * @author hongming.wang
     * @since 2.8.0
     */
    private void destroyLocation() {
        if (null != locationClient) {
            /**
             * 如果AMapLocationClient是在当前Activity实例化的，
             * 在Activity的onDestroy中一定要执行AMapLocationClient的onDestroy
             */
            locationClient.onDestroy();
            locationClient = null;
            locationOption = null;
        }
    }
}
