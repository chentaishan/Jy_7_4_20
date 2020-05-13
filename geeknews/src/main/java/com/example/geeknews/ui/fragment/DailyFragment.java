package com.example.geeknews.ui.fragment;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.EventLog;
import android.util.Log;
import android.view.View;

import com.example.geeknews.CalendarEvent;
import com.example.geeknews.DailyAdapter;
import com.example.geeknews.R;
import com.example.geeknews.bean.DailyListBean;
import com.example.geeknews.contract.DailyContract;
import com.example.geeknews.presenter.DailyPresenter;
import com.example.geeknews.ui.CalendarActivity;
import com.example.geeknews.ui.base.BaseMvpFragment;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.Calendar;

public class DailyFragment extends BaseMvpFragment<DailyPresenter> implements DailyContract.View<DailyListBean>, View.OnClickListener {

    private static final String TAG = "DailyFragment";
    private RecyclerView recycler;
    private DailyAdapter dailyAdapter;
    private FloatingActionButton mCalenderFab;
    private String date;


    @Override
    public void successUI(DailyListBean dailyListBean) {

        Log.e(TAG, "successUI: " + dailyListBean.toString());
        dailyAdapter.setBanner(dailyListBean.getTop_stories());
        dailyAdapter.setStoryList(dailyListBean.getStories(),"今日新闻");


    }

    @Override
    public void beforeSuccessUI(DailyListBean dailyListBean) {
        dailyAdapter.setBeforeStoryList(dailyListBean.getStories(),dailyListBean.getDate());

    }

    @Override
    protected void initViewFragment(View root) {

        recycler = root.findViewById(R.id.recycler);
        recycler.setLayoutManager(new LinearLayoutManager(getActivity()));
        dailyAdapter = new DailyAdapter(getActivity());
        recycler.setAdapter(dailyAdapter);

        mCalenderFab = (FloatingActionButton) root.findViewById(R.id.fab_calender);
        mCalenderFab.setOnClickListener(this);


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

        //注册eventbus 方便接收日历数据
        EventBus.getDefault().register(this);
        presenter.getDailyListData();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void receiverDate(CalendarEvent event){
        date = event.date;

        Log.d(TAG, "receiverDate: "+ date);

        presenter.getBeforeListData(date);
    }

    @Override
    protected int getLayout() {
        return R.layout.daily_fragment;
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fab_calender:
                // TODO 20/05/11 跳转到日历界面
                startActivity(new Intent(getActivity(), CalendarActivity.class));

                break;
            default:
                break;
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

        EventBus.getDefault().unregister(this);
    }
}
