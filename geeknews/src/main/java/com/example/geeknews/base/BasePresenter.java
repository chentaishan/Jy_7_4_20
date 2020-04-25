package com.example.geeknews.base;

import java.lang.ref.WeakReference;

public abstract class BasePresenter<V> implements IBasePresenter<V> {
    public V view;
    private WeakReference<V> vWeakReference;

    @Override
    public void attachView(V view) {
        vWeakReference = new WeakReference<>(view);
        this.view= vWeakReference.get();


    }

    @Override
    public void detachView() {
        if (view!=null){
            view=null;
        }
    }
}
