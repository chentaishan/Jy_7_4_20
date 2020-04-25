package com.example.geeknews.ui;

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.Toolbar;
import android.widget.FrameLayout;

import com.example.geeknews.R;
import com.example.geeknews.bean.User;
import com.example.geeknews.contract.DailyContract;
import com.example.geeknews.presenter.DailyPresenter;
import com.example.geeknews.ui.base.BaseActivity;
import com.example.geeknews.ui.base.BaseMvpActivity;
import com.example.geeknews.ui.fragment.DailyFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends BaseActivity  {


    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.content)
    FrameLayout content;
    @BindView(R.id.navigation)
    NavigationView navigation;
    @BindView(R.id.drawer_layout)
    DrawerLayout drawerLayout;


    @Override
    protected void initView() {
        super.initView();

        // 替换fragment 为content

        FragmentManager supportFragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = supportFragmentManager.beginTransaction();

        fragmentTransaction.replace(R.id.content,new DailyFragment()).commit();

    }

    @Override
    protected void initData() {


    }

    @Override
    protected int getLayout() {
        return R.layout.activity_main;
    }


}
