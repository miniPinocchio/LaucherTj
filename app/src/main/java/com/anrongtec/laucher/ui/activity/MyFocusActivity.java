package com.anrongtec.laucher.ui.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.anrongtec.laucher.R;
import com.anrongtec.laucher.ui.fragment.CooperationFragment;
import com.anrongtec.laucher.ui.fragment.EKongFragment;

/**
 * @author huiliu
 */
public class MyFocusActivity extends AppCompatActivity {

    private EKongFragment eKongFragment;//E控
    private CooperationFragment mCooperationFragment;//实名盾

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_focus);

    }
}
