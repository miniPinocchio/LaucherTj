package com.anrongtec.laucher.ui.activity;

import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.anrongtec.laucher.R;
import com.anrongtec.laucher.netconfig.Constant;
import com.anrongtec.laucher.webview.SwipeRefreshWebView;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author huiliu
 */
public class LimitCarActivity extends ToolBarActivity {

    @BindView(R.id.wv_limit_car)
    SwipeRefreshWebView mWvLimitCar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_limit_car);
        ButterKnife.bind(this);
        setRightImgGone(false);
        initData();
    }

    /**
     * 初始化数据
     */
    private void initData() {
        String url = Constant.LIMIT_CAR;
        mWvLimitCar.autoRefresh(R.color.colorPrimary);
        WebView scrollView = mWvLimitCar.getScrollView();
        scrollView.loadUrl(url);
        scrollView.setWebViewClient(new SampleWebViewClient());
    }

    private class SampleWebViewClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            mWvLimitCar.autoRefresh();
            view.loadUrl(url);
            return false;
        }

    }
}
