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
    @Override
    public void getV2exListData(String type) {
        Flowable.just(ApiService.TAB_HOST+type).subscribeOn(Schedulers.io())
                .map(new Function<String, Document>() {
                    @Override
                    public Document apply(String s) throws Exception {
                        
                        return Jsoup.connect(s).get();
                    }
                })
                .map(new Function<Document, List<V2exListBean>>() {
                    @Override
                    public List<V2exListBean> apply(Document doc) throws Exception {
                        List<V2exListBean> mList = new ArrayList<>();
                        Elements itemElements = doc.select("div.cell.item");    //item根节点
                        int count = itemElements.size();
                        for (int i = 0; i < count; i++) {
                            Elements titleElements = itemElements.get(i).select("div.cell.item table tr td span.item_title > a");   //标题
                            Elements imgElements = itemElements.get(i).select("div.cell.item table tr td img.avatar");              //头像
                            Elements commentElements = itemElements.get(i).select("div.cell.item table tr a.count_livid");          //评论数


                            Elements nodeElements = itemElements.get(i).select("div.cell.item table tr span.small.fade a.node");    //节点
                            Elements nameElements = itemElements.get(i).select("div.cell.item table tr span.small.fade strong a");  //作者 & 最后回复
                            Elements timeElements = itemElements.get(i).select("div.cell.item table tr span.small.fade");           //更新时间

                            V2exListBean bean = new V2exListBean();


                            if (titleElements.size() > 0) {
                                bean.setTitle(titleElements.get(0).text());
                                bean.setTopicId(parseId(titleElements.get(0).attr("href")));
                            }
                            if (imgElements.size() > 0) {
                                bean.setImgUrl(parseImg(imgElements.get(0).attr("src"))); // http:
                            }
                            if (nodeElements.size() > 0) {
                                bean.setNode(nodeElements.get(0).text());
                            }
                            if (nameElements.size() > 0) {
                                bean.setName(nameElements.get(0).text());
                            }
                            //存在没有 最后回复者、评论数、更新时间的情况
                            if (nameElements.size() > 1) {
                                bean.setLastUser(nameElements.get(1).text());
                            }
                            if (commentElements.size() > 0) {
                                bean.setCommentNum(Integer.valueOf(commentElements.get(0).text()));
                            }
                            if (timeElements.size() > 1) {
                                bean.setUpdateTime(parseTime(timeElements.get(1).text()));
                            }

                            mList.add(bean);
                        }
                        return mList;
                    }
                }). observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new ResourceSubscriber<List<V2exListBean>>() {

                    @Override
                    public void onNext(List<V2exListBean> o) {

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
    }
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


}
