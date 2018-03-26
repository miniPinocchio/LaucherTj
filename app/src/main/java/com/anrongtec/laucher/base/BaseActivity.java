package com.anrongtec.laucher.base;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

/**
 * activity的基类
 *
 * @author sun.bl
 * @version [1.0, 2015-6-29]
 */
@SuppressLint({"NewApi", "Registered"})
public class BaseActivity extends AppCompatActivity {
    private boolean isFirstFocused = true;

    // 后台通信工具类
//    protected HttpUtil httpUtil;

    public String TAG = "BaseActivity";

    @Override
    protected void onCreate(@Nullable
                                    Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        httpUtil = new HttpUtil(this, this);
    }

    @Override
    protected void onDestroy() {
//        httpUtil.onDestroy();
        super.onDestroy();
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (isFirstFocused && hasFocus) {
            isFirstFocused = false;
            onWindowInitialized();
        }
    }

    /**
     * 界面渲染完毕，可在这里进行初始化工作，建议在这里启动线程进行初始化工作，同时可以获取界面View的大小
     *
     * @see [onWindowFocusChanged]
     */
    public void onWindowInitialized() {

    }

    /**
     * 显示提示Toast
     *
     * @param message
     * @see [类、类#方法、类#成员]
     */
    public void showToastMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    /**
     * 启动activity
     *
     * @param actClazz
     */
    protected void startAct(Class actClazz) {
        startActivity(new Intent(this, actClazz));
    }


    /**
     * 启动activity
     *
     * @param actClazz
     */
    protected void startAct(Class actClazz, Intent intent) {
        startActivity(intent);
    }

}
