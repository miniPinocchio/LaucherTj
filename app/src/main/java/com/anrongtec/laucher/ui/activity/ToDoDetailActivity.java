package com.anrongtec.laucher.ui.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.Toast;

import com.anrongtec.laucher.R;
import com.anrongtec.laucher.bean.todo.TodoWorkDetail;
import com.anrongtec.laucher.netconfig.Constant;
import com.anrongtec.laucher.webview.SwipeRefreshWebView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author huiliu
 * @date 2017/11/28
 * @email liu594545591@126.com
 * @introduce
 */
public class ToDoDetailActivity extends ToolBarActivity {

    private final String mAppPackageName = "com.xjj.cloud.oa.debug";
    private final int REMARK_LENGTH = 100;
    @BindView(R.id.wv_to_do_detail)
    SwipeRefreshWebView mWvToDoDetail;
    @BindView(R.id.btn_to_do_deal)
    Button mBtnToDoDeal;
    private TodoWorkDetail mWorkDetail;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_to_do_detail);
        ButterKnife.bind(this);
        setRightImgGone(false);
        initData();
        initView();
    }

    /**
     * 获取intent数据
     */
    private void initData() {
        Intent intent = getIntent();
        mWorkDetail = intent.getParcelableExtra(Constant.TO_DO_DATA);
    }

    @OnClick(R.id.btn_to_do_deal)
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_to_do_deal:
                startAPP(mAppPackageName);
                break;
            default:
                break;
        }
    }

    @SuppressLint({"SetJavaScriptEnabled", "ClickableViewAccessibility"})
    private void initView() {
        String id = mWorkDetail.getId();
        String name = Uri.encode(mWorkDetail.getName());
        int type = mWorkDetail.getType();
        String title = Uri.encode(mWorkDetail.getTitle());
        String remark1 = mWorkDetail.getRemark();
        String remarkLimit = null;
        if (remark1.length() > REMARK_LENGTH) {
            remarkLimit = remark1.substring(0, REMARK_LENGTH);
        }
        String remark = Uri.encode(remarkLimit);


        String url = Constant.TODO_URL + "?id=" + id + "&type=" + type + "&title=" + title + "&name=" + name + "&remark=" + remark;
        mWvToDoDetail.autoRefresh(R.color.colorPrimary);
        WebView scrollView = mWvToDoDetail.getScrollView();
        scrollView.loadUrl(url);
        scrollView.setWebViewClient(new SampleWebViewClient());
    }

    private class SampleWebViewClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView cview, String url) {
            mWvToDoDetail.autoRefresh();
            cview.loadUrl(url);
            return false;
        }

    }

    /**
     * 启动一个app
     */
    public void startAPP(String appPackageName) {
        try {
            Intent intent = this.getPackageManager().getLaunchIntentForPackage(appPackageName);
            startActivity(intent);
        } catch (Exception e) {
            Toast.makeText(this, "没有安装", Toast.LENGTH_LONG).show();
        }
    }

}
