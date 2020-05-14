package com.example.geeknews.ui.fragment;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.example.geeknews.R;
import com.example.geeknews.adapter.TopicAdapter;
import com.example.geeknews.adapter.V2exAdapter;
import com.example.geeknews.bean.V2exListBean;
import com.example.geeknews.contract.V2exContract;
import com.example.geeknews.presenter.V2exPresenter;
import com.example.geeknews.ui.base.BaseMvpFragment;
import com.example.geeknews.utils.Constants;

import java.util.List;

public class VtexPagerFragment extends BaseMvpFragment<V2exPresenter> implements V2exContract.View {
    private RecyclerView mMainView;
    private SwipeRefreshLayout mRefreshSwipe;
    private V2exAdapter mAdapter;

    @Override
    public void successUI(List<V2exListBean> v2exListBeanList) {
        mAdapter.updateData(v2exListBeanList);
    }

    @Override
    public void errorUI(String error) {

    }

    @Override
    protected void initViewFragment(View itemView) {
        mMainView = (RecyclerView) itemView.findViewById(R.id.view_main);
        mRefreshSwipe = (SwipeRefreshLayout) itemView.findViewById(R.id.swipe_refresh);
        mAdapter = new V2exAdapter(getActivity() );
        mMainView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mMainView.setAdapter(mAdapter);
    }

    @Override
    protected V2exPresenter initPresent() {
        return new V2exPresenter();
    }

    @Override
    protected void initData() {

        String type = getArguments().getString(Constants.IT_VTEX_TYPE);
        presenter.getV2exListData(type);
    }

    @Override
    protected int getLayout() {
        return R.layout.view_common_list;
    }


}
