package com.anrongtec.laucher.ui.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.anrongtec.laucher.R;
import com.anrongtec.laucher.adapter.LoadMoreAdapter;
import com.anrongtec.laucher.adapter.MessageAdapter;
import com.anrongtec.laucher.bean.message.AdmComparingInfo;
import com.anrongtec.laucher.bean.message.Page;
import com.anrongtec.laucher.bean.message.ZYMessage;
import com.anrongtec.laucher.netconfig.Constant;
import com.anrongtec.laucher.ui.fragment.CooperationFragment;
import com.anrongtec.laucher.ui.fragment.EKongFragment;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bingoogolapple.refreshlayout.BGARefreshLayout;

/**
 * @author huiliu
 */
public class EkongFocusActivity extends ToolBarActivity {

    @BindView(R.id.tv_message_search)
    TextView mTvMessageSearch;
    @BindView(R.id.tv_ekong_data)
    TextView mTvEkongData;
    @BindView(R.id.ll_ekong_data)
    LinearLayout mLlEkongData;
    @BindView(R.id.tv_cooper_data)
    TextView mTvCooperData;
    @BindView(R.id.ll_cooper_data)
    LinearLayout mLlCooperData;
    @BindView(R.id.frame_ekong_container)
    FrameLayout mFrameContainer;
    @BindView(R.id.view_breakfast_list)
    RecyclerView mViewBreakfastList;
    @BindView(R.id.rl_refresh)
    BGARefreshLayout mRlRefresh;
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
    private static final String[] FRAGMENT_TAG = {"cooper","ekong",};
    private int selindex = 0;

    private EKongFragment eKongFragment;//E控
    private CooperationFragment mCooperationFragment;//实名盾


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ekong_focus);
        ButterKnife.bind(this);
        select(0);
        setRightImgGone(false);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        Intent push = getIntent();
        if (push != null) {
            int intExtra = push.getIntExtra(Constant.PUSH_DATA, 0);
            switch (intExtra) {
                case 96:
                    resetImg();
                    mTvEkongData.setTextColor(Color.parseColor("#0ba5f4"));
                    select(3);
                    break;
                case 97:
                    resetImg();
                    mTvCooperData.setTextColor(Color.parseColor("#0ba5f4"));
                    select(2);
                    break;
                default:
                    resetImg();
                    mTvEkongData.setTextColor(Color.parseColor("#0ba5f4"));
                    select(0);
                    break;
            }

        }
    }

    private void select(int i) {
        //开启事务，fragment的控制是由事务来实现的
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        //隐藏所有fragment
        hideFragment(transaction);
        switch (i) {
            case 1:
                if (eKongFragment == null) {
                    eKongFragment = EKongFragment.getInstance();
                    transaction.add(R.id.frame_ekong_container, eKongFragment, FRAGMENT_TAG[1]);
                } else {
                    transaction.show(eKongFragment);
                }
                break;
            case 0:
                if (mCooperationFragment == null) {
                    mCooperationFragment = CooperationFragment.getInstance();
                    transaction.add(R.id.frame_ekong_container, mCooperationFragment, FRAGMENT_TAG[0]);
                } else {
                    transaction.show(mCooperationFragment);
                }
                break;
            default:
                break;

        }
        transaction.commit();
    }

    private void hideFragment(FragmentTransaction transaction) {
        if (eKongFragment != null) {
            transaction.hide(eKongFragment);
        }
        if (mCooperationFragment != null) {
            transaction.hide(mCooperationFragment);
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
    }


    @OnClick({R.id.ll_ekong_data, R.id.ll_cooper_data, R.id.tv_message_search})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_ekong_data:
                resetImg();
                mTvEkongData.setTextColor(Color.parseColor("#0ba5f4"));
                select(1);
                break;
            case R.id.ll_cooper_data:
                resetImg();
                mTvCooperData.setTextColor(Color.parseColor("#0ba5f4"));
                select(0);
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
        mTvEkongData.setTextColor(Color.parseColor("#2e2e54"));
        mTvCooperData.setTextColor(Color.parseColor("#2e2e54"));
    }
}
