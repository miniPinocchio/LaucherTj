package com.anrongtec.laucher.ui.fragment;


import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.anrongtec.laucher.R;
import com.anrongtec.laucher.TjApp;
import com.anrongtec.laucher.adapter.CooperAdapter;
import com.anrongtec.laucher.adapter.LoadMoreAdapter;
import com.anrongtec.laucher.bean.detain.DetainRoot;
import com.anrongtec.laucher.bean.focusperson.CooperPage;
import com.anrongtec.laucher.bean.focusperson.CooperPerson;
import com.anrongtec.laucher.bean.focusperson.CooperPersonInfos;
import com.anrongtec.laucher.bean.focusperson.CooperPersons;
import com.anrongtec.laucher.bean.message.ZYMessage;
import com.anrongtec.laucher.bean.sos.SosBean;
import com.anrongtec.laucher.netconfig.Constant;
import com.anrongtec.laucher.netconfig.UserService;
import com.anrongtec.laucher.ui.activity.ZyMessageActivity;
import com.anrongtec.laucher.util.GsonUtil;
import com.anrongtec.laucher.widget.CustomToast;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
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
public class CooperationFragment extends Fragment implements Callback<String>, View.OnClickListener, BGARefreshLayout.BGARefreshLayoutDelegate, LoadMoreAdapter.LoadMoreApi {

    public static CooperationFragment instance = null;
    @BindView(R.id.rl_cooper_ticket)
    RecyclerView mRlCooperTicket;
    @BindView(R.id.bga_cooper_ticket)
    BGARefreshLayout mBgaCooperTicket;
    Unbinder unbinder;
    private List<CooperPersonInfos> mMessages;
    private CooperAdapter mAdapter;
    private LoadMoreAdapter mLoadMoreAdapter;
    private int page = 1;
    private CooperPage mPage;

    private String token;
    private String filter = "";
    private int pageSize = 5;
    private int mNetType;
    private ZYMessage mZyMessage;
    public static final String TABLENAME = "adm_comparing_info";
    public String tableId;
    private TextView tv_item_person_attention;
    private int position;
    private List<CooperPersonInfos> rows;
    private IntentFilter intentFilter;
    private CooperationFragment.LocalReceiver localReceiver;

    public static CooperationFragment getInstance() {
        if (instance == null) {
            instance = new CooperationFragment();
        }
        return instance;
    }

