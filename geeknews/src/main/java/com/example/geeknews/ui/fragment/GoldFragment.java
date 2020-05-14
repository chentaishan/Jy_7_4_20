package com.example.geeknews.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;

import com.example.geeknews.R;
import com.example.geeknews.bean.TabEvent;
import com.example.geeknews.ui.GoldManagerActivity;
import com.example.geeknews.ui.base.BaseFragment;
import com.example.geeknews.utils.Constants;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

/**
 * 稀土掘金 fragment
 */
public class GoldFragment extends BaseFragment {

    public static String[] typeStr = {"Android", "iOS", "前端", "后端", "设计", "产品", "阅读", "工具资源"};


    private TabLayout mGoldMainTab;
    private ImageView mGoldMenuIv;
    private ViewPager mGoldMainVp;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        EventBus.getDefault().register(this);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void updateTabUI(TabEvent tabEvent){

        mGoldMainTab.removeAllTabs();

        for (int i = 0; i <   Constants.isSelected.size(); i++) {
            if (  Constants.isSelected.get(i)){

                mGoldMainTab.addTab(mGoldMainTab.newTab().setText(  Constants.title.get(i)));
            }
        }
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initView(View itemView) {

        for (int i = 0; i < typeStr.length; i++) {
            Constants.title.add(typeStr[i]);

            if (i % 2 == 0) {
                Constants.isSelected.add(true);
            } else {
                Constants.isSelected.add(false);
            }
        }

        mGoldMainTab = (TabLayout) itemView.findViewById(R.id.tab_gold_main);
        mGoldMenuIv = (ImageView) itemView.findViewById(R.id.iv_gold_menu);
        mGoldMainVp = (ViewPager) itemView.findViewById(R.id.vp_gold_main);
        mGoldMainTab.setTabMode(TabLayout.MODE_SCROLLABLE);
        for (int i = 0; i <   Constants.isSelected.size(); i++) {
            if (  Constants.isSelected.get(i)){
                mGoldMainTab.addTab(mGoldMainTab.newTab().setText(  Constants.title.get(i)));
            }
        }


        mGoldMenuIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO 跳转到 管理页面

                startActivity(new Intent(getActivity(), GoldManagerActivity.class));

            }
        });
    }


    @Override
    protected int getLayout() {
        return R.layout.fragment_gold_main;
    }
}
