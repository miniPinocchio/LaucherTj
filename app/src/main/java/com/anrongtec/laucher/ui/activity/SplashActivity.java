package com.anrongtec.laucher.ui.activity;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.view.KeyEvent;
import android.view.View;
import android.widget.TextView;

import com.anrongtec.laucher.R;
import com.anrongtec.laucher.TjApp;
import com.anrongtec.laucher.base.BaseActivity;
import com.anrongtec.laucher.bean.sos.SosNewBean;
import com.anrongtec.laucher.bean.sos.SosNewBeans;
import com.anrongtec.laucher.netconfig.UserService;
import com.anrongtec.laucher.ui.MainActivity;
import com.anrongtec.laucher.util.ApkUtil;
import com.anrongtec.laucher.util.DialogUtil;
import com.anrongtec.laucher.util.GsonUtil;
import com.anrongtec.laucher.util.LogUtil;
import com.anrongtec.laucher.util.dialog.SpotsDialog;
import com.xdja.feedbacklibrary.FeedBackManager;
import com.xdja.feedbacklibrary.constants.FBConfig;
import com.xdja.reckon.ReckonAgent;
import com.xdja.reckon.function.StateListener;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import didikee.com.permissionshelper.PermissionsHelper;
import didikee.com.permissionshelper.permission.DangerousPermissions;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/***
 * 启动页
 * @author huiliu
 */
public class SplashActivity extends BaseActivity implements Callback<String> {

    private static final String AUTHORITY = "com.xdja.app.pj/";
    //private static final String MY_AUTH = "com.anrongtec.gateway";
    private static final String MY_AUTH = "com.anrongtec.laucher";
    private static final Uri STUDENT_ALL_URI = Uri.parse("content://" + AUTHORITY + MY_AUTH);
    @BindView(R.id.tv_handle_mdm)
    TextView mTvHandleMdm;
    @BindView(R.id.tv_handle_sos)
    TextView mTvHandleSos;
    @BindView(R.id.tv_login_tips)
    TextView mTvLoginTips;
    private SsoReceiver mReceiver;
    private ActiveReceiver mActiveReceiver;
    private String IS_ACTIVE_ACTION = "com.nq.mdm.check.user.status";
    private String MDM_PACKAGE_NAME = "com.nq.mdm";
    private String MDM_ACTION = "com.anrongtec.launcher.isActive";
    private SosNewBean mUserInfo;
    /**
     * 提示信息
     */
    private StringBuffer tipsText;
    private boolean isActive;

