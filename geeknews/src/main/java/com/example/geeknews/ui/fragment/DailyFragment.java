package com.example.geeknews.ui.fragment;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.example.geeknews.DailyAdapter;
import com.example.geeknews.R;
import com.example.geeknews.bean.DailyListBean;
import com.example.geeknews.bean.User;
import com.example.geeknews.contract.DailyContract;
import com.example.geeknews.presenter.DailyPresenter;
import com.example.geeknews.ui.base.BaseFragment;
import com.example.geeknews.ui.base.BaseMvpFragment;

public class DailyFragment extends BaseMvpFragment<DailyPresenter> implements DailyContract.View<DailyListBean> {

    private static final String TAG = "DailyFragment";
    private RecyclerView recycler;
    private DailyAdapter dailyAdapter;

    @Override
    public void successUI(DailyListBean dailyListBean) {

        Log.e(TAG, "successUI: "+dailyListBean.toString());
        dailyAdapter.setBanner(dailyListBean.getTop_stories());
        dailyAdapter.setStoryList(dailyListBean.getStories());


    }

    @Override
    protected void initView(View root) {
        super.initView(root);
        recycler = root.findViewById(R.id.recycler);
        recycler.setLayoutManager(new LinearLayoutManager(getActivity()));
        dailyAdapter = new DailyAdapter(getActivity());
        recycler.setAdapter(dailyAdapter);

    }

    @Override
    public void errorUI(String error) {

    }

    @Override
    protected DailyPresenter initPresent() {
        return new DailyPresenter();
    }

    @Override
    protected void initData() {

        presenter.getDailyListData();
    }

    @Override
    protected int getLayout() {
        return R.layout.daily_fragment;
    }
}
