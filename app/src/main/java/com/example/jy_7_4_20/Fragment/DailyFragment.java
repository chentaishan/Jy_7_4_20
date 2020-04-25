package com.example.jy_7_4_20.Fragment;

import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.example.jy_7_4_20.R;
import com.example.jy_7_4_20.adapter.DailyAdapter;
import com.example.jy_7_4_20.base.BaseMvpFragment;
import com.example.jy_7_4_20.bean.DailyListBean;
import com.example.jy_7_4_20.contract.DailyContract;
import com.example.jy_7_4_20.presenter.DailyPresenter;

public class DailyFragment extends BaseMvpFragment<DailyPresenter> implements DailyContract.View<DailyListBean> {
    private RecyclerView mRecycler;
    private DailyAdapter dailyAdapter;

    @Override
    protected void initData() {

        presenter.getDailyDataList();

    }

    @Override
    protected void initView(View root) {

        super.initView(root);
        mRecycler = (RecyclerView) root.findViewById(R.id.recycler);
        mRecycler.setLayoutManager(new LinearLayoutManager(getActivity()));
        dailyAdapter = new DailyAdapter(getActivity());
        mRecycler.addItemDecoration(new DividerItemDecoration(getActivity(),DividerItemDecoration.VERTICAL));
        mRecycler.setAdapter(dailyAdapter);
    }

    @Override
    protected DailyPresenter initPresenter() {
        return new DailyPresenter();
    }

    @Override
    protected int getLayout() {
        return R.layout.daily_fragment;
    }

    private static final String TAG = "DailyFragment";
    @Override
    public void error(String error) {

        Log.d(TAG, "error: "+error);
    }

    @Override
    public void updateUISuccess(DailyListBean dailyListBean) {

        dailyAdapter.initTopData(dailyListBean.getTop_stories());
        dailyAdapter.initData(dailyListBean.getStories());

    }
}
