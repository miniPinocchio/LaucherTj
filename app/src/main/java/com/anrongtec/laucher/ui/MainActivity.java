package com.anrongtec.laucher.ui;

import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.util.SparseArray;
import android.view.KeyEvent;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.anrongtec.laucher.R;
import com.anrongtec.laucher.TjApp;
import com.anrongtec.laucher.adapter.MyFragmentPagerAdapter;
import com.anrongtec.laucher.bean.upgrade.VersionRoot;
import com.anrongtec.laucher.manager.DownLoadManager;
import com.anrongtec.laucher.netconfig.Constant;
import com.anrongtec.laucher.service.OnlineService;
import com.anrongtec.laucher.service.UploadGpsService;
import com.anrongtec.laucher.ui.activity.ToolBarActivity;
import com.anrongtec.laucher.util.ApkUtil;
import com.anrongtec.laucher.util.FileProvider7;
import com.anrongtec.laucher.util.GsonUtil;
import com.anrongtec.laucher.util.LogUtil;
import com.anrongtec.laucher.widget.CustomToast;
import com.tdtech.devicemanager.ContainerPolicy;
import com.tdtech.devicemanager.DevicePolicyManager;
import com.xdja.reckon.ReckonAgent;
import com.zte.zsdk.DeviceManager;

import java.io.File;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * @author huiliu
 */
