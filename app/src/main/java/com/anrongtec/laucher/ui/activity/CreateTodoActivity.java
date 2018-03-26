package com.anrongtec.laucher.ui.activity;

import android.os.Bundle;
import android.webkit.WebView;

import com.anrongtec.laucher.R;
import com.anrongtec.laucher.bean.sos.SosNewBean;
import com.anrongtec.laucher.netconfig.Constant;
import com.anrongtec.laucher.netconfig.UserService;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author huiliu
 */
public class CreateTodoActivity extends ToolBarActivity {

    @BindView(R.id.web_create)
    WebView mWebCreate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_todo);
        ButterKnife.bind(this);
        setRightImgGone(false);
        initView();
    }

    private void initView() {
        SosNewBean userInfo = UserService.getNewUserInfo(this);
        String code = userInfo.getCode();
        String url = Constant.CREATE_TODO + "?code=" + code;
        mWebCreate.loadUrl(url);
        mWebCreate.getSettings().setJavaScriptEnabled(true);
    }

}