    /**
     * app所需要的全部危险权限
     */
    static final String[] PERMISSIONS = new String[]{
            DangerousPermissions.PHONE,
            DangerousPermissions.STORAGE,
            DangerousPermissions.LOCATION,
            DangerousPermissions.SMS
    };
    private PermissionsHelper permissionsHelper;
    private String ACTIVE_ZERO = "0";//激活
    private boolean mIsCheck = false;

    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (mIsCheck) {
//                checkData();
            }
        }
    };
    private SpotsDialog.Builder mBuilder;
    private Dialog mDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LogUtil.d("----==============onCreate()");
        setContentView(R.layout.activity_splash);
        ButterKnife.bind(this);
        tipsText = new StringBuffer();
        registerActive();
        registerSSO();
        checkPermissions();
    }

    @Override
    protected void onResume() {
        super.onResume();
        LogUtil.d("----==============onResume()");
        if (mIsCheck) {
            checkData();
        }
    }

    /**
     * 检验用户信息和统一认证 、激活
     */
    private void init() {
        String isActive = UserService.getIsActive(this);
        if (ACTIVE_ZERO.equals(isActive)) {
            mIsCheck = false;
            checkData();
        } else {
            checkIsActive();
        }
        //TODO 初始化应用反馈sdk   因为内存溢出
//        initFB();
    }

    private void checkPermissions() {
        tipsText.append("检查是否激活设备\n");
        mTvLoginTips.setText(tipsText);
        permissionsHelper = new PermissionsHelper(this, PERMISSIONS, true);
        if (permissionsHelper.checkAllPermissions(PERMISSIONS)) {
            permissionsHelper.onDestroy();
            mHandler.sendEmptyMessageDelayed(99, 5000);
            init();
        } else {
            //申请权限
            permissionsHelper.startRequestNeedPermissions();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        permissionsHelper.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        permissionsHelper.onActivityResult(requestCode, resultCode, data);
    }

    /**
     * 开启统计服务
     */
    private void startStatistics() {
        ReckonAgent.getInstance(this)
                .config("20.3.2.44", "9082")
                .startAnalytics(mUserInfo.getId())
                .addStateListener(new StateListener() {
                    @Override
                    public void reportState(String s) {
                        LogUtil.d("===统计服务===\n" + s);
                    }
                });
    }


    /**
     * 检测是否激活mdm
     */
    private void checkIsActive() {
        tipsText.append("检查是否激活设备\n");
        mTvLoginTips.setText(tipsText);
        Intent intent = new Intent();
        intent.setPackage(MDM_PACKAGE_NAME);
        //对应BroadcastReceiver中intentFilter的action
        intent.setAction(IS_ACTIVE_ACTION);
        //发送广播
        sendBroadcast(intent);
    }

    /**
     * 注册统一认证广播
     */
    private void registerSSO() {
        IntentFilter filter = new IntentFilter();
        filter.addAction(MY_AUTH);
        mReceiver = new SsoReceiver();
        registerReceiver(mReceiver, filter);
    }

    /**
     * 接收mdm激活广播
     */
    private void registerActive() {
        IntentFilter filter = new IntentFilter();
        filter.addAction(MDM_ACTION);
        mActiveReceiver = new ActiveReceiver();
        registerReceiver(mActiveReceiver, filter);
    }

    @OnClick({R.id.tv_handle_mdm, R.id.tv_handle_sos})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_handle_mdm:
                //调用mdm启动页面
                Intent startIntent = new Intent();
                startIntent.setClassName("com.nq.mdm", "com.nq.ui.view.MDMSplashActivity");
                startActivity(startIntent);
                break;
            case R.id.tv_handle_sos:
                ApkUtil.openAPP(this, "com.xdja.uaac");
                break;
            default:
                break;
        }
    }

    public class ActiveReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            boolean status = intent.getBooleanExtra("status", false);
            isActive = status;
            if (status) {
                UserService.saveActive("0");
                checkData();
            } else {
                //调用mdm启动页面
                Intent startIntent = new Intent();
                startIntent.setClassName("com.nq.mdm", "com.nq.ui.view.MDMSplashActivity");
                startActivity(startIntent);
            }
        }
    }

    public class SsoReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            tipsText.append("收到统一认证广播\n");
            mTvLoginTips.setText(tipsText);
            checkData();
        }
    }

    /**
     * 统一认证
     */
    public void checkData() {
        tipsText.append("校验统一认证用户\n");
        LogUtil.d("校验统一认证用户\n");
        mTvLoginTips.setText(tipsText);
        //通过ContentResolver获得一个调用ContentProvider对象
        ContentResolver contentResolver = getContentResolver();
        Cursor cursor = contentResolver.query(STUDENT_ALL_URI, null, null, null, null);
        if (cursor != null) {
            for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
                int resultCode = cursor.getColumnIndex("resultCode");
                int message = cursor.getColumnIndex("message");
                int billStr = cursor.getColumnIndex("billStr");
                String resultCodes = cursor.getString(resultCode);
                /*
                  "resultCode：错误代码
                 message：错误信息
                 billStr：票据信息（加密的数据）
                 说明：
                 0:认证成功 -99 未知错误 -1:票据信息为空 -2:权限被拒绝 -3:传入信息错误 -4:票据信息已过期"
                 */
                switch (resultCodes) {
                    case "0":
                        tipsText.append("校验用户信息\n");
                        mTvLoginTips.setText(tipsText);
                        String billStrs = cursor.getString(billStr);
                        if (billStrs != null) {
//                            TjApp.getRetrofit().getUserInfo("strToken=" + billStrs).enqueue(this);
                            if (mDialog == null||!mDialog.isShowing()) {
                                mDialog = DialogUtil.loadingDialog(SplashActivity.this, "正在打开桌面,请稍后");
                            }
                            TjApp.getRetrofit().getNewUserInfo("strToken=" + billStrs).enqueue(this);
                        }
                        break;
                    case "-1":
                    case "-2":
                    case "-3":
                    case "-4":
                    case "-99":
                        final String messages = cursor.getString(message);
                        DialogUtil.createNoticeDialog(this, "温馨提示", messages, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.dismiss();
                                tipsText.append(messages + "\n");
                                mTvLoginTips.setText(tipsText);
                                checkData();
                            }
                        });
                        break;
                    default:
                        break;
                }

            }
            cursor.close();
        } else {
            DialogUtil.createNoticeDialog(this, "温馨提示", "请先安装统一认证客户端", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    finish();
                }
            });
        }
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
        tipsText.append("网络异常，请检查手机网络\n");
        mTvLoginTips.setText(tipsText);
        DialogUtil.createSelectDialog(this, "攻城狮温馨提示", "经检查，目前网络不给力，休息一下", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                checkData();
            }
        }, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                startActivity(new Intent(SplashActivity.this, VisitorActivity.class));
                if (mDialog != null) {
                    mDialog.dismiss();
                }
                mIsCheck = true;
            }
        });
    }

    /**
     * 解析数据
     *
     * @param data 返回数据
     */
    private void resolveData(String data) {
        mDialog.dismiss();
        tipsText.append("登录信息\n");
        mTvLoginTips.setText(tipsText);
        if (data != null) {
            SosNewBeans sosBeans = GsonUtil.parseJsonWithGson(data, SosNewBeans.class);
//            SosBeans sosBeans = GsonUtil.parseJsonWithGson(data, SosBeans.class);
            //验证结果flag：0验证通过，1票据信息过期，2票据签名验证未通过，3该票据信息不属于平台发放票据，4其它错误
            switch (sosBeans.getStatus()) {
                case 0:
                    mUserInfo = sosBeans.getData();
                    if (mUserInfo != null) {
                        UserService.saveNewUserInfo(mUserInfo);
                        startStatistics();
//                      FeedBackManager.getInstance().setUserInfo(mUserInfo.getCode(), mUserInfo.getName());
                        startAct(MainActivity.class);
                        mIsCheck = true;
                        finish();
                    }
                    break;
                case 1:
                case 2:
                case 3:
                case 4:
                    DialogUtil.createNoticeDialog(this, "温馨提示", sosBeans.getMsg(), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            checkData();
                        }
                    });
                    break;
                default:
                    break;
            }
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        LogUtil.d("----==============onPause()");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        LogUtil.d("----==============onRestart()");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        LogUtil.d("----==============onDestroy()");
        if (mReceiver != null && mActiveReceiver != null) {
            unregisterReceiver(mReceiver);
            unregisterReceiver(mActiveReceiver);
        }
    }

    /**
     * 屏蔽回退
     *
     * @param keyCode
     * @param event
     * @return
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        //不执行父类点击事件
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            return true;
        }
        //继续执行父类其他点击事件
        return super.onKeyDown(keyCode, event);
    }


    /**
     * 初始化反馈sdk
     */
    private void initFB() {
        FBConfig.Builder builder = new FBConfig.Builder(getApplicationContext())
                //是否自动上传异常
                .setUseAutoUploadExcetpion(true)
                //反馈地址
                .setFeedbackServerBaseUrl("http://20.3.2.44")
                //文件下载地址
                .setFileDownloadUrl("http://20.3.2.44")
                //文件上传地址
                .setFileUploadUrl("http://20.3.2.44");
        FeedBackManager.getInstance().setConfig(builder.build());
        FeedBackManager.getInstance().init(getApplicationContext());
    }
}
