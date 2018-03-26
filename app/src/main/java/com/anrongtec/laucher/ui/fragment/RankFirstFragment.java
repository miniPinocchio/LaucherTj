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
public class RankFirstFragment extends Fragment {


    public RankFirstFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_rank_first, container, false);
        return view;
    }


    public static final String ARGS_PAGE = "args_page";
    private int mPage;

    public static RankFirstFragment newInstance(int page) {
        Bundle args = new Bundle();

        args.putInt(ARGS_PAGE, page);
        RankFirstFragment fragment = new RankFirstFragment();
        fragment.setArguments(args);
        return fragment;
    }

}
