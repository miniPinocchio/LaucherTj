package com.anrongtec.laucher;

import android.app.Application;
import android.content.Context;

import com.anrongtec.laucher.db.LiteOrmDBUtil;
import com.anrongtec.laucher.netconfig.ApiServiceFactory;
import com.anrongtec.laucher.netconfig.DataLayer;
import com.anrongtec.laucher.netconfig.NetInterface;
import com.anrongtec.laucher.util.CrashHandler;
import com.anrongtec.laucher.util.LogUtil;
import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.Logger;

/**
 *
 * @author huiliu
 * @date 2017/11/15
 *
 * @email liu594545591@126.com
 * @introduce
 */
public class TjApp extends Application {
    private static Context context;

    private static NetInterface mInterface;

    //public static IWXAPI sIWXAPIPay;

    //public static LocationClient mLocationClient;


    @Override
    public void onCreate() {
        super.onCreate();

        context = getApplicationContext();
//        initNet();
        //初始化网络
        DataLayer.init(this);
        Logger.addLogAdapter(new AndroidLogAdapter() {
            @Override
            public boolean isLoggable(int priority, String tag) {
                return BuildConfig.DEBUG;
            }
        });
        initOrmLite();

        CrashHandler crashHandler = CrashHandler.getInstance();
        crashHandler.init(this);

//        initFB();
//        ImagePipelineConfig frescoConfig = ImagePipelineConfig.newBuilder(this).setDownsampleEnabled(true).build();
//        Fresco.initialize(this, frescoConfig);
    }


    /**
     * 初始化数据库
     */
    private void initOrmLite() {
        boolean cascadeDB = LiteOrmDBUtil.createCascadeDB(this);
        if (cascadeDB) {
            LogUtil.d("创建数据库成功");
        }
    }

    public static NetInterface getRetrofit() {
        return ApiServiceFactory.getApi();
    }

    public static Context getContext() {
        return context;
    }

    /**
     * 程序退出的时候 调用这个方法
     */
    @Override
    public void onTerminate() {
        super.onTerminate();

    }

    /**
     * 当程序运行内存不足的时候就会回调这个方法
     */
    @Override
    public void onLowMemory() {
        super.onLowMemory();
    }
}

