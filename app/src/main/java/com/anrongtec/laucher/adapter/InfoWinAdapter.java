package com.anrongtec.laucher.adapter;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.amap.api.maps.AMap;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.Marker;
import com.anrongtec.laucher.R;
import com.anrongtec.laucher.bean.location.PoliceLocation;

/**
 *
 * @author wangyingying
 * @date 2017/12/30
 */

public class InfoWinAdapter implements AMap.InfoWindowAdapter, View.OnClickListener {
    private LatLng latLng;
    private LinearLayout call;
    private LinearLayout navigation;
    private TextView nameTV;
    private String agentName;
    private TextView addrTV;
    private String snippet;
    private TextView tv_police_number;
    private TextView tv_phone;
    private TextView tv_name;
    private TextView tv_police_status;
    private LinearLayout navigation_ll;
    private LinearLayout call_ll;

    private View view;

    private Marker marker;

    private PoliceLocation policeLocation;

    private Context mContext;

    public InfoWinAdapter(Context context) {
        mContext = context;
    }

    @Override
    public View getInfoWindow(Marker marker) {
        this.marker = marker;
        if (view == null){
            view = LayoutInflater.from(mContext).inflate(R.layout.custom_info_window, null);
        }
        policeLocation = (PoliceLocation) marker.getObject();
        render(marker);
        return view;
    }

    @Override
    public View getInfoContents(Marker marker) {
        return null;
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.call_LL:  //点击打电话
                Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:"+policeLocation.getPhone()));
                if (ActivityCompat.checkSelfPermission(mContext, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    return;
                }

                mContext.startActivity(intent);
                break;
            case R.id.navigation_LL:
                marker.hideInfoWindow();
                break;
                default:
                    break;
        }
    }

    /**
     * 自定义infowinfow窗口
     */
    public void render(Marker marker) {
        //数据
        latLng = marker.getPosition();
        snippet = marker.getSnippet();
        agentName = marker.getTitle();

        //控件
        tv_name = view.findViewById(R.id.tv_name);
        tv_phone = view.findViewById(R.id.tv_police_phone);
        tv_police_number = view.findViewById(R.id.tv_police_number);
        navigation_ll = view.findViewById(R.id.navigation_LL);
        call_ll = view.findViewById(R.id.call_LL);
        tv_police_status = view.findViewById(R.id.tv_police_status);

        //绑定
        if (policeLocation != null) {
            tv_name.setText(policeLocation.getName());
            tv_phone.setText(policeLocation.getPhone());
            tv_police_number.setText(policeLocation.getCode());
            call_ll.setOnClickListener(this);
            navigation_ll.setOnClickListener(this);
            tv_police_status.setText(policeLocation.getPersonnel_status());
            view.setTag(policeLocation.getCode());
        }

    }


}


