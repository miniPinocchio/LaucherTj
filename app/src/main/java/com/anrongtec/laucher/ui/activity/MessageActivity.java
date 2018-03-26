package com.anrongtec.laucher.ui.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.anrongtec.laucher.R;
import com.anrongtec.laucher.adapter.LoadMoreAdapter;
import com.anrongtec.laucher.adapter.MessageAdapter;
import com.anrongtec.laucher.bean.message.AdmComparingInfo;
import com.anrongtec.laucher.bean.message.Page;
import com.anrongtec.laucher.bean.message.ZYMessage;
import com.anrongtec.laucher.bean.sos.SosBean;
import com.anrongtec.laucher.netconfig.Constant;
import com.anrongtec.laucher.netconfig.UserService;
import com.anrongtec.laucher.ui.fragment.BuyFragment;
import com.anrongtec.laucher.ui.fragment.EnterFragment;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bingoogolapple.refreshlayout.BGARefreshLayout;

/**
 * @author liuhui
 *         消息列表
 */
public class MessageActivity extends ToolBarActivity {

    @BindView(R.id.view_breakfast_list)
    RecyclerView mViewBreakfastList;
    @BindView(R.id.rl_refresh)
    BGARefreshLayout mRefreshLayout;
    @BindView(R.id.tv_focus_data)
    TextView tvFocusData;
    @BindView(R.id.ll_focus_data)
    LinearLayout llFocusData;
    @BindView(R.id.tv_wild_data)
    TextView tvWildData;
    @BindView(R.id.ll_wild_data)
    LinearLayout llWildData;
    @BindView(R.id.tv_message_search)
    TextView tvMessageSearch;

    private List<AdmComparingInfo> mMessages;
    private MessageAdapter mAdapter;
    private LoadMoreAdapter mLoadMoreAdapter;
    private int page = 1;
    private Page mPage;

    private String token;
    private String filter = "";
    private int pageSize = 5;
    private int mNetType;
    private ZYMessage mZyMessage;
    public static final String TABLENAME = "adm_comparing_info";
    public String tableId;
    private static final String PRV_SELINDEX = "PREV_SELINDEX";
    private static final String[] FRAGMENT_TAG = {"focus", "wild"};
    private int selindex = 0;

    private EnterFragment mEnterFragment;//进站
    private BuyFragment mBuyFragment;//购票


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);
        ButterKnife.bind(this);
//        initFragment(savedInstanceState);
        select(0);
        setRightImgGone(false);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        Intent push = getIntent();
        SosBean userInfo = UserService.getUserInfo(this);
        String code = userInfo.getCode();
        filter = "watch_user_code=" + code;
        if (push != null) {
            int intExtra = push.getIntExtra(Constant.PUSH_DATA, 0);
            switch (intExtra) {
                case 98:
                    resetImg();
                    tvWildData.setTextColor(Color.parseColor("#0ba5f4"));
                    select(1);
                    mBuyFragment.getData(filter,5,1);
                    break;
                case 99:
                    resetImg();
                    tvFocusData.setTextColor(Color.parseColor("#0ba5f4"));
                    select(0);
                    mEnterFragment.getData("",filter,5,1);
                    break;
                default:
                    resetImg();
                    tvFocusData.setTextColor(Color.parseColor("#0ba5f4"));
                    select(0);
                    break;
            }

        }
    }

    private void initFragment(Bundle savedInstanceState) {
        FragmentManager sfm = getSupportFragmentManager();
        if (savedInstanceState != null) {
            //读取上一次界面Save的时候tab选中的状态
            selindex = savedInstanceState.getInt(PRV_SELINDEX, selindex);
            mEnterFragment = (EnterFragment) sfm.findFragmentByTag(FRAGMENT_TAG[0]);
            mBuyFragment = (BuyFragment) sfm.findFragmentByTag(FRAGMENT_TAG[1]);
        } else {
            select(selindex);
        }
    }

    private void select(int i) {
        //开启事务，fragment的控制是由事务来实现的
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        //隐藏所有fragment
        hideFragment(transaction);
        switch (i) {
            case 0:
                //初始化fragment并添加到事务中，如果为null就new一个
                if (mEnterFragment == null) {
                    mEnterFragment = EnterFragment.getInstance();
                    transaction.add(R.id.frame_container, mEnterFragment, FRAGMENT_TAG[0]);
                } else {
                    //显示需要显示的fragment
                    transaction.show(mEnterFragment);
                }
                break;
            case 1:
                if (mBuyFragment == null) {
                    mBuyFragment = BuyFragment.getInstance();
                    transaction.add(R.id.frame_container, mBuyFragment, FRAGMENT_TAG[1]);
                } else {
                    transaction.show(mBuyFragment);
                }
                break;
            default:
                break;

        }
        transaction.commit();
    }

    private void hideFragment(FragmentTransaction transaction) {
        if (mEnterFragment != null) {
            transaction.hide(mEnterFragment);
        }
        if (mBuyFragment != null) {
            transaction.hide(mBuyFragment);
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
    }


    @OnClick({R.id.ll_focus_data, R.id.ll_wild_data, R.id.tv_message_search})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_focus_data:
                resetImg();
                tvFocusData.setTextColor(Color.parseColor("#0ba5f4"));
                select(0);
                break;
            case R.id.ll_wild_data:
                resetImg();
                tvWildData.setTextColor(Color.parseColor("#0ba5f4"));
                select(1);
                break;
            case R.id.tv_message_search:
                //关注和失控详情页
                startActivity(new Intent(this, DetainSearchActivity.class));
                break;
            default:
                break;
        }
    }

    private void resetImg() {
        tvFocusData.setTextColor(Color.parseColor("#2e2e54"));
        tvWildData.setTextColor(Color.parseColor("#2e2e54"));
    }
}