    public CooperationFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cooperation, container, false);
        unbinder = ButterKnife.bind(this, view);
        initData();
        initRefreshLayout();
        SosBean userInfo = UserService.getUserInfo(getActivity());
        String identifier = userInfo.getIdentifier();
        filter = "sxsfzh=" + identifier;
        getData(filter, pageSize, 1);
        return view;
    }
    @Override
    public void onDetach() {
        super.onDetach();
    }


    private void getData(String filter, int pageSize, int page) {
        mNetType = 2;
        TjApp.getRetrofit().getCooperInfos(filter, pageSize, page).enqueue(this);
    }

    private void initData() {
        intentFilter = new IntentFilter();
        intentFilter.addAction("com.anrongtec.focus");
        localReceiver = new CooperationFragment.LocalReceiver();
        //注册本地接收器
        LocalBroadcastManager.getInstance(getActivity()).registerReceiver(localReceiver, intentFilter);

        mMessages = new ArrayList<>();
        mAdapter = new CooperAdapter(mMessages, getActivity(), this);
        mLoadMoreAdapter = new LoadMoreAdapter(getActivity(), mAdapter);
        mLoadMoreAdapter.setLoadMoreListener(this);
        mRlCooperTicket.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        mRlCooperTicket.setAdapter(mLoadMoreAdapter);


    }

    private class LocalReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            SosBean userInfo = UserService.getUserInfo(getActivity());
            mMessages.clear();
            page = 1;
            String code = userInfo.getCode();
            filter = "";
            getData(filter, pageSize, 1);
        }
    }


    private void initRefreshLayout() {
        // 为BGARefreshLayout 设置代理
        mBgaCooperTicket.setDelegate(this);
        BGAMeiTuanRefreshViewHolder meiTuanRefreshViewHolder = new BGAMeiTuanRefreshViewHolder(getActivity(), true);
        meiTuanRefreshViewHolder.setPullDownImageResource(R.mipmap.bga_refresh_mt_pull_down);
        meiTuanRefreshViewHolder.setChangeToReleaseRefreshAnimResId(R.drawable.bga_refresh_mt_change_to_release_refresh);
        meiTuanRefreshViewHolder.setRefreshingAnimResId(R.drawable.bga_refresh_mt_refreshing);
        mBgaCooperTicket.setRefreshViewHolder(meiTuanRefreshViewHolder);
    }

    @Override
    public void onBGARefreshLayoutBeginRefreshing(BGARefreshLayout refreshLayout) {
        page = 1;
        mMessages.clear();
        getData(filter, pageSize, page);
    }

    @Override
    public boolean onBGARefreshLayoutBeginLoadingMore(BGARefreshLayout refreshLayout) {
        return false;
    }

    @SuppressLint("WrongConstant")
    @Override
    public void onClick(View view) {
        position = (int) view.getTag();
        CooperPersonInfos message = mMessages.get(position);
        tv_item_person_attention = view.findViewById(R.id.tv_item_person_attention);
        switch (view.getId()) {
            case R.id.rl_item_msg:
                Intent intent = new Intent(getActivity(), ZyMessageActivity.class);
                intent.setFlags(5);
                intent.putExtra(Constant.PUSH_DATA, message);
                startActivity(intent);
                break;
            case R.id.ll_focus_people_attention:
                tableId = String.valueOf(message.getId());
                getDetain();
                break;
            default:
                break;
        }
    }


    /**
     * 扣留人员
     */
    private void getDetain() {
        mNetType = 1;
        SosBean userInfo = UserService.getUserInfo(getActivity());
        String code = userInfo.getCode();
        TjApp.getRetrofit().getDetain(tableId, "2", code).enqueue(this);
    }

    /**
     * 收藏人员
     */
    private void favoriteItem() {
        mNetType = 1;
        SosBean userInfo = UserService.getUserInfo(getActivity());
        String code = userInfo.getCode();
        String name = userInfo.getName();
        TjApp.getRetrofit().favoriteItem(TABLENAME, tableId, "2", code, name).enqueue(this);
    }

    @Override
    public void onResponse(Call<String> call, Response<String> response) {
        String data = response.body();
        if (response.isSuccessful()) {
            resolveData(data);
        } else {
            mBgaCooperTicket.endRefreshing();
            mLoadMoreAdapter.loadAllDataCompleted();
            CustomToast.INSTANCE.showToast(getActivity(), "服务器异常" + response.code());
        }
    }


    @Override
    public void onFailure(Call<String> call, Throwable t) {
        CustomToast.INSTANCE.showToast(getActivity(), "网络异常");
    }


    @SuppressLint("HandlerLeak")
    Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 998) {
                mMessages.get(position).setStatus(2);
                mLoadMoreAdapter.loadCompleted();
                mBgaCooperTicket.endRefreshing();

            }
        }
    };

    /**
     * 解析数据
     *
     * @param data
     */
    private void resolveData(String data) {
        if (mNetType == 1) {
            if (data != null) {
                DetainRoot detainRoot = GsonUtil.parseJsonWithGson(data, DetainRoot.class);
                if (detainRoot.getStatus() == 0) {
                    mHandler.sendEmptyMessage(998);
                } else {
                    CustomToast.INSTANCE.showToast(getActivity(), detainRoot.getMsg());
                }
            }
        } else if (mNetType == 2) {
            if (data != null) {
                CooperPersons messageInfos = GsonUtil.parseJsonWithGson(data, CooperPersons.class);
                CooperPerson messageInfo = messageInfos.getData();
                mPage = messageInfo.getPage();
                rows = messageInfo.getRows();
                if (rows != null) {
                    mMessages.addAll(messageInfo.getRows());
                }
                if (rows == null || rows.size() <= 0) {
                    mBgaCooperTicket.endRefreshing();
                    mLoadMoreAdapter.loadAllDataCompleted();
                    return;
                }
                mLoadMoreAdapter.loadCompleted();
                mBgaCooperTicket.endRefreshing();

            }
        }
    }

    @Override
    public void loadMore() {
        if (page < mPage.getTotalPage()) {
            page += 1;
            getData(filter, pageSize, page);
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
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
        LocalBroadcastManager.getInstance(getActivity()).unregisterReceiver(localReceiver);
    }
}


