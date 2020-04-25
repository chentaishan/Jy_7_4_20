package com.example.jy_7_4_20;

import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.FrameLayout;

import com.example.jy_7_4_20.Fragment.DailyFragment;
import com.example.jy_7_4_20.base.BaseActivity;

public class MainActivity extends BaseActivity {


    private Toolbar mToolbar;
    private FrameLayout mContent;
    private NavigationView mNavigation;
    private DrawerLayout mDrawerlayout;

    @Override
    protected void initData() {

    }


    @Override
    protected void initView() {

        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mContent = (FrameLayout) findViewById(R.id.content);
        mNavigation = (NavigationView) findViewById(R.id.navigation);
        mDrawerlayout = (DrawerLayout) findViewById(R.id.drawerlayout);

        mToolbar.setTitle("HHHHHH");
        setSupportActionBar(mToolbar);

        initPragment();

        mNavigation.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                return false;
            }
        });
    }

    private void initPragment() {
        DailyFragment dailyFragment = new DailyFragment();

        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.content,dailyFragment).commit();
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_main;
    }
}
