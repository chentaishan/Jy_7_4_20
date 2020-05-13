package com.example.geeknews.ui.fragment;

import android.support.design.widget.AppBarLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.geeknews.R;
import com.example.geeknews.adapter.GankSubAdapter;
import com.example.geeknews.bean.GankBean;
import com.example.geeknews.contract.GankSubContract;
import com.example.geeknews.presenter.GankPresenter;
import com.example.geeknews.ui.base.BaseMvpFragment;
import com.example.geeknews.utils.ScreenUtils;

public class GankSubFragment extends BaseMvpFragment<GankPresenter> implements GankSubContract.View {
    private ImageView mImg;
    private ImageView mBlurImg;
    private AppBarLayout mAppbar;
    private RecyclerView mRecycler;
    private GankSubAdapter gankSubAdapter;

    @Override
    public void successUI(GankBean gankBean) {
        gankSubAdapter.addDataList(gankBean.getResults());
    }

    @Override
    public void errorUI(String error) {

    }

    private static final String TAG = "GankSubFragment";
    @Override
    protected void initViewFragment(View itemView) {
        mImg = (ImageView) itemView.findViewById(R.id.img);
        mBlurImg = (ImageView) itemView.findViewById(R.id.img_blur);

        mAppbar = (AppBarLayout) itemView.findViewById(R.id.appbar);
        mRecycler = (RecyclerView) itemView.findViewById(R.id.recycler);
        mRecycler.setLayoutManager(new LinearLayoutManager(getActivity()));
        gankSubAdapter = new GankSubAdapter(getActivity());
        mRecycler.setAdapter(gankSubAdapter);

        Glide.with(getActivity()).load("http://ww1.sinaimg.cn/large/0065oQSqly1fsfq1ykabxj30k00pracv.jpg").into(mImg);
        //设置完全透明
        mBlurImg.setAlpha(0f);

        // 偏移的监听器
        mAppbar.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int offset) {

                Log.d(TAG, "onOffsetChanged: "+offset);
                int size = ScreenUtils.dip2px(getActivity(),256f);

                float percent = (float)Math.abs(offset)/size;

                Log.d(TAG, "onOffsetChanged: "+percent);

                mBlurImg.setAlpha(percent);
            }
        });

    }

    @Override
    protected GankPresenter initPresent() {
        return new GankPresenter();
    }

    @Override
    protected void initData() {

        presenter.getGankListData();
    }

    @Override
    protected int getLayout() {
        return R.layout.gank_sub_fragment;
    }

}
