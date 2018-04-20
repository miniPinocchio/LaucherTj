package com.anrongtec.laucher.ui.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.anrongtec.laucher.R;
import com.anrongtec.laucher.adapter.VisitorAdapter;
import com.anrongtec.laucher.bean.AppModel;
import com.anrongtec.laucher.db.LiteOrmDBUtil;
import com.anrongtec.laucher.netconfig.Constant;
import com.anrongtec.laucher.util.ApkUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author huiliu
 */
public class VisitorActivity extends ToolBarActivity implements View.OnClickListener {

    private final String CONTECTS = "com.android.contacts";
    @BindView(R.id.rv_main_tab_visitor_app)
    RecyclerView mRvMainTabVisitorApp;
    private List<Integer> mPostions;
    private List<AppModel> mSystemModels;

    private VisitorAdapter mAppsAdapter;
    private List<AppModel> mAppsFromLiteOrm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_visitor);
        ButterKnife.bind(this);
        setRightImgGone(false);
        initData();
        initView();

    }

    private void initData() {
        mAppsFromLiteOrm = ApkUtil.getAppsFromLiteOrm(this, true);
        mPostions = new ArrayList<>();
        mSystemModels = ApkUtil.getAppsFromSystems(this);
        for (int i = 0; i < mSystemModels.size(); i++) {
            for (int j = 0; j < mAppsFromLiteOrm.size(); j++) {
                if (mSystemModels.get(i).getAppPackName().equals(mAppsFromLiteOrm.get(j).getAppPackName())) {
                    mSystemModels.get(i).setCheck(true);
                    mPostions.add(i);
                }
            }
        }
    }

    private void initView() {
        mAppsAdapter = new VisitorAdapter(this, mSystemModels, this);
        //直接使用GroupedGridLayoutManager实现子项的Grid效果
        GridLayoutManager manager = new GridLayoutManager(this, 4);
        mRvMainTabVisitorApp.setLayoutManager(manager);
        mRvMainTabVisitorApp.setAdapter(mAppsAdapter);
        ivSetting.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_setting_right:
                LiteOrmDBUtil.deleteWhere(AppModel.class, "isSystem", new Object[]{true});
                for (int i = 0; i < mPostions.size(); i++) {
                    LiteOrmDBUtil.insert(mSystemModels.get(mPostions.get(i)));
                }
                LocalBroadcastManager manager = LocalBroadcastManager.getInstance(this);
                Intent intent = new Intent();
                intent.setAction(Constant.MANAGER_SYS_APPS);
                manager.sendBroadcast(intent);
                finish();
                break;
            case R.id.rl_main_tab_app:
                Integer position = (Integer) v.getTag();
                if (CONTECTS.equals(mSystemModels.get(position).getAppPackName())) {
                    intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:"));
                    startActivity(intent);
                } else {
                    ApkUtil.openAPP(this, mSystemModels.get(position).getAppPackName());
                }
            default:
                break;
        }
    }

}
