package com.example.jy_7_4_20.base;

public interface IBasePresenter<V> {

    void attachView(V baseView);
    void detachView();
}
