package com.anrongtec.laucher.ui.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.ArrayMap;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.TextView;

import com.anrongtec.laucher.R;
import com.anrongtec.laucher.bean.sos.SosNewBean;
import com.anrongtec.laucher.netconfig.Constant;
import com.anrongtec.laucher.netconfig.UserService;
import com.anrongtec.laucher.webview.SwipeRefreshWebView;
import com.anrongtec.laucher.widget.CustomToast;

import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author huiliu
 */
public class TodoActivity extends ToolBarActivity{

    @BindView(R.id.tv_main_search)
    TextView mTvMainSearch;
    @BindView(R.id.web_team)
    SwipeRefreshWebView webTeam;
    @BindView(R.id.btn_to_do_deal)
    Button mBtnToDoDeal;


    private final String mAppPackageName = "com.xjj.cloud.oa.debug";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todo);
        setRightImgGone(false);
        ButterKnife.bind(this);
        initView();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @SuppressLint("SetJavaScriptEnabled")
    private void initView() {
        SosNewBean userInfo = UserService.getNewUserInfo(this);
        String code = userInfo.getCode();
//        String url = Constant.TODO_OVERVIEW + "?code=" + code;
        String url = Constant.HOST + "backlog.htm?code=" + code;
        webTeam.autoRefresh(R.color.colorPrimary);
        WebView scrollView = webTeam.getScrollView();
        scrollView.setWebViewClient(new SampleWebViewClient());
        scrollView.getSettings().setJavaScriptEnabled(true);
        scrollView.addJavascriptInterface(new Contact(TodoActivity.this), "myObj");
        scrollView.loadUrl(url);
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

    /**
     * 启动一个app
     */
    public void startAPP(String appPackageName) {
        try {
            Intent intent = this.getPackageManager().getLaunchIntentForPackage(appPackageName);
            startActivity(intent);
        } catch (Exception e) {
            CustomToast.INSTANCE.showToast(this, "请前往应用仓库下载\"OA办公\"");
        }
    }

    private class SampleWebViewClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            webTeam.autoRefresh();
            view.loadUrl(url);
//            isShowCreate();
            return true;
        }
    }


    private Map<String, String> getParamsMap(String url, String pre) {
        ArrayMap<String, String> queryStringMap = new ArrayMap<>();
        if (url.contains(pre)) {
            int index = url.indexOf(pre);
            int end = index + pre.length();
            String queryString = url.substring(end + 1);
            String[] queryStringSplit = queryString.split("&");

            String[] queryStringParam;
            for (String qs : queryStringSplit) {
                if (qs.toLowerCase().startsWith("data=")) {
                    //单独处理data项，避免data内部的&被拆分
                    int dataIndex = queryString.indexOf("data=");
                    int lastIndexOf = queryString.lastIndexOf("}");
                    String dataValue = queryString.substring(dataIndex + 19, lastIndexOf - 2);
                    queryStringMap.put("data", dataValue);
                } else {
                    queryStringParam = qs.split("=");

                    String value = "";
                    if (queryStringParam.length > 1) {
                        //避免后台有时候不传值,如“key=”这种
                        value = queryStringParam[1];
                    }
                    queryStringMap.put(queryStringParam[0].toLowerCase(), value);
                }
            }
        }
        return queryStringMap;
    }

    @Override
    public void onBackPressed() {
        webTeam.setGoBack(TodoActivity.this);
    }

    //定义接口，提供给JS调用
    class Contact {
        private TodoActivity mTodoActivity;

        public Contact(TodoActivity todoActivity) {
            this.mTodoActivity = todoActivity;
        }

        @JavascriptInterface
        public void callAndroid(final String type) {
            CustomToast.INSTANCE.showToast(TodoActivity.this, "调动成功");
            mTodoActivity.finish();
        }


    }


}