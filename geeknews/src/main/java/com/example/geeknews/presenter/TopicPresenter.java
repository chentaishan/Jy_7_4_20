package com.example.geeknews.presenter;

import com.example.geeknews.base.BasePresenter;
import com.example.geeknews.bean.DailyListBean;
import com.example.geeknews.bean.SectionListBean;
import com.example.geeknews.contract.TopicContract;
import com.example.geeknews.net.HttpManager;
import com.example.geeknews.service.ApiService;
import com.example.geeknews.utils.RxUtils;

import io.reactivex.subscribers.ResourceSubscriber;

public class TopicPresenter extends BasePresenter<TopicContract.View> implements TopicContract.Presenter {
    @Override
    public void getSectionListData() {

        ResourceSubscriber<SectionListBean> resourceSubscriber = HttpManager.getInstance().getApiService(ApiService.zhihuUrl, ApiService.class)
                .getSectionList()
                .compose(RxUtils.<SectionListBean>rxScheduler())
                .subscribeWith(new ResourceSubscriber<SectionListBean>() {

                    @Override
                    public void onNext(SectionListBean o) {

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
}
