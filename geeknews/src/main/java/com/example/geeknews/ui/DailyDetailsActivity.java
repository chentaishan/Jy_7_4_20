package com.example.geeknews.ui;


import android.support.design.widget.AppBarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.geeknews.R;
import com.example.geeknews.bean.ZhihuDetailBean;
import com.example.geeknews.contract.DailyDetailsContract;
import com.example.geeknews.presenter.DailyDetailsPresenter;
import com.example.geeknews.ui.base.BaseMvpActivity;
import com.example.geeknews.utils.HtmlUtil;

public class DailyDetailsActivity extends BaseMvpActivity<DailyDetailsPresenter> implements DailyDetailsContract.View, View.OnClickListener {
    private ImageView mImg;
    private Toolbar mToolbar;
    private WebView mWebView;
    private NestedScrollView mScrollview;
    private AppBarLayout mAppbar;
    private FloatingActionButton mFab;

    @Override
    public void successUI(ZhihuDetailBean zhihuDetailBean) {

        // 图片 展示  title
        Glide.with(this).load(zhihuDetailBean.getImage()).into(mImg);

        mToolbar.setTitle(zhihuDetailBean.getTitle());

        setSupportActionBar(mToolbar);
        // webView 展示  JavaScript  css


        String htmlData = HtmlUtil.createHtmlData(zhihuDetailBean.getBody(), zhihuDetailBean.getCss(), zhihuDetailBean.getJs());

        mWebView.loadData(htmlData,HtmlUtil.MIME_TYPE,HtmlUtil.ENCODING);

    }

    @Override
    public void errorUI(String error) {

    }

    @Override
    protected void initViewActivity() {
        mImg = (ImageView) findViewById(R.id.top_img);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mWebView = (WebView) findViewById(R.id.webview);

        mAppbar = (AppBarLayout) findViewById(R.id.appbar);
        mFab = (FloatingActionButton) findViewById(R.id.fab);
        mFab.setOnClickListener(this);


    }

    @Override
    protected DailyDetailsPresenter initPresenter() {
        return new DailyDetailsPresenter();
    }

    @Override
    protected void initData() {

        String id = getIntent().getIntExtra("id",-1)+"";
        presenter.getDailyDetailsData(id);
    }

    @Override
    protected int getLayout() {
        return R.layout.daily_details_activity;
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fab:
                // TODO 20/05/14
                break;
            default:
                break;
        }
    }
}
