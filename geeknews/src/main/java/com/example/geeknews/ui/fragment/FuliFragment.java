package com.example.geeknews.ui.fragment;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.View;

import com.example.geeknews.R;
import com.example.geeknews.adapter.GirlAdapter;
import com.example.geeknews.bean.GirlBean;
import com.example.geeknews.contract.GirlContract;
import com.example.geeknews.presenter.GirlPresenter;
import com.example.geeknews.ui.base.BaseMvpFragment;

public class FuliFragment extends BaseMvpFragment<GirlPresenter> implements GirlContract.View {

    private RecyclerView mRecyclerview;
    private GirlAdapter girlAdapter;

    @Override
    protected void initData() {

        // 调用网络请求
        presenter.getGirlListData();
    }


    @Override
    protected void initViewFragment(View root) {
        mRecyclerview = (RecyclerView) root.findViewById(R.id.recyclerview);
        mRecyclerview.setLayoutManager(new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL));

        girlAdapter = new GirlAdapter(getActivity());
        mRecyclerview.setAdapter(girlAdapter);
    }

    @Override
    protected GirlPresenter initPresent() {

        return new GirlPresenter();
    }

    @Override
    protected int getLayout() {
        return R.layout.fuli_fragment;
    }

    @Override
    public void successUI(GirlBean girlBean) {

        girlAdapter.addDataList(girlBean.getResults());
    }

    @Override
    public void errorUI(String error) {

        Log.d(TAG, "errorUI: ");
    }

    private static final String TAG = "FuliFragment";
}
