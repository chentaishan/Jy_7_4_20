package com.example.jy_7_4_20.contract;

import com.example.jy_7_4_20.base.IBasePresenter;
import com.example.jy_7_4_20.base.IBaseView;

public interface DailyContract {

    interface View<T> extends IBaseView {
        void updateUISuccess(T t);
    }

    interface Presenter extends IBasePresenter<View> {
        void getDailyDataList();
    }
}
