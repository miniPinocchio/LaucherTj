package com.anrongtec.laucher.ui.fragment;


import android.annotation.SuppressLint;
import android.app.Dialog;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.anrongtec.laucher.R;
import com.anrongtec.laucher.TjApp;
import com.anrongtec.laucher.bean.sign.SignData;
import com.anrongtec.laucher.bean.sign.SignInfo;
import com.anrongtec.laucher.bean.sign.SignInfoData;
import com.anrongtec.laucher.bean.sign.SignResult;
import com.anrongtec.laucher.bean.sos.SosNewBean;
import com.anrongtec.laucher.bean.userinfo.WeatherData;
import com.anrongtec.laucher.bean.userinfo.WeatherDatas;
import com.anrongtec.laucher.netconfig.UserService;
import com.anrongtec.laucher.util.DialogUtil;
import com.anrongtec.laucher.util.GsonUtil;
import com.anrongtec.laucher.util.LogUtil;
import com.anrongtec.laucher.util.StringUtil;
import com.anrongtec.laucher.widget.CustomToast;
import com.bumptech.glide.Glide;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 *
 * @author huiliu
 */
public class SignFragment extends BaseFragment implements Callback<String> {


    @BindView(R.id.iv_sign_user_photo)
    CircleImageView mIvSignUserPhoto;
    @BindView(R.id.tv_sign_name)
    TextView mTvSignName;
    @BindView(R.id.tv_sign_organ)
    TextView mTvSignOrgan;
    @BindView(R.id.tv_sign_date)
    TextView mTvSignDate;
    Unbinder unbinder;
    @BindView(R.id.tv_sign_location)
    TextView mTvSignLocation;
    @BindView(R.id.tv_sign_time)
    TextView mTvSignTime;
    @BindView(R.id.tv_user_sign_record)
    TextView mTvUserSignRecord;
    private SosNewBean mUserInfo;

    private WeatherData mDatasData;
    private String mAtt_url;
    private String BASE_URL = "http://20.3.2.47:90";
    private String mDepName;

    private AMapLocationClient locationClient = null;
    private AMapLocationClientOption locationOption = null;

    @SuppressLint("HandlerLeak")
    public Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 99:
                    mLocation = (AMapLocation) msg.obj;
                    mAddress = mLocation.getAddress();
                    mTvSignLocation.setText(mAddress == null ? mResult : mAddress);
                    break;
                case 98:
                    if (mDialog != null && mDialog.isShowing()) {
                        mDialog.dismiss();
                    }
                    mTvSignTime.setText(StringUtil.stampToDateLetter(String.valueOf(System.currentTimeMillis())));
                    break;
                default:
                    break;
            }

        }
    };
    private Dialog mDialog;
    private int mType;
    private String mResult;
    private String mAddress;
    private AMapLocation mLocation;


    public SignFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sign, container, false);

        unbinder = ButterKnife.bind(this, view);
        initData();
        initView();
        initLocation();
        return view;
    }

    private void initData() {
        mType = 1;
        mUserInfo = UserService.getNewUserInfo(getActivity());
        TjApp.getRetrofit().getServerLocalData("code=" + mUserInfo.getCode() + "&id=" + mUserInfo.getId() + "&depId="
                + mUserInfo.getDepid() + "&depCode=" + mUserInfo.getDepcode() + "&today=" + "2018-01-24"
        ).enqueue(SignFragment.this);
    }

    private void initSign() {
        mType = 2;
        String dateSign = StringUtil.stampToDate(System.currentTimeMillis());
        TjApp.getRetrofit().getSign("code=" + mUserInfo.getCode() + "&timeNyr=" +
                dateSign, "1", "10").enqueue(SignFragment.this);
    }

    private void initView() {
        mTvSignName.setText(mUserInfo.getName());
        mTvSignDate.setText(StringUtil.stampToDate(String.valueOf(System.currentTimeMillis())));
    }


    @OnClick(R.id.tv_user_sign_record)
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_user_sign_record:
                mDialog = DialogUtil.loadingDialog(getActivity(), "正在签到...");
                toSign();
                break;
            default:
                break;
        }
    }

    /**
     * 打卡签到
     */
    private void toSign() {
        mType = 3;
        String time = StringUtil.stampToTime(System.currentTimeMillis());
        TjApp.getRetrofit().addUserSign(mUserInfo.getCode(), mUserInfo.getDepcode(),
                time, "", 1, 1, mAddress,
                String.valueOf(mLocation.getLongitude()), String.valueOf(mLocation.getLatitude()),
                (int) mLocation.getAccuracy(), "").enqueue(this);
//        if (mResult != null) {
//            CustomToast.INSTANCE.showToast(getActivity(),"定位失败，签到");
//        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onResponse(Call<String> call, Response<String> response) {
        String data = response.body();
        if (response.isSuccessful()) {
            resolveData(data);
        } else {
            CustomToast.INSTANCE.showToast(getActivity(), "服务器异常" + response.code());
        }
    }

    @Override
    public void onFailure(Call<String> call, Throwable t) {
        CustomToast.INSTANCE.showToast(getActivity(), "网络异常");
    }

    /**
     * 解析数据
     *
     * @param data
     */
    private void resolveData(String data) {
        if (data != null) {
            if (mType == 1) {
                WeatherDatas datas = GsonUtil.parseJsonWithGson(data, WeatherDatas.class);
                if (datas != null) {
                    mDatasData = datas.getData();
                    mDepName = mDatasData.getDepName();
                    mTvSignOrgan.setText(mDepName == null ? "" : mDepName);

                    mAtt_url = mDatasData.getAtt_url();
                    if (mAtt_url != null) {
                        Glide.with(getActivity())
                                .load(BASE_URL + mAtt_url)
                                .crossFade()
                                .into(mIvSignUserPhoto);
                    }
                }
                initSign();
            } else if (mType == 2) {
                SignInfo signInfo = GsonUtil.parseJsonWithGson(data, SignInfo.class);
                SignData signInfoData = signInfo.getData();
                List<SignInfoData> rows = signInfoData.getRows();
                if (rows != null && rows.size() > 0) {
                    SignInfoData infoData = rows.get(rows.size() - 1);
                    mTvSignTime.setText(StringUtil.stampToDateLetter(infoData.getClock_in_time()));
                }
            } else if (mType == 3) {
                SignResult signResult = GsonUtil.parseJsonWithGson(data, SignResult.class);
                CustomToast.INSTANCE.showToast(getActivity(), signResult.getMsg());
                mTvSignTime.setText(StringUtil.stampToDateLetter(String.valueOf(System.currentTimeMillis())));
                if (mDialog != null && mDialog.isShowing()) {
                    mDialog.dismiss();
                }
            }
        }
    }

    /**
     * 初始化定位
     */

    private void initLocation() {
        mTvSignLocation.setText("正在定位。。。");

        //初始化client
        locationClient = new AMapLocationClient(getActivity());
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
                    //解析定位结果，
                    mResult = sb.toString();
                }
            } else {
//                mTvMainSearch.setText("定位失败，loc is null");
            }
        }
    };

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
