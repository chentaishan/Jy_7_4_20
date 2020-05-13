package com.example.geeknews.presenter;

import com.example.geeknews.utils.RxUtils;
import com.example.geeknews.base.BasePresenter;
import com.example.geeknews.bean.DailyListBean;
import com.example.geeknews.contract.DailyContract;
import com.example.geeknews.net.HttpManager;
import com.example.geeknews.service.ApiService;

import io.reactivex.subscribers.ResourceSubscriber;

public class DailyPresenter extends BasePresenter<DailyContract.View> implements DailyContract.Presenter {

    private static final String TAG = "DailyPresenter";
//    C:\Users\chen\.android/debug.keystore

    @Override
    public void getDailyListData() {

        ResourceSubscriber<DailyListBean> resourceSubscriber = HttpManager.getInstance().getApiService(ApiService.zhihuUrl, ApiService.class)
                .getLatestList()
                .compose(RxUtils.<DailyListBean>rxScheduler())
                .subscribeWith(new ResourceSubscriber<DailyListBean>() {

                    @Override
                    public void onNext(DailyListBean o) {

                        view.successUI(o);
                    }

                    @Override
                    public void onError(Throwable t) {

                        view.errorUI(t.getMessage());
                    }

                    @Override
                    public void onComplete() {

                    }
                });

        addSubscribe(resourceSubscriber);

    }

    @Override
    public void getBeforeListData(String date) {
        ResourceSubscriber<DailyListBean> resourceSubscriber = HttpManager.getInstance().getApiService(ApiService.zhihuUrl, ApiService.class)
                .getBeforeListData(date)
                .compose(RxUtils.<DailyListBean>rxScheduler())
                .subscribeWith(new ResourceSubscriber<DailyListBean>() {

                    @Override
                    public void onNext(DailyListBean o) {

                        view.beforeSuccessUI(o);
                    }

                    @Override
                    public void onError(Throwable t) {

                        view.errorUI(t.getMessage());
                    }

                    @Override
                    public void onComplete() {

                    }
                });

        addSubscribe(resourceSubscriber);
    }


}
