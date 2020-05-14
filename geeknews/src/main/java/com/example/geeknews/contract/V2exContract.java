package com.example.geeknews.contract;

import com.example.geeknews.base.IBasePresenter;
import com.example.geeknews.base.IBaseView;
import com.example.geeknews.bean.CartBean;
import com.example.geeknews.bean.V2exListBean;

import java.util.List;

/**
 * 购物车   契约类
 */
public interface V2exContract {
    interface View extends IBaseView{
        void successUI(List<V2exListBean> v2exListBeanList);

    }

    interface Presenter extends IBasePresenter<View>{

        void getV2exListData(String type);
    }

}
