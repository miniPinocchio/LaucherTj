package com.anrongtec.laucher.ui.fragment;


import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;

import com.anrongtec.laucher.R;
import com.anrongtec.laucher.netconfig.Constant;
import com.anrongtec.laucher.netconfig.UserService;
import com.anrongtec.laucher.webview.SwipeRefreshWebView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 *
 * @author huiliu
 */
public class ScheduleFragment extends Fragment {


    @BindView(R.id.web_schedule)
    SwipeRefreshWebView mWebSchedule;
    Unbinder unbinder;
    @BindView(R.id.iv_go_back)
    ImageView mIvGoBack;
    private WebView mWebView;

    public ScheduleFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_schedule, container, false);
        unbinder = ButterKnife.bind(this, view);
        initView();
        return view;
    }


    @SuppressLint({"SetJavaScriptEnabled", "ClickableViewAccessibility"})
    private void initView() {
        String code = UserService.getNewUserInfo(getActivity()).getCode();
        String url = Constant.SCHEDULE_URL + "?code=" + code;
        setHasOptionsMenu(true);
        mWebView = mWebSchedule.getScrollView();
        mWebSchedule.autoRefresh(R.color.colorPrimary);
        WebView scrollView = mWebView;
        scrollView.loadUrl(url);
        scrollView.setWebViewClient(new SampleWebViewClient());
    }

    @OnClick(R.id.iv_go_back)
    public void onViewClicked(View view) {
        switch (view.getId()){
            case R.id.iv_go_back:
                mWebView.goBack();
                break;
            default:
                break;
        }
    }



    private class SampleWebViewClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            mWebSchedule.autoRefresh();
            view.loadUrl(url);
            return false;
        }

    }


    /**
     * 解决滑动冲突
     */
    @Override
    public void onResume() {
        super.onResume();
    }

    public static final String ARGS_PAGE = "args_page";
    private int mPage;

    public static ScheduleFragment newInstance(int page) {
        Bundle args = new Bundle();

        args.putInt(ARGS_PAGE, page);
        ScheduleFragment fragment = new ScheduleFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }


}
