package com.anrongtec.laucher.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Created by huiliu on 2017/10/22.
 *
 * @email liu594545591@126.com
 * @introduce
 */
public class MPagerAdapter extends FragmentPagerAdapter {
    public MPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return null;
    }

    @Override
    public int getCount() {
        return 0;
    }

//    private static final int PAGE_COUNT = 3;
//
//    private Context mContext;
//
//    public MPagerAdapter(Context context, FragmentManager fm) {
//        super(fm);
//        this.mContext = context;
//    }
//
//    @Override
//    public Fragment getItem(int position) {
//        int type;
//        switch (position) {
//            case 0:
//                type = Constants.TYPE_TIMELINE_PUBLIC;
//                break;
//            case 1:
//                type = Constants.TYPE_TIMELINE_FRIEND;
//                break;
//            case 2:
//                type = Constants.TYPE_TIMELINE_MINE;
//                break;
//            default:
//                type = Constants.TYPE_TIMELINE_PUBLIC;
//                break;
//        }
//        return MainFragment.newInstance(type);
//    }
//
//    @Override
//    public int getCount() {
//        return PAGE_COUNT;
//    }
//
//    @Override
//    public CharSequence getPageTitle(int position) {
//        switch (position) {
//            case 0:
//                return "广场";
//            case 1:
//                return "好友";
//            case 2:
//                return "我";
//            default:
//                return "微博";
//        }
//    }
}
