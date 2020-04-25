package com.example.jy_7_4_20.service;


import com.example.jy_7_4_20.bean.DailyListBean;
import com.example.jy_7_4_20.bean.SectionListBean;
import com.example.jy_7_4_20.bean.ZhihuDetailBean;

import java.util.HashMap;

import io.reactivex.Flowable;
import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

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
