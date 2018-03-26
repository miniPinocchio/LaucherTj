package com.anrongtec.laucher.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.CompoundButton;

import com.anrongtec.laucher.R;
import com.anrongtec.laucher.adapter.MainTabAppAdapter;
import com.anrongtec.laucher.bean.AppModel;
import com.anrongtec.laucher.bean.entity.GroupEntity;
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
public class ManagerAppsActivity extends ToolBarActivity implements View.OnClickListener, CompoundButton.OnCheckedChangeListener {

    @BindView(R.id.rv_manager_apps)
    RecyclerView mRvManagerApps;

    private ArrayList<GroupEntity> mGroups;
    private List<AppModel> mModels;
    private List<Integer> mPostions;
    private MainTabAppAdapter mAppsAdapter;
    private List<AppModel> mAppsFromLiteOrm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manager_apps);
        ButterKnife.bind(this);
        toolbar.setNavigationIcon(R.drawable.icon_title_back);
        setRightImg(R.drawable.img_right_ok);
        initData();
        initView();
    }

    private void initData() {
        mAppsFromLiteOrm = ApkUtil.getAppsFromLiteOrm(this, false);
        mPostions = new ArrayList<>();
        mModels = ApkUtil.getAppsFromSystem(this);

        for (int i = 0; i < mModels.size(); i++) {
            for (int j = 0; j < mAppsFromLiteOrm.size(); j++) {
                if (mModels.get(i).getAppPackName().equals(mAppsFromLiteOrm.get(j).getAppPackName())){
                    mModels.get(i).setCheck(true);
                    mPostions.add(i);
                }
            }
        }
    }

    private void initView() {
        mAppsAdapter = new MainTabAppAdapter(this, mModels, this);
        mRvManagerApps.setLayoutManager(new GridLayoutManager(this, 4, GridLayoutManager.VERTICAL, false));
        mRvManagerApps.setAdapter(mAppsAdapter);
        ivSetting.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_setting_right:
                LiteOrmDBUtil.deleteWhere(AppModel.class,"isSystem",new Object[]{false});
                for (int i = 0; i < mPostions.size(); i++) {
                    LiteOrmDBUtil.insert(mModels.get(mPostions.get(i)));
                }
                LocalBroadcastManager manager = LocalBroadcastManager.getInstance(this);
                Intent intent = new Intent();
                intent.setAction(Constant.MANAGER_APPS);
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
    }
}
