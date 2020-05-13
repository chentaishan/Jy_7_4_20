package com.example.geeknews.presenter;

import com.example.geeknews.base.BasePresenter;
import com.example.geeknews.base.IBasePresenter;
import com.example.geeknews.bean.CartBean;
import com.example.geeknews.bean.GankBean;
import com.example.geeknews.contract.GankSubContract;
import com.example.geeknews.net.HttpManager;
import com.example.geeknews.service.ApiService;
import com.example.geeknews.utils.RxUtils;

import io.reactivex.subscribers.ResourceSubscriber;

/**
 * 获取干活集中营的数据
 */
public class GankPresenter extends BasePresenter<GankSubContract.View> implements GankSubContract.Presenter {

    @Override
    public void getGankListData() {
        ResourceSubscriber<GankBean> resourceSubscriber = HttpManager.getInstance().getApiService(ApiService.gankUrl, ApiService.class)
                .getGankListBean("Android","20")
                .compose(RxUtils.<GankBean>rxScheduler())
                .subscribeWith(new ResourceSubscriber<GankBean>() {
                    @Override
                    public void onNext(GankBean o) {

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
