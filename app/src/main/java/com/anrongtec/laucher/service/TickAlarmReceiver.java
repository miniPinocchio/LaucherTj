package com.anrongtec.laucher.service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.anrongtec.laucher.util.NetUtil;
import com.anrongtec.laucher.widget.CustomToast;


/**
 *
 * @author huiliu
 * @date 2017/10/9
 *
 * @email liu594545591@126.com
 * @introduce
 */
public class TickAlarmReceiver extends BroadcastReceiver {
    public TickAlarmReceiver() {

    }

    @Override
    public void onReceive(Context context, Intent intent) {
        if (!NetUtil.isNetworkAvailable(context)) {
            CustomToast.INSTANCE.showToast(context,"请检查网络");
            return;
        }
        Intent startSrv = new Intent(context, OnlineService.class);
        startSrv.putExtra("CMD", "TICK");
        context.startService(startSrv);
    }

}
