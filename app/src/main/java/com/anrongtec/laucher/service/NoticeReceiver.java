package com.anrongtec.laucher.service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.anrongtec.laucher.netconfig.Constant;

import java.util.Objects;

/**
 * @author huiliu
 */
public class NoticeReceiver extends BroadcastReceiver {
    private Context mContext;


    @Override
    public void onReceive(Context context, Intent intent) {
        if (Objects.equals(intent.getAction(), Constant.NOTICE_ACTION)){

        }

    }
}
