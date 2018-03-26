package com.anrongtec.laucher.ui.activity;

import android.graphics.Point;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.Window;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.LocationSource;
import com.amap.api.maps.MapView;
import com.amap.api.maps.Projection;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MarkerOptions;
import com.anrongtec.laucher.R;
import com.anrongtec.laucher.TjApp;
import com.anrongtec.laucher.adapter.InfoWinAdapter;
import com.anrongtec.laucher.bean.location.PoliceLocation;
import com.anrongtec.laucher.bean.location.PoliceLocations;
import com.anrongtec.laucher.netconfig.UserService;
import com.anrongtec.laucher.util.GsonUtil;
import com.anrongtec.laucher.widget.CustomToast;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * @author huiliu
 */
public class MapCenterActivity extends ToolBarActivity implements LocationSource,
        AMapLocationListener, AMap.OnMapTouchListener, Callback<String>, AMap.OnMarkerClickListener {

    @BindView(R.id.map_center_view)
    MapView mMapCenterView;

    private AMap aMap;
    private OnLocationChangedListener mListener;
    private AMapLocationClient mlocationClient;
    private AMapLocationClientOption mLocationOption;

    private MarkerOptions markerOption;
    Marker marker;

    boolean useMoveToLocationWithMapMode = true;

    /**
     * 自定义定位小蓝点的Marker
     */
    Marker locationMarker;

    /**
     * 坐标和经纬度转换工具
     */
    Projection projection;
    private String phone;
    private String name;
    private String code;

    private InfoWinAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);// 不显示程序的标题栏
        setContentView(R.layout.activity_map_center);
        ButterKnife.bind(this);
        setRightImgGone(false);
        mMapCenterView.onCreate(savedInstanceState);// 此方法必须重写
        init();
    }

    /**
     * 获取其他警员信息
     */
    private void getLocationData(LatLng mLatLng) {
        String longitude = String.valueOf(mLatLng.longitude);
        String latitude = String.valueOf(mLatLng.latitude);
        String filter = "longitude=" + longitude + "&latitude=" + latitude + "&R=10";
        TjApp.getRetrofit().getLocation(filter).enqueue(this);
    }


    /**
     * 初始化
     */
    private void init() {
        if (aMap == null) {
            aMap = mMapCenterView.getMap();
            setUpMap();
        }
    }

    /**
     * 设置一些amap的属性
     */
    private void setUpMap() {
        aMap.setLocationSource(this);// 设置定位监听
        aMap.getUiSettings().setMyLocationButtonEnabled(false);// 设置默认定位按钮是否显示
        aMap.setMyLocationEnabled(true);// 设置为true表示显示定位层并可触发定位，false表示隐藏定位层并不可触发定位，默认是false
        aMap.setOnMapTouchListener(this);
    }

    /**
     * 方法必须重写
     */
    @Override
    protected void onResume() {
        super.onResume();
        mMapCenterView.onResume();

        useMoveToLocationWithMapMode = true;
    }

    /**
     * 方法必须重写
     */
    @Override
    protected void onPause() {
        super.onPause();
        mMapCenterView.onPause();
        deactivate();

        useMoveToLocationWithMapMode = false;
    }

    /**
     * 方法必须重写
     */
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mMapCenterView.onSaveInstanceState(outState);
    }

    /**
     * 方法必须重写
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        mMapCenterView.onDestroy();
        if (null != mlocationClient) {
            mlocationClient.onDestroy();
        }
    }

    /**
     * 定位成功后回调函数
     */
    @Override
    public void onLocationChanged(AMapLocation amapLocation) {
        if (mListener != null && amapLocation != null) {
            if (amapLocation != null
                    && amapLocation.getErrorCode() == 0) {
                LatLng mLatLng = new LatLng(amapLocation.getLatitude(), amapLocation.getLongitude());
                getLocationData(mLatLng);
                //展示自定义定位小蓝点
                if (locationMarker == null) {
                    //首次定位
                    locationMarker = aMap.addMarker(new MarkerOptions().position(mLatLng)
                            .icon(BitmapDescriptorFactory.fromResource(R.drawable.location_marker))
                            .anchor(0.5f, 0.5f));
                    //首次定位,选择移动到地图中心点并修改级别到15级
                    aMap.moveCamera(CameraUpdateFactory.newLatLngZoom(mLatLng, 15));
                } else {
                    if (useMoveToLocationWithMapMode) {
                        //二次以后定位，使用sdk中没有的模式，让地图和小蓝点一起移动到中心点（类似导航锁车时的效果）
                        startMoveLocationAndMap(mLatLng);
                    } else {
                        startChangeLocation(mLatLng);
                    }
                }
            } else {
                String errText = "定位失败," + amapLocation.getErrorCode() + ": " + amapLocation.getErrorInfo();
                Log.e("AmapErr", errText);
            }
        }
    }

    /**
     * 修改自定义定位小蓝点的位置
     *
     * @param latLng
     */
    private void startChangeLocation(LatLng latLng) {

        if (locationMarker != null) {
            LatLng curLatlng = locationMarker.getPosition();
            if (curLatlng == null || !curLatlng.equals(latLng)) {
                locationMarker.setPosition(latLng);
            }
        }
    }

    /**
     * 同时修改自定义定位小蓝点和地图的位置
     *
     * @param latLng
     */
    private void startMoveLocationAndMap(LatLng latLng) {

        //将小蓝点提取到屏幕上
        if (projection == null) {
            projection = aMap.getProjection();
        }
        if (locationMarker != null && projection != null) {
            LatLng markerLocation = locationMarker.getPosition();
            Point screenPosition = aMap.getProjection().toScreenLocation(markerLocation);
            locationMarker.setPositionByPixels(screenPosition.x, screenPosition.y);

        }

        //移动地图，移动结束后，将小蓝点放到放到地图上
        myCancelCallback.setTargetLatlng(latLng);
        //动画移动的时间，最好不要比定位间隔长，如果定位间隔2000ms 动画移动时间最好小于2000ms，可以使用1000ms
        //如果超过了，需要在myCancelCallback中进行处理被打断的情况
        aMap.animateCamera(CameraUpdateFactory.changeLatLng(latLng), 1000, myCancelCallback);

    }


    MyCancelCallback myCancelCallback = new MyCancelCallback();

    @Override
    public void onTouch(MotionEvent motionEvent) {
        Log.i("amap", "onTouch 关闭地图和小蓝点一起移动的模式");
        useMoveToLocationWithMapMode = false;
    }

    @Override
    public void onResponse(Call<String> call, Response<String> response) {
        String data = response.body();
        if (response.isSuccessful()) {
            resolveData(data);
        } else {
            CustomToast.INSTANCE.showToast(this, "服务器异常" + response.code());
        }
    }

    @Override
    public void onFailure(Call<String> call, Throwable t) {
        CustomToast.INSTANCE.showToast(this, "网络异常");
    }

    /**
     * 解析数据
     *
     * @param data
     */
    private void resolveData(String data) {
        if (data != null) {
            PoliceLocations policeLocations = GsonUtil.parseJsonWithGson(data, PoliceLocations.class);
            if (policeLocations != null) {
                List<PoliceLocation> policeData = policeLocations.getData();
                for (PoliceLocation police : policeData) {
                    double latitude = Double.parseDouble(police.getLatitude());
                    double longtitude = Double.parseDouble(police.getLongitude());
                    //电话
                    phone = police.getPhone();
                    //姓名
                    name = police.getName();
                    //警号
                    code = police.getCode();
                    String code = UserService.getNewUserInfo(this).getCode();
                    if (!this.code.equals(code)) {
                        LatLng latLng = new LatLng(latitude, longtitude);
                        addMarkersToMap(latLng, police);
                    }
                }
            }
        }
    }

    /**
     * 在地图上添加marker
     */
    private void addMarkersToMap(LatLng latlng, final PoliceLocation police) {
        markerOption = new MarkerOptions().icon(BitmapDescriptorFactory.fromResource(R.drawable.map_police))
                .position(latlng)
                .draggable(true);
        marker = aMap.addMarker(markerOption);
        marker.setObject(police);

        if (adapter == null) {
            adapter = new InfoWinAdapter(this);
        }
        aMap.setInfoWindowAdapter(adapter);

        aMap.setOnInfoWindowClickListener(new AMap.OnInfoWindowClickListener() {
            @Override
            public void onInfoWindowClick(Marker marker) {
            }
        });

    }

    @Override
    public boolean onMarkerClick(Marker marker) {

        return false;
    }

    /**
     * 监控地图动画移动情况，如果结束或者被打断，都需要执行响应的操作
     */
    class MyCancelCallback implements AMap.CancelableCallback {

        LatLng targetLatlng;

        public void setTargetLatlng(LatLng latlng) {
            this.targetLatlng = latlng;
        }

        @Override
        public void onFinish() {
            if (locationMarker != null && targetLatlng != null) {
                locationMarker.setPosition(targetLatlng);
            }
        }

        @Override
        public void onCancel() {
            if (locationMarker != null && targetLatlng != null) {
                locationMarker.setPosition(targetLatlng);
            }
        }
    }

    ;


    /**
     * 激活定位
     */
    @Override
    public void activate(OnLocationChangedListener listener) {
        mListener = listener;
        if (mlocationClient == null) {
            mlocationClient = new AMapLocationClient(this);
            mLocationOption = new AMapLocationClientOption();
            //设置定位监听
            mlocationClient.setLocationListener(this);
            //设置为高精度定位模式
            mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
            //是指定位间隔
            mLocationOption.setInterval(20000);
            //设置定位参数
            mlocationClient.setLocationOption(mLocationOption);
            // 此方法为每隔固定时间会发起一次定位请求，为了减少电量消耗或网络流量消耗，
            // 注意设置合适的定位时间的间隔（最小间隔支持为2000ms），并且在合适时间调用stopLocation()方法来取消定位请求
            // 在定位结束后，在合适的生命周期调用onDestroy()方法
            // 在单次定位情况下，定位无论成功与否，都无需调用stopLocation()方法移除请求，定位sdk内部会移除
            mlocationClient.startLocation();
        }
    }

    /**
     * 停止定位
     */
    @Override
    public void deactivate() {
        mListener = null;
        if (mlocationClient != null) {
            mlocationClient.stopLocation();
            mlocationClient.onDestroy();
        }
        mlocationClient = null;
    }
}
