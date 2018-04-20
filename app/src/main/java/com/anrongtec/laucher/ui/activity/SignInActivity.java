package com.anrongtec.laucher.ui.activity;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.anrongtec.laucher.R;
import com.anrongtec.laucher.ui.fragment.BaseFragment;
import com.anrongtec.laucher.ui.fragment.FragmentFactory;
import com.anrongtec.laucher.widget.NoScrollViewPager;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author huiliu
 */
public class SignInActivity extends ToolBarActivity {

    @BindView(R.id.tab_sign_in)
    TabLayout mTabSignIn;
    @BindView(R.id.viewpager_sign_in)
    NoScrollViewPager mViewpagerSignIn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        ButterKnife.bind(this);
        setRightImgGone(false);
        initData();
    }

    private void initData() {
        ShortPagerAdapter adapter = new ShortPagerAdapter(getSupportFragmentManager());
        mViewpagerSignIn.setAdapter(adapter);
        mViewpagerSignIn.setScroll(false);
        mTabSignIn.setupWithViewPager(mViewpagerSignIn);
    }


    private class ShortPagerAdapter extends FragmentPagerAdapter {
        public String[] mTitle;

        public ShortPagerAdapter(FragmentManager fm) {
            super(fm);
            mTitle = getResources().getStringArray(R.array.tab_short_title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mTitle[position];
        }

        @Override
        public BaseFragment getItem(int position) {
            return FragmentFactory.createFragment(position);
        }

        @Override
        public int getCount() {
            return 2;
        }
    }
}