public class MainActivity extends ToolBarActivity implements ViewPager.OnPageChangeListener, Callback<String> {
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.toolbar_title)
    TextView mToolbarTitle;
    private SparseArray<Fragment> mFragments = new SparseArray<>(5);
    private int localVersion;
    private String fileUrl;
    private final int DOWN_ERROR = 4;
    public static final String FILE_PROVIDER = "com.anrongtec.laucher.fileprovider";
    /**
     * 接收下载完的广播
     **/
    DownloadCompleteReceiver receiver;
    private File file;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        toolbar.setVisibility(View.GONE);
        startService(new Intent(this, OnlineService.class));
        startService(new Intent(this, UploadGpsService.class));
        ViewPager pager = (ViewPager) findViewById(R.id.viewpager);

        MyFragmentPagerAdapter adapter = new MyFragmentPagerAdapter(getSupportFragmentManager(),
                this);
        pager.setAdapter(adapter);
        pager.setCurrentItem(0);
        pager.addOnPageChangeListener(this);

        final TextView sms = (TextView) findViewById(R.id.tv_sms);
        TextView phone = (TextView) findViewById(R.id.tv_phone);
        TextView jx_client = (TextView) findViewById(R.id.tv_jx_client);
        TextView transform = (TextView) findViewById(R.id.tv_transform);

        transform.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                transform();
            }
        });

        phone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                call();
            }
        });

        sms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sms();
            }
        });

        jx_client.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openJX();
            }
        });
        //注册下载广播
        receiver = new DownloadCompleteReceiver();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(Constant.UPDATE_APPS);
        registerReceiver(receiver, intentFilter);
    }


    @Override
    protected void onResume() {
        super.onResume();
        getVersionNum();
    }

    /**
     * 获取版本号进行对比更行
     */
    private void getVersionNum() {
        try {
            localVersion = getVersionName();
            TjApp.getRetrofit().getVersionUpdate("number=" + localVersion).enqueue(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private int getVersionName() throws Exception {
        //getPackageName()是你当前类的包名，0代表是获取版本信息
        PackageManager packageManager = getPackageManager();
        PackageInfo packInfo = packageManager.getPackageInfo(getPackageName(), 0);
        return packInfo.versionCode;
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        //不执行父类点击事件
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            return true;
        }
        //继续执行父类其他点击事件
        return super.onKeyDown(keyCode, event);
    }

    private void transform() {
        String model = Build.MODEL;
        if ("MHA-AL00".equals(model)) {
            DevicePolicyManager mDevicePolicyManager = DevicePolicyManager.getInstance(getApplicationContext());
            if (mDevicePolicyManager != null) {
                ContainerPolicy containerPolicy = mDevicePolicyManager.getContainerPolicy();
                containerPolicy.switchContainer() ;
            } else {
                CustomToast.INSTANCE.showToast(this, "当前为单系统手机");
            }
        } else if ("ZTE A2018".equals(model)) {
            com.zte.zsdk.DeviceManager.zsdkGetInstance(getApplicationContext()).zsdkSwitchZone();
        } else if ("ZTE C2017".equals(model)) {
//            ApkUtil.openPackage(MainActivity.this, "com.zte.tdomain");
            DeviceManager.zsdkGetInstance(getApplicationContext()).zsdkSwitchZone();
        } else {
            CustomToast.INSTANCE.showToast(this, "当前手机不可切换系统");
        }
    }

    /**
     * 调用拨号界面
     */
    private void call() {
        Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:"));
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    private void sms() {
        ApkUtil.openAPP(getApplicationContext(), "com.android.mms");
    }

    private void openJX() {
        ApkUtil.openAPP(getApplicationContext(), "com.xdja.jxclient");
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        stopService(new Intent(this, OnlineService.class));
        stopService(new Intent(this, UploadGpsService.class));
        ReckonAgent.getInstance(this).stopAnalytics();
        // 注销下载广播
        if (receiver != null) {
            unregisterReceiver(receiver);
        }

    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        switch (position) {
            case 3:
                toolbar.setVisibility(View.VISIBLE);
                toolbar.setNavigationIcon(R.drawable.none_back);
                setRightImgGone(false);
                toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                    }
                });
                mToolbarTitle.setText("收藏");
                break;
            case 0:
                toolbar.setVisibility(View.GONE);
                break;
            case 1:
                toolbar.setNavigationIcon(R.drawable.none_back);
                setRightImgGone(false);
                toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                    }
                });
                toolbar.setVisibility(View.VISIBLE);
                mToolbarTitle.setText("日程");
                break;
            case 2:
                setRightImgGone(false);
                toolbar.setNavigationIcon(R.drawable.none_back);
                toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                    }
                });
                toolbar.setVisibility(View.VISIBLE);
                mToolbarTitle.setText("排行榜");
                break;
            default:
                break;
        }

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    public void onResponse(Call<String> call, Response<String> response) {
        String data = response.body();
        if (response.isSuccessful()) {
            resolveData(data);
        }
    }

    @Override
    public void onFailure(Call<String> call, Throwable t) {
        CustomToast.INSTANCE.showToast(this, "网络异常");
    }


    /**
     * 解析数据
     *
     * @param data 返回数据
     */
    private void resolveData(String data) {
        LogUtil.d(data);
        if (data != null) {
            VersionRoot versionRoot = GsonUtil.parseJsonWithGson(data, VersionRoot.class);
            if (versionRoot.getStatus() == 0 && versionRoot.getData() != null && versionRoot.getData().getUpdate()) {
                fileUrl = versionRoot.getData().getFileUrl();
                versionHandler.sendEmptyMessage(998);
            }
        } else {
        }

    }


    private Handler versionHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 998) {
                showUpdateDialog();
            }
        }
    };


    private void showUpdateDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("发现新版本可以升级");
        //当点确定按钮时从服务器上下载 新的apk 然后安装
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Log.i(TAG, "下载apk,更新");
                downLoadApk();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.setCancelable(false);
        dialog.show();
    }

    private void downLoadApk() {
        final ProgressDialog pd;    //进度条对话框
        pd = new ProgressDialog(this);
        pd.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        pd.setMessage("正在下载更新");
        pd.setCancelable(false);
        pd.show();
        new Thread() {
            @Override
            public void run() {
                try {
                    file = DownLoadManager.getFileFromServer(fileUrl, pd, MainActivity.this);
                    pd.dismiss(); //结束掉进度条对话框
                } catch (Exception e) {
                    Toast.makeText(getApplicationContext(), "下载新版本失败", Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }
            }
        }.start();

    }


    /**
     * 接受下载完成后的intent
     */
    class DownloadCompleteReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            //判断是否下载完成的广播
            if (Objects.equals(intent.getAction(), Constant.UPDATE_APPS)) {
                installApk();
            }
        }
    }

    private void installApk() {
        // 需要自己修改安装包路径
        File file = new File(Environment.getExternalStorageDirectory(),
                "津门智慧战士.apk");
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        FileProvider7.setIntentDataAndType(this,
                intent, "application/vnd.android.package-archive", file, true);
        startActivity(intent);
    }

}

