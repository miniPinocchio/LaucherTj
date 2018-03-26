package com.anrongtec.laucher.ui.fragment;


import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.anrongtec.laucher.R;
import com.anrongtec.laucher.TjApp;
import com.anrongtec.laucher.adapter.CollectAdapter;
import com.anrongtec.laucher.adapter.LoadMoreAdapter;
import com.anrongtec.laucher.bean.favorite.Favorite;
import com.anrongtec.laucher.bean.favorite.Favorites;
import com.anrongtec.laucher.bean.favorite.Page;
import com.anrongtec.laucher.bean.favorite.Rows;
import com.anrongtec.laucher.netconfig.UserService;
import com.anrongtec.laucher.util.GsonUtil;
import com.anrongtec.laucher.util.LogUtil;
import com.anrongtec.laucher.widget.CustomToast;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import cn.bingoogolapple.refreshlayout.BGAMeiTuanRefreshViewHolder;
import cn.bingoogolapple.refreshlayout.BGARefreshLayout;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 *
 * @author huiliu
 */
public class CollectFragment extends Fragment implements Callback<String>, BGARefreshLayout.BGARefreshLayoutDelegate, LoadMoreAdapter.LoadMoreApi, View.OnClickListener {


    @BindView(R.id.tv_main_search)
    EditText mTvMainSearch;
    @BindView(R.id.rv_collect)
    RecyclerView mRvCollect;
    Unbinder unbinder;
    @BindView(R.id.rl_collect_refresh)
    BGARefreshLayout mRlCollectRefresh;

    private List<Rows> mStrings;
    private int pageSize = 5;
    private int page = 1;

    private String filter;
    private LoadMoreAdapter mLoadMoreAdapter;
    private Page mPage1;

    public CollectFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_collect, container, false);
        unbinder = ButterKnife.bind(this, view);
        initView();
        initRefreshLayout();
        return view;
    }

    private void initRefreshLayout() {
        // 为BGARefreshLayout 设置代理
        mRlCollectRefresh.setDelegate(this);
        BGAMeiTuanRefreshViewHolder meiTuanRefreshViewHolder = new BGAMeiTuanRefreshViewHolder(getActivity(), true);
        meiTuanRefreshViewHolder.setPullDownImageResource(R.mipmap.bga_refresh_mt_pull_down);
        meiTuanRefreshViewHolder.setChangeToReleaseRefreshAnimResId(R.drawable.bga_refresh_mt_change_to_release_refresh);
        meiTuanRefreshViewHolder.setRefreshingAnimResId(R.drawable.bga_refresh_mt_refreshing);
        mRlCollectRefresh.setRefreshViewHolder(meiTuanRefreshViewHolder);
    }

    private void getData() {
        String code = UserService.getNewUserInfo(getActivity()).getCode();
        TjApp.getRetrofit().getCollect("code=" + code, pageSize, page).enqueue(this);
    }

    private void initView() {
        mStrings = new ArrayList<>();
        LinearLayoutManager manager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL,
                false);
        CollectAdapter adapter = new CollectAdapter(getActivity(), mStrings, this);
        mLoadMoreAdapter = new LoadMoreAdapter(getActivity(), adapter);
        mLoadMoreAdapter.setLoadMoreListener(this);
        mRvCollect.setLayoutManager(manager);
        mRvCollect.setAdapter(mLoadMoreAdapter);
    }


    public static final String ARGS_PAGE = "args_page";
    private int mPage;

    public static CollectFragment newInstance(int page) {
        Bundle args = new Bundle();
        args.putInt(ARGS_PAGE, page);
        CollectFragment fragment = new CollectFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onStart() {
        super.onStart();
        LogUtil.d("==onStart==");
        getData();
    }

    @Override
    public void onPause() {
        super.onPause();
        LogUtil.d("==onPause==");
    }

    @Override
    public void onResume() {
        super.onResume();
        LogUtil.d("==onResume==");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        LogUtil.d("==onDestroyView==");
        unbinder.unbind();
    }

    @OnClick(R.id.tv_main_search)
    public void onViewClicked() {
    }

    @Override
    public void onResponse(Call<String> call, Response<String> response) {
        String data = response.body();
        if (response.isSuccessful()) {
            resolveData(data);
        } else {
            CustomToast.INSTANCE.showToast(getActivity(), "服务器异常" + response.code());
        }
    }

    @Override
    public void onFailure(Call<String> call, Throwable t) {
        CustomToast.INSTANCE.showToast(getActivity(), "网络异常");
    }

    /**
     * 解析数据
     *
     * @param data
     */
    private void resolveData(String data) {
        if (data != null) {
            Favorites favorites = GsonUtil.parseJsonWithGson(data, Favorites.class);
            if (favorites != null) {
                Favorite data1 = favorites.getData();
                if (data1 != null) {
                    mPage1 = data1.getPage();
                    List<Rows> rows = data1.getRows();
                    if (rows != null) {
                        mStrings.addAll(rows);
                    }
                    if (rows == null || rows.size() <= 0) {
                        mRlCollectRefresh.endRefreshing();
                        mLoadMoreAdapter.loadAllDataCompleted();
                        return;
                    }
                    mLoadMoreAdapter.loadCompleted();
                    mRlCollectRefresh.endRefreshing();
                }
            }
        }
    }


    @Override
    public void onBGARefreshLayoutBeginRefreshing(BGARefreshLayout refreshLayout) {
        page = 1;
        mStrings.clear();
        getData();
    }

    @Override
    public boolean onBGARefreshLayoutBeginLoadingMore(BGARefreshLayout refreshLayout) {
        return false;
    }

    @Override
    public void loadMore() {
        if (page < mPage1.getTotalPage()) {
            page += 1;
            getData();
        } else {
            Handler handler = new Handler();
            final Runnable r = new Runnable() {
                @Override
                public void run() {
                    mLoadMoreAdapter.loadAllDataCompleted();
                }
            };
            handler.post(r);

        }
    }

    @Override
    public void onClick(View v) {
        Integer tag = (Integer) v.getTag();
        switch (v.getId()) {
            case R.id.ll_favorite_item:
//                Intent intent = new Intent(getActivity(), ToDoDetailActivity.class);
//                intent.putExtra(Constant.INTENT_DATA, mStrings.get(tag));
//                startActivity(intent);
                break;
            default:
                break;
        }
    }
}
