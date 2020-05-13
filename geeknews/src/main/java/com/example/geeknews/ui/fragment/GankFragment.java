package com.example.geeknews.ui.fragment;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.example.geeknews.R;
import com.example.geeknews.ui.base.BaseFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * 微信
 */
public class GankFragment extends BaseFragment {
    private TabLayout mTablayout;
    private ViewPager mViewpager;
    List<Fragment> fragments = new ArrayList<>();

    @Override
    protected void initData() {

    }

    @Override
    protected void initView(View root) {

        fragments.add(new GankSubFragment());
        fragments.add(new FuliFragment());

        mTablayout = (TabLayout) root.findViewById(R.id.tablayout);
        mViewpager = (ViewPager) root.findViewById(R.id.viewpager);
        mViewpager.setAdapter(new FragmentPagerAdapter(getChildFragmentManager()) {
            @Override
            public Fragment getItem(int i) {
                return fragments.get(i);
            }

            @Override
            public int getCount() {
                return fragments.size();
            }
        });
        mTablayout.setupWithViewPager(mViewpager);
        mTablayout.getTabAt(0).setText("Android");
        mTablayout.getTabAt(1).setText("美女");
    }

    @Override
    protected int getLayout() {
        return R.layout.gank_fragment;
    }
}
