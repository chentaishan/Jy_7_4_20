package com.example.geeknews.contract;

import com.example.geeknews.base.IBasePresenter;
import com.example.geeknews.base.IBaseView;
import com.example.geeknews.bean.SectionListBean;

/**
 * 专栏
 */
public interface TopicContract {
    interface View extends IBaseView{
        void successUI(SectionListBean sectionListBean);

    }

    interface Presenter extends IBasePresenter<View>{

        void getSectionListData();
    }

}
