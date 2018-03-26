package com.anrongtec.laucher.ui.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.anrongtec.laucher.R;

/**
 * A simple {@link Fragment} subclass.
 * @author huiliu
 */
public class RankThirdFragment extends Fragment {


    public RankThirdFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_rank_third, container, false);
    }

    public static final String ARGS_PAGE = "args_page";
    private int mPage;

    public static RankThirdFragment newInstance(int page) {
        Bundle args = new Bundle();

        args.putInt(ARGS_PAGE, page);
        RankThirdFragment fragment = new RankThirdFragment();
        fragment.setArguments(args);
        return fragment;
    }

}
