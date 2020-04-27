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
 * 知乎日报 总页面
 */

public class ZhihuFragment extends BaseFragment {
    private TabLayout mTablayout;
    private ViewPager mViewpager;
    private List<Fragment> fragmentList;

    @Override
    protected void initData() {

        mViewpager.setAdapter(new FragmentPagerAdapter(getChildFragmentManager()) {
            @Override
            public Fragment getItem(int i) {
                return fragmentList.get(i);
            }

            @Override
            public int getCount() {
                return fragmentList.size();
            }
        });
        mTablayout.setupWithViewPager(mViewpager);
        mTablayout.getTabAt(0).setText("日报");
        mTablayout.getTabAt(1).setText("专栏");
        mTablayout.getTabAt(2).setText("热门");
    }

    @Override
    protected void initView(View itemView) {

        mTablayout = (TabLayout) itemView.findViewById(R.id.tablayout);
        mViewpager = (ViewPager) itemView.findViewById(R.id.viewpager);

        fragmentList = new ArrayList<>();
        fragmentList.add(new DailyFragment());
        fragmentList.add(new TopicFragment());
        fragmentList.add(new HotFragment());





    }

    @Override
    protected int getLayout() {
        return R.layout.zhihu_fragment;
    }
}
