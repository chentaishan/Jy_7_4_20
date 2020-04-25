package com.example.geeknews.presenter;

import android.util.Log;

import com.example.geeknews.base.BasePresenter;
import com.example.geeknews.bean.DailyListBean;
import com.example.geeknews.contract.DailyContract;
import com.example.geeknews.net.HttpManager;
import com.example.geeknews.service.ApiService;

import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class DailyPresenter extends BasePresenter<DailyContract.View> implements DailyContract.Presenter {

    private static final String TAG = "DailyPresenter";
    @Override
    public void getDailyListData() {

        ApiService apiService = HttpManager.getInstance().getApiService(ApiService.zhihuUrl, ApiService.class);

        Flowable<DailyListBean> latestList = apiService.getLatestList();
        latestList.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<DailyListBean>() {
                    @Override
                    public void accept(DailyListBean dailyListBean) throws Exception {

                        view.successUI(dailyListBean);
                        Log.d(TAG, "accept: "+dailyListBean.toString());
                    }
                });

    }


}
