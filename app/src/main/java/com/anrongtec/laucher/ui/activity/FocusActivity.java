package com.anrongtec.laucher.ui.activity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import com.anrongtec.laucher.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author huiliu
 */
public class FocusActivity extends ToolBarActivity {

    @BindView(R.id.tv_main_search)
    TextView mTvMainSearch;
    @BindView(R.id.wv_focus)
    WebView mWvFocus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_focus);
        ButterKnife.bind(this);
        toolbar.setNavigationIcon(R.drawable.icon_title_back);
        initViews();

    }

    @SuppressLint("SetJavaScriptEnabled")
    private void initViews() {
        String fileUrl = "file:///android_asset/index.html";
        mWvFocus.getSettings().setDefaultTextEncodingName("utf-8");
        mWvFocus.loadUrl(fileUrl);
        mWvFocus.setWebViewClient(new WebViewClient());
        WebSettings webSettings = mWvFocus.getSettings();
        //设置为可调用js方法
        webSettings.setJavaScriptEnabled(true);
    }
}
