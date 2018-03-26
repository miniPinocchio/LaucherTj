package com.anrongtec.laucher.ui.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.anrongtec.laucher.R;
import com.anrongtec.laucher.bean.sos.SosNewBean;
import com.anrongtec.laucher.netconfig.Constant;
import com.anrongtec.laucher.netconfig.UserService;
import com.anrongtec.laucher.webview.SwipeRefreshWebView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 *
 * @author huiliu
 */
public class RankingFragment extends Fragment {


    Unbinder unbinder;
    @BindView(R.id.web_ranking)
    SwipeRefreshWebView mWebRanking;

    private List<Fragment> fragments;
    private List<String> titles;
    private FragmentPagerAdapter mAdapter;

    public RankingFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_ranking, container, false);
        unbinder = ButterKnife.bind(this, view);
//        initData();
        initViews();
        return view;
    }

    private void initViews() {
        SosNewBean userInfo = UserService.getNewUserInfo(getActivity());
        String code = userInfo.getCode();
        String depcode = userInfo.getDepcode();
        String url = Constant.RANKING_URL + "?code=" + code + "&depcode=" + depcode;
        setHasOptionsMenu(true);
        mWebRanking.autoRefresh(R.color.colorPrimary);
        WebView scrollView = mWebRanking.getScrollView();
        scrollView.loadUrl(url);
        scrollView.setWebViewClient(new SampleWebViewClient());
    }


private class SampleWebViewClient extends WebViewClient {
    @Override
    public boolean shouldOverrideUrlLoading(WebView view, String url) {
        mWebRanking.autoRefresh();
        view.loadUrl(url);
        return true;
    }
}

    public static final String ARGS_PAGE = "args_page";
    private int mPage;

    public static RankingFragment newInstance(int page) {
        Bundle args = new Bundle();

        args.putInt(ARGS_PAGE, page);
        RankingFragment fragment = new RankingFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    private void initData() {
        fragments = new ArrayList<>();
        Fragment aFragment = RankFirstFragment.newInstance(0);
        Fragment bFragment = RankSecondFragment.newInstance(1);
        Fragment cFragment = RankThirdFragment.newInstance(2);
        fragments.add(aFragment);
        fragments.add(bFragment);
        fragments.add(cFragment);

        titles = new ArrayList<>();
        titles.add("在线时长");
        titles.add("个人任务");
        titles.add("团队任务");
    }
}
