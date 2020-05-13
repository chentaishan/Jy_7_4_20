package com.example.geeknews.presenter;

import com.example.geeknews.base.BasePresenter;
import com.example.geeknews.bean.CartBean;
import com.example.geeknews.bean.GirlBean;
import com.example.geeknews.contract.CartContract;
import com.example.geeknews.contract.GirlContract;
import com.example.geeknews.net.HttpManager;
import com.example.geeknews.service.ApiService;
import com.example.geeknews.utils.RxUtils;

import io.reactivex.subscribers.ResourceSubscriber;

/**
 *  福利  美女 Pre
 */
public class GirlPresenter extends BasePresenter<GirlContract.View> implements GirlContract.Presenter {
    @Override
    public void getGirlListData() {

        ResourceSubscriber<GirlBean> resourceSubscriber = HttpManager.getInstance().getApiService(ApiService.gankUrl, ApiService.class)
                .getGirlsData()
                .compose(RxUtils.<GirlBean>rxScheduler())
                .subscribeWith(new ResourceSubscriber<GirlBean>() {

                    @Override
                    public void onNext(GirlBean o) {

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
