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
public class WeatherActivity extends ToolBarActivity {

    @BindView(R.id.web_weather)
    SwipeRefreshWebView mWebWeather;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);
        ButterKnife.bind(this);
        toolbar.setNavigationIcon(R.drawable.icon_title_back);
        setRightImgGone(false);
        initView();
    }

    private void initView() {
        mWebWeather.autoRefresh(R.color.colorPrimary);
        WebView scrollView = mWebWeather.getScrollView();
        scrollView.loadUrl(Constant.WEATHER_URL);
        scrollView.setWebViewClient(new SampleWebViewClient());
    }

    private class SampleWebViewClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            //该url是http请求，用webview加载url
            mWebWeather.autoRefresh();
            view.loadUrl(url);
            return false;
        }
    }

}
