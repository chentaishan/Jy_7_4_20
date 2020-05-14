package com.example.geeknews.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;

import com.example.geeknews.R;
import com.example.geeknews.adapter.VtexPagerAdapter;
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


        mVtexMainTab.setTabMode(TabLayout.MODE_SCROLLABLE);
        mVtexMainTab.setupWithViewPager(mVtexMainVp);
        for (int i = 0; i < type.length; i++) {
            VtexPagerFragment fragment = new VtexPagerFragment();
            Bundle bundle = new Bundle();
            bundle.putString(Constants.IT_VTEX_TYPE, type[i]);
            fragment.setArguments(bundle);
            mVtexMainTab.addTab(mVtexMainTab.newTab());
            fragments.add(fragment);
        }
        VtexPagerAdapter mAdapter = new VtexPagerAdapter(getChildFragmentManager(), fragments);
        mVtexMainVp.setAdapter(mAdapter);
    }

    @Override
    protected void initView(View itemView) {

        mVtexMainTab = (TabLayout) itemView.findViewById(R.id.tab_vtex_main);
        mVtexMenuIv = (ImageView) itemView.findViewById(R.id.iv_vtex_menu);
        mVtexMainVp = (ViewPager) itemView.findViewById(R.id.vp_vtex_main);


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
