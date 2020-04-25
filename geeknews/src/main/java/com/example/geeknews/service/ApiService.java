package com.example.geeknews.service;

import android.database.Observable;

import com.example.geeknews.bean.DailyListBean;
import com.example.geeknews.bean.SectionListBean;
import com.example.geeknews.bean.ZhihuDetailBean;

import io.reactivex.Flowable;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ApiService {

//    http://news-at.zhihu.com/api/4/news/latest

    String zhihuUrl ="http://news-at.zhihu.com/";

    @GET("api/4/news/latest")
    Flowable<DailyListBean> getLatestList();

    /**
     * 专栏日报
     */
    @GET("api/4/sections")
    Observable<SectionListBean> getSectionList();

    /**
     * 日报详情
     * http://news-at.zhihu.com/api/4/news/9713242
     */
    @GET("api/4/news/{id}")
    Observable<ZhihuDetailBean> getDetailInfo(@Path("id") int id);


}
