package com.example.geeknews.ui;


import android.support.design.widget.AppBarLayout;
import android.support.v7.widget.Toolbar;
import android.webkit.WebView;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.geeknews.R;
import com.example.geeknews.bean.ZhihuDetailBean;
import com.example.geeknews.contract.ZhihuDetailsContract;
import com.example.geeknews.presenter.DailyDetailsPresenter;
import com.example.geeknews.ui.base.BaseMvpActivity;
import com.example.geeknews.utils.HtmlUtil;

public class DailyDetailsActivity extends BaseMvpActivity<DailyDetailsPresenter> implements ZhihuDetailsContract.View {


    private ImageView mImgTop;
    private Toolbar mToolbar;
    private AppBarLayout mAppbar;
    private WebView mWebview;

    @Override
    public void successUI(ZhihuDetailBean zhihuDetailBean) {

        Glide.with(this).load(zhihuDetailBean.getImage()).into(mImgTop);

        // 加载css 和js
        String htmlData = HtmlUtil.createHtmlData(zhihuDetailBean.getBody(),zhihuDetailBean.getCss(),zhihuDetailBean.getJs());

        // 加载网页内容
        mWebview.loadData(htmlData, HtmlUtil.MIME_TYPE,HtmlUtil.ENCODING);


        mToolbar.setTitle(zhihuDetailBean.getTitle());
        setSupportActionBar(mToolbar);


    }

    @Override
    public void errorUI(String error) {

    }

    @Override
    protected void initViewActivity() {
        mImgTop = (ImageView) findViewById(R.id.top_img);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mAppbar = (AppBarLayout) findViewById(R.id.appbar);
        mWebview = (WebView) findViewById(R.id.webview);


    }

    @Override
    protected DailyDetailsPresenter initPresenter() {
        return new DailyDetailsPresenter();
    }

    @Override
    protected void initData() {

        String id = getIntent().getIntExtra("id",-1)+"";
        presenter.getZhihuListData(id);
    }

    @Override
    protected int getLayout() {
        return R.layout.daily_details_activity;
    }




}
