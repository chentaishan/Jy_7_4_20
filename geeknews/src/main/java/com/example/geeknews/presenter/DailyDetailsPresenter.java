package com.example.geeknews.presenter;

import com.example.geeknews.base.BasePresenter;
import com.example.geeknews.bean.CartBean;
import com.example.geeknews.bean.ZhihuDetailBean;
import com.example.geeknews.contract.CartContract;
import com.example.geeknews.contract.ZhihuDetailsContract;
import com.example.geeknews.net.HttpManager;
import com.example.geeknews.service.ApiService;
import com.example.geeknews.utils.RxUtils;

import io.reactivex.subscribers.ResourceSubscriber;

/**
 *
 */
public class DailyDetailsPresenter extends BasePresenter<ZhihuDetailsContract.View> implements ZhihuDetailsContract.Presenter {
    @Override
    public void getZhihuListData(String id) {

        ResourceSubscriber<ZhihuDetailBean> resourceSubscriber = HttpManager.getInstance().getApiService(ApiService.zhihuUrl, ApiService.class)
                .getDetailInfo(Integer.valueOf(id))
                .compose(RxUtils.<ZhihuDetailBean>rxScheduler())
                .subscribeWith(new ResourceSubscriber<ZhihuDetailBean>() {

                    @Override
                    public void onNext(ZhihuDetailBean o) {

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
