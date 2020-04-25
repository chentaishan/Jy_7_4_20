package com.example.jy_7_4_20.presenter;

import com.example.jy_7_4_20.base.BasePresenter;
import com.example.jy_7_4_20.bean.DailyListBean;
import com.example.jy_7_4_20.contract.DailyContract;
import com.example.jy_7_4_20.net.HttpManager;
import com.example.jy_7_4_20.service.ApiService;
import com.example.jy_7_4_20.utils.RxUtils;

import io.reactivex.subscribers.ResourceSubscriber;

public class DailyPresenter extends BasePresenter<DailyContract.View> implements DailyContract.Presenter {


    @Override
    public void getDailyDataList() {
        ResourceSubscriber<DailyListBean> resourceSubscriber = HttpManager.getInstance().getApiService(ApiService.zhihuUrl, ApiService.class)

                .getLatestList()
                .compose(RxUtils.<DailyListBean>rxScheduler())
                .subscribeWith(new ResourceSubscriber<DailyListBean>() {
                    @Override
                    public void onNext(DailyListBean dailyListBean) {

                        view.updateUISuccess(dailyListBean);
                    }


                    @Override
                    public void onError(Throwable t) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
        addSubscribe(resourceSubscriber);


    }
}
