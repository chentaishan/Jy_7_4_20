package com.example.geeknews.service;

import android.database.Observable;

import com.example.geeknews.bean.CartBean;
import com.example.geeknews.bean.DailyListBean;
import com.example.geeknews.bean.GankBean;
import com.example.geeknews.bean.GirlBean;
import com.example.geeknews.bean.SectionListBean;
import com.example.geeknews.bean.ZhihuDetailBean;

import io.reactivex.Flowable;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ApiService {

//    http://news-at.zhihu.com/api/4/sections

    String zhihuUrl ="http://news-at.zhihu.com/";
    String gankUrl ="https://gank.io/";

    @GET("api/4/news/latest")
    Flowable<DailyListBean> getLatestList();


    /**
     * 获取以往数据
     * @param date
     * @return
     */
    @GET("api/4/news/before/{date}")
    Flowable<DailyListBean> getBeforeListData(@Path("date") String date);
    /**
     * 专栏日报
     */
    @GET("api/4/sections")
    Flowable<SectionListBean> getSectionList();

    /**
     * 日报详情
     * http://news-at.zhihu.com/api/4/news/9713242
     */
    @GET("api/4/news/{id}")
    Flowable<ZhihuDetailBean> getDetailInfo(@Path("id") int id);


    String cartUrl=" http://cdwan.cn/";

//    http://cdwan.cn/api/cart/index
    @GET("api/cart/index")
    Flowable<CartBean> getCartListBean();


//    https://gank.io/api/data/Android/10/1

    /**
     *
     * @param type  类型  如：Android Ios
     * @param num  每页的总数
     * @return
     */
    @GET("api/data/{type}/{num}/10")
    Flowable<GankBean> getGankListBean(@Path("type") String type,@Path("num") String num);


    /**
     * https://gank.io/api/data/%E7%A6%8F%E5%88%A9/20/3

     */
    @GET("api/data/%E7%A6%8F%E5%88%A9/20/3")
    Flowable<GirlBean> getGirlsData();


}
