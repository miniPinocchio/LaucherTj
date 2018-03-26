package com.anrongtec.laucher.ui.activity;

import android.os.Bundle;
import android.widget.ListView;

import com.anrongtec.laucher.R;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author huiliu
 */
public class SettingActivity extends ToolBarActivity {

    @BindView(R.id.lv_setting)
    ListView mLvSetting;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        ButterKnife.bind(this);
        setRightImgGone(true);
        iniData();
    }

    /**
     * 初始化数据
     */
    private void iniData() {
        ArrayList<String> setString = new ArrayList<>();
        setString.add("");
    }
}
