package com.example.geeknews.contract;

import com.example.geeknews.base.IBasePresenter;
import com.example.geeknews.base.IBaseView;
import com.example.geeknews.bean.CartBean;
import com.example.geeknews.bean.ZhihuDetailBean;

/**
 * 购物车   契约类
 */
public interface ZhihuDetailsContract {
    interface View extends IBaseView{
        void successUI(ZhihuDetailBean zhihuDetailBean);

    }

    interface Presenter extends IBasePresenter<View>{

        void getZhihuListData(String id);
    }

}
