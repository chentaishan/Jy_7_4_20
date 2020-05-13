package com.example.geeknews.ui;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.FrameLayout;

import com.bumptech.glide.Glide;
import com.example.geeknews.R;
import com.example.geeknews.bean.User;
import com.example.geeknews.contract.DailyContract;
import com.example.geeknews.presenter.DailyPresenter;
import com.example.geeknews.ui.base.BaseActivity;
import com.example.geeknews.ui.base.BaseMvpActivity;
import com.example.geeknews.ui.fragment.DailyFragment;
import com.example.geeknews.ui.fragment.GankFragment;
import com.example.geeknews.ui.fragment.WxFragment;
import com.example.geeknews.ui.fragment.ZhihuFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends BaseActivity {


    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.content)
    FrameLayout content;
    @BindView(R.id.navigation)
    NavigationView navigation;
    @BindView(R.id.drawer_layout)
    DrawerLayout drawerLayout;
    private FragmentManager supportFragmentManager;
    private ZhihuFragment zhihuFragment;
    private WxFragment wxFragment;

    // 最后一个使用的fragment
    private Fragment lastFragment;
    private GankFragment gankFragment;

    public static final int ZHIHU_TYPE = 0;
    public static final int WEIXIN_TYPE = 1;
    public static final int GANK_TYPE = 2;
    public static final int GOLD_TYPE = 3;
    public static final int V2EX_TYPE = 4;
    // 当前点击的类型
    private int type;

    /**
     * 记录当前被选择的菜单，
     * 当点击其他菜单时，把其他菜单置为选中状态
     * 同时把上一个菜单置为未选中状态
     */
    private MenuItem lastMenuItem;

    @Override
    protected void initView() {
        super.initView();



        setTitle("知乎日报");
        setSupportActionBar(toolbar);

        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open, R.string.close);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
        // 替换fragment 为content

        // 把知乎加载到管理器 且显示
        zhihuFragment = new ZhihuFragment();
        wxFragment = new WxFragment();
        gankFragment = new GankFragment();

        supportFragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = supportFragmentManager.beginTransaction();

        fragmentTransaction
                .add(R.id.content, zhihuFragment)
                .add(R.id.content, wxFragment)
                .add(R.id.content, gankFragment)
                .show(zhihuFragment)
                .hide(wxFragment)
                .hide(gankFragment)
                .commit();
        lastFragment = zhihuFragment;

        lastMenuItem = navigation.getMenu().findItem(R.id.zhihu);

        navigation.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {

                    case R.id.zhihu:
                        type = ZHIHU_TYPE;
                        break;
                    case R.id.wx:
                        type = WEIXIN_TYPE;
                        break;
                    case R.id.gank:
                        type = GANK_TYPE;
                        break;
                    case R.id.gold:
                        type = WEIXIN_TYPE;
                        break;
                    case R.id.vtex:
                        type = WEIXIN_TYPE;
                        break;

                }
                switchFragment(menuItem);
//                关闭侧滑
                drawerLayout.closeDrawer(Gravity.LEFT);

                return false;
            }
        });

    }

    /**
     * 获取当前 点击的fragment 对象
     *
     * @return
     */
    private Fragment getCurrFragment() {
        switch (type) {
            case ZHIHU_TYPE:
                if (zhihuFragment == null) {
                    zhihuFragment = new ZhihuFragment();
                }
                return zhihuFragment;

            case WEIXIN_TYPE:
                if (wxFragment == null) {
                    wxFragment = new WxFragment();
                }
                return wxFragment;
            case GANK_TYPE:
                if (gankFragment == null) {
                    gankFragment = new GankFragment();
                }
                return gankFragment;
            case GOLD_TYPE:
                break;
            case V2EX_TYPE:
                break;
            default:
                return null;

        }

        return null;
    }

    private static final String TAG = "MainActivity";
    public void switchFragment(MenuItem menuItem) {
        Fragment currFragment = getCurrFragment();
        if (currFragment==lastFragment){
            Log.d(TAG, "switchFragment: 现在就是这个页面，别点了");
            return;
        }
        FragmentTransaction fragmentTransaction = supportFragmentManager.beginTransaction();
        fragmentTransaction.show(currFragment)
                .hide(lastFragment)
                .commit();
        lastFragment = currFragment;
//        处理上个菜单的状态

        lastMenuItem.setChecked(false);

        lastMenuItem = menuItem;


        menuItem.setChecked(true);

    }


    @Override
    protected void initData() {


    }

    @Override
    protected int getLayout() {
        return R.layout.activity_main;
    }


}
