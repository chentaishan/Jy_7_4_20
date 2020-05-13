package com.example.geeknews.contract;

import com.example.geeknews.base.IBasePresenter;
import com.example.geeknews.base.IBaseView;
import com.example.geeknews.bean.CartBean;
import com.example.geeknews.bean.GankBean;

/**
 * 购物车   契约类
 */
public interface GankSubContract {
    interface View extends IBaseView{
        void successUI(GankBean gankBean);

    }

    interface Presenter extends IBasePresenter<View>{

        void getGankListData();
    }

}
