package com.anrongtec.laucher.ui.fragment;

import android.annotation.SuppressLint;

import java.util.HashMap;

/**
 *
 * @author huiliu
 * @date 2017/10/22
 *
 * @email liu594545591@126.com
 * @introduce
 */
public class FragmentFactory {
    @SuppressLint("UseSparseArrays")
    private static HashMap<Integer, BaseFragment> mBaseFragments = new HashMap<>();

    public static BaseFragment createFragment(int pos) {
        BaseFragment baseFragment = mBaseFragments.get(pos);
        if (baseFragment == null) {
            switch (pos) {
                case 0:
                    //签到
                    baseFragment = new SignFragment();
                    break;
                case 1:
                    //统计
                    baseFragment = new SignStatisticsFragment();
                    break;
//                case 2:
//                    baseFragment = new EntertainmentFragment();//娱乐
//                    break;
//                case 3:
//                    baseFragment = new SportsFragment();//体育
//                    break;
//                case 4:
//                    baseFragment = new FinanceFragment();//财经
//                    break;
//                case 5:
//                    baseFragment = new ScienceFragment();//科技
//                    break;
//                case 6:
//                    baseFragment = new ModeFragment();//时尚
//                    break;
//                case 7:
//                    baseFragment = new VideoFragment();//视频
//                    break;
//                case 8:
//                    baseFragment = new  DirectSeedingFragment();//直播
//                    break;
                default:
                    break;

            }
            mBaseFragments.put(pos, baseFragment);
        }
        return baseFragment;
    }
}
