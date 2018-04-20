package com.anrongtec.laucher.ui.fragment;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;


/**
 * A simple {@link Fragment} subclass.
 * @author huiliu
 */
public  class BaseFragment extends Fragment {

    protected Context mContent;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //上下文。
        mContent = getContext();
    }


}