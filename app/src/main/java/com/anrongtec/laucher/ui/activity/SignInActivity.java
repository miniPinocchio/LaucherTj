package com.anrongtec.laucher.ui.activity;

import android.os.Bundle;

import com.anrongtec.laucher.R;

/**
 * @author huiliu
 */
public class SignInActivity extends ToolBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        setRightImgGone(false);

    }
}
