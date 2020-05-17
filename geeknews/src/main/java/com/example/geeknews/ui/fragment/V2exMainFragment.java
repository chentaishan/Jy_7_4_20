package com.example.geeknews.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;

import com.example.geeknews.R;
import com.example.geeknews.ui.NodeActivity;
import com.example.geeknews.ui.base.BaseFragment;
import com.example.geeknews.utils.Constants;

import java.util.ArrayList;
import java.util.List;

public class V2exMainFragment extends BaseFragment {
    private TabLayout mVtexMainTab;
    private ImageView mVtexMenuIv;
    private ViewPager mVtexMainVp;
    public static String[] typeStr = {"技术", "创意", "好玩", "Apple", "酷工作", "交易", "城市", "问与答", "最热", "全部", "R2"};
    public static String[] type = {"tech", "creative", "play", "apple", "jobs", "deals", "city", "qna", "hot", "all", "r2"};
    List<Fragment> fragments = new ArrayList<>();


    @Override
    protected void initData() {

        for (int i = 0; i < typeStr.length; i++) {
            VtexPagerFragment vtexPagerFragment = new VtexPagerFragment();

            Bundle bundle = new Bundle();
            bundle.putString(Constants.IT_VTEX_TYPE,type[i]);
            vtexPagerFragment.setArguments(bundle);
            fragments.add(vtexPagerFragment);

            mVtexMainTab.addTab(mVtexMainTab.newTab());

        }
        // viewPager fragment  tablayout
        mVtexMainVp.setAdapter(new FragmentPagerAdapter(getChildFragmentManager()) {
            @Override
            public Fragment getItem(int i) {
                return fragments.get(i);
            }

            @Override
            public int getCount() {
                return fragments.size();
            }

            @Nullable
            @Override
            public CharSequence getPageTitle(int position) {
                return typeStr[position];
            }
        });

        mVtexMainTab.setupWithViewPager(mVtexMainVp);



    }

    @Override
    protected void initView(View itemView) {

        mVtexMainTab = (TabLayout) itemView.findViewById(R.id.tab_vtex_main);
        mVtexMenuIv = (ImageView) itemView.findViewById(R.id.iv_vtex_menu);
        mVtexMainVp = (ViewPager) itemView.findViewById(R.id.vp_vtex_main);


        mVtexMainTab.setTabMode(TabLayout.MODE_SCROLLABLE);

        mVtexMenuIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(getActivity(), NodeActivity.class));
            }
        });

    }

    @Override
    protected int getLayout() {
        return R.layout.fragment_vtex_main;
    }
}
