package com.anrongtec.laucher.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.anrongtec.laucher.ui.fragment.MainFragment;
import com.anrongtec.laucher.ui.fragment.RankingFragment;
import com.anrongtec.laucher.ui.fragment.ScheduleFragment;

/**
 * @author huiliu
 * @date 2017/10/22
 * @email liu594545591@126.com
 * @introduce
 */
public class MyFragmentPagerAdapter extends FragmentPagerAdapter {
    public final int COUNT = 2;
    private String[] titles = new String[]{"Tab1", "Tab2", "Tab3", "Tab4"};
    private Context context;

    public MyFragmentPagerAdapter(FragmentManager fm, Context context) {
        super(fm);
        this.context = context;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
//            case 0:
//                return CollectFragment.newInstance(position);
            case 0:
                return MainFragment.newInstance(position);
            case 1:
                return ScheduleFragment.newInstance(position);
//            case 2:
//                return RankingFragment.newInstance(position);
            default:
                return RankingFragment.newInstance(position);
//                return RankingFragment.newInstance(position);
        }
    }

    @Override
    public int getCount() {
        return COUNT;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return titles[position];
    }

    private int mChildCount = 0;

    @Override
    public void notifyDataSetChanged() {
        mChildCount = getCount();
        super.notifyDataSetChanged();
    }

    @Override
    public int getItemPosition(Object object) {
        if (mChildCount > 0) {
            mChildCount--;
            return POSITION_NONE;
        }
        return super.getItemPosition(object);
    }

}