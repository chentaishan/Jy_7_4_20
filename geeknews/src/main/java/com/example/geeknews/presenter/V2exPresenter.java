package com.example.geeknews.presenter;

import android.util.Log;

import com.example.geeknews.base.BasePresenter;
import com.example.geeknews.bean.CartBean;
import com.example.geeknews.bean.SectionListBean;
import com.example.geeknews.bean.V2exListBean;
import com.example.geeknews.contract.CartContract;
import com.example.geeknews.contract.V2exContract;
import com.example.geeknews.net.HttpManager;
import com.example.geeknews.service.ApiService;
import com.example.geeknews.utils.Constants;
import com.example.geeknews.utils.RxUtils;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subscribers.ResourceSubscriber;
import okhttp3.ResponseBody;

/**
 * 购物车 的P 层实现类
 */
public class V2exPresenter extends BasePresenter<V2exContract.View> implements V2exContract.Presenter {


    private String parseId(String str) {
        int idEnd = str.indexOf("#");
        return str.substring(3, idEnd);
    }

    private String parseTime(String str) {
        int timeEnd = str.indexOf("  •");
        if (timeEnd == -1) {
            return str;
        }
        return str.substring(0, timeEnd);
    }

    public static String parseImg(String str) {
        return "http:" + str;
    }

    private static final String TAG = "V2exPresenter";


    @Override
    public void getV2exListData(String type) {

        //首先加载网页

        //https://www.v2ex.com/?tab=creative
        Flowable.just(ApiService.TAB_HOST + type)
                .subscribeOn(Schedulers.io())
                .map(new Function<String, Document>() {
                    @Override
                    public Document apply(String s) throws Exception {
                        return Jsoup.connect(s).get();
                    }
                })
                .map(new Function<Document, List<V2exListBean>>() {
                    @Override
                    public List<V2exListBean> apply(Document document) throws Exception {
                        // 把Doucment 数据 转成 listbean
                        List<V2exListBean> listBeans = new ArrayList<>();

                        Elements elements = document.select("div.cell.item");
                        for (int i = 0; i < elements.size(); i++) {
                            Element element = elements.get(i);
                            String imgUrl= element.select("table tr td a img.avatar").get(0).attr("src");
                            String title= element.select("table tr td span.item_title  > a").text();

                            V2exListBean v2exListBean = new V2exListBean();

                            v2exListBean.setTitle(title);
                            v2exListBean.setImgUrl(imgUrl);
                            Log.d(TAG, "apply: "+imgUrl+"----title="+title);

                            listBeans.add(v2exListBean);
                        }

                        return listBeans;
                    }
                }).observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new ResourceSubscriber<List<V2exListBean>>() {
                    @Override
                    public void onNext(List<V2exListBean> v2exListBeanList) {

                        view.successUI(v2exListBeanList);
                    }

                    @Override
                    public void onError(Throwable t) {

                        view.errorUI(t.getMessage());
                    }

                    @Override
                    public void onComplete() {

                    }
                });


    }
}
