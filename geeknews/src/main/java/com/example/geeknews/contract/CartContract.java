package com.example.geeknews.contract;

import com.example.geeknews.base.IBasePresenter;
import com.example.geeknews.base.IBaseView;
import com.example.geeknews.bean.CartBean;
import com.example.geeknews.bean.SectionListBean;

/**
 * 购物车   契约类
 */
public interface CartContract {
    interface View extends IBaseView{
        void successUI(CartBean cartBean);

    }

    interface Presenter extends IBasePresenter<View>{

        void getCartListData();
    }

}
