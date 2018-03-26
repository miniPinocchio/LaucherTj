package com.anrongtec.laucher.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.SparseBooleanArray;
import android.view.View;
import android.widget.CompoundButton;

import com.anrongtec.laucher.R;
import com.anrongtec.laucher.adapter.MainTabAppAdapter;
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
public class ManagerSysAppsActivity extends ToolBarActivity implements View.OnClickListener, CompoundButton.OnCheckedChangeListener {

    @BindView(R.id.rv_manager_sys_apps)
    RecyclerView mRvManagerSysApps;
    private List<Integer> mPostions;
    private List<AppModel> mSystemModels;

    private SparseBooleanArray mCheckStates = new SparseBooleanArray();
    private MainTabAppAdapter mAppsAdapter;
    private List<AppModel> mAppsFromLiteOrm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manager_sys_apps);
        ButterKnife.bind(this);
        setRightImg(R.drawable.img_right_ok);
        initData();
        initView();
    }

    private void initData() {
        mAppsFromLiteOrm = ApkUtil.getAppsFromLiteOrm(this, true);
        mPostions = new ArrayList<>();
        mSystemModels = ApkUtil.getAppsFromSystems(this);
        for (int i = 0; i < mSystemModels.size(); i++) {
            for (int j = 0; j < mAppsFromLiteOrm.size(); j++) {
                if (mSystemModels.get(i).getAppPackName().equals(mAppsFromLiteOrm.get(j).getAppPackName())){
                    mSystemModels.get(i).setCheck(true);
                    mPostions.add(i);
                }
            }
        }
    }

    private void initView() {
        mAppsAdapter = new MainTabAppAdapter(this, mSystemModels, this);
        //直接使用GroupedGridLayoutManager实现子项的Grid效果
        GridLayoutManager manager = new GridLayoutManager(this, 4);
        mRvManagerSysApps.setLayoutManager(manager);
        mRvManagerSysApps.setAdapter(mAppsAdapter);
        ivSetting.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_setting_right:
                LiteOrmDBUtil.deleteWhere(AppModel.class,"isSystem",new Object[]{true});
                for (int i = 0; i < mPostions.size(); i++) {
                    LiteOrmDBUtil.insert(mSystemModels.get(mPostions.get(i)));
                }
                LocalBroadcastManager manager = LocalBroadcastManager.getInstance(this);
                Intent intent = new Intent();
                intent.setAction(Constant.MANAGER_SYS_APPS);
                manager.sendBroadcast(intent);
                finish();
                break;
            default:
                break;
        }
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        int position = (int) buttonView.getTag();
        if (isChecked) {
            mPostions.add(position);
        } else {
            mPostions.remove(mPostions.indexOf(position));
        }
        mSystemModels.get(position).setCheck(isChecked);
        mAppsAdapter.setModels(mSystemModels);
    }
}
