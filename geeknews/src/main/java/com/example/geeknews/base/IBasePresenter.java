package com.example.geeknews.base;

public interface IBasePresenter<V> {
    void attachView(V view);
    void detachView();
}
