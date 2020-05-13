package com.example.geeknews.contract;

import com.example.geeknews.base.IBasePresenter;
import com.example.geeknews.base.IBaseView;
import com.example.geeknews.bean.CartBean;
import com.example.geeknews.bean.GirlBean;

/**
 * 购物车   契约类
 */
public interface GirlContract {
    interface View extends IBaseView{
        void successUI(GirlBean girlBean);

    }

    interface Presenter extends IBasePresenter<View>{

        void getGirlListData();
    }

}
