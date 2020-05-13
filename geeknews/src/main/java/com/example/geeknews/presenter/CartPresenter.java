package com.example.geeknews.presenter;

import com.example.geeknews.base.BasePresenter;
import com.example.geeknews.bean.CartBean;
import com.example.geeknews.bean.SectionListBean;
import com.example.geeknews.contract.CartContract;
import com.example.geeknews.contract.TopicContract;
import com.example.geeknews.net.HttpManager;
import com.example.geeknews.service.ApiService;
import com.example.geeknews.utils.RxUtils;

import io.reactivex.subscribers.ResourceSubscriber;

/**
 * 购物车 的P 层实现类
 */
public class CartPresenter extends BasePresenter<CartContract.View> implements CartContract.Presenter {
    @Override
    public void getCartListData() {

        ResourceSubscriber<CartBean> resourceSubscriber = HttpManager.getInstance().getApiService(ApiService.cartUrl, ApiService.class)
                .getCartListBean()
                .compose(RxUtils.<CartBean>rxScheduler())
                .subscribeWith(new ResourceSubscriber<CartBean>() {

                    @Override
                    public void onNext(CartBean o) {

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
