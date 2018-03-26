package com.anrongtec.laucher.ui.activity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.util.ArrayMap;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.anrongtec.laucher.R;
import com.anrongtec.laucher.bean.sos.SosNewBean;
import com.anrongtec.laucher.netconfig.Constant;
import com.anrongtec.laucher.netconfig.UserService;
import com.anrongtec.laucher.util.DialogUtil;
import com.anrongtec.laucher.util.PhoneUtils;
import com.anrongtec.laucher.webview.SwipeRefreshWebView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author huiliu
 */
public class TeamActivity extends ToolBarActivity {

    @BindView(R.id.web_team)
    SwipeRefreshWebView mWebTeam;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_team);
        ButterKnife.bind(this);
        toolbar.setNavigationIcon(R.drawable.icon_title_back);
        setRightImgGone(false);
        initView();
    }

    private void initView() {
        SosNewBean userInfo = UserService.getNewUserInfo(this);
        String depcode = userInfo.getDepcode();
        String code = userInfo.getCode();
        String url = Constant.TEAM_URL + "team.htm?deptCode=" + depcode + "&watchUserCode=" + code;
        mWebTeam.autoRefresh(R.color.colorPrimary);
        WebView scrollView = mWebTeam.getScrollView();
        scrollView.loadUrl(url);
        scrollView.setWebViewClient(new TeamActivity.SampleWebViewClient());
    }

    private class SampleWebViewClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            /**
             * 通过判断拦截到的url是否含有pre，来辨别是http请求还是调用android方法的请求
             */
            String pre = "http://";
            String protocol = "protocol://android";
            if (url.contains(pre)) {
                //该url是http请求，用webview加载url
                mWebTeam.autoRefresh();
                view.loadUrl(url);
                return false;
            } else if (url.contains(protocol)) {
                //该url是调用android方法的请求，通过解析url中的参数来执行相应方法
                Map<String, String> map = getParamsMap(url, protocol);
                String code = map.get("code");
                String data = map.get("data");
                parseCode(code, data);
                return true;
            }

            return false;
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

    private void parseCode(final String code, final String data) {
        DialogUtil.createTipDialog(this, "温馨提醒", "确定要拨打电话吗", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                    //执行打电话的操作，具体代码省略
                    PhoneUtils.call(TeamActivity.this, data);
            }
        },new DialogInterface.OnClickListener(){
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        if (code.equals("toast")) {
            try {
                JSONObject json = new JSONObject(data);
                String toast = json.optString("data");
                Toast.makeText(this, toast, Toast.LENGTH_SHORT).show();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return;
        }
    }

}
