package com.example.geeknews.contract;

import com.example.geeknews.base.IBasePresenter;
import com.example.geeknews.base.IBaseView;

/**
 * 日报 业务的契约接口
 */
public interface DailyContract {

      interface View<T> extends IBaseView{

        void successUI(T t);

        void beforeSuccessUI(T t);

    }

      interface Presenter extends IBasePresenter<View> {

        void getDailyListData();
        void  getBeforeListData(String date);
    }
}
