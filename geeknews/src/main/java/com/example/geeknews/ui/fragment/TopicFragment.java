package com.example.geeknews.ui.fragment;

import android.support.design.widget.Snackbar;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.example.geeknews.R;
import com.example.geeknews.adapter.TopicAdapter;
import com.example.geeknews.bean.SectionListBean;
import com.example.geeknews.contract.TopicContract;
import com.example.geeknews.presenter.TopicPresenter;
import com.example.geeknews.ui.base.BaseFragment;
import com.example.geeknews.ui.base.BaseMvpFragment;

/**
 * 专栏
 */
public class TopicFragment extends BaseMvpFragment<TopicPresenter> implements TopicContract.View {
    private RecyclerView mRecycler;
    private TopicAdapter topicAdapter;

    @Override
    protected void initData() {

        presenter.getSectionListData();
    }

    @Override
    protected void initViewFragment(View root) {


        mRecycler = (RecyclerView) root.findViewById(R.id.recycler);
        mRecycler.setLayoutManager(new GridLayoutManager(getActivity(),2));

        topicAdapter = new TopicAdapter(getActivity());
        mRecycler.setAdapter(topicAdapter);
    }

    @Override
    protected TopicPresenter initPresent() {
        return new TopicPresenter();
    }

    @Override
    protected int getLayout() {
        return R.layout.topic_fragment;
    }

    @Override
    public void successUI(SectionListBean sectionListBean) {
        topicAdapter.addDataList(sectionListBean.getData());



    }

    private static final String TAG = "TopicFragment";
    @Override
    public void errorUI(String error) {

        Log.d(TAG, "errorUI: "+error);
    }
}
