package com.example.geeknews.base;

import java.lang.ref.WeakReference;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

public abstract class BasePresenter<V> implements IBasePresenter<V> {
    public V view;
    private WeakReference<V> vWeakReference;
    private CompositeDisposable compositeDisposable;

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
        if (compositeDisposable!=null){
//            取消所有连接
            compositeDisposable.dispose();
        }
    }


    /**
     * 由CompositeDisposable 托管，如果需要取消请求，可以调用 compositeDisposable.dispose();
     * @param disposable
     */
    protected void addSubscribe(Disposable disposable) {
        if (compositeDisposable == null) {
            compositeDisposable = new CompositeDisposable();
        }
        compositeDisposable.add(disposable);
    }



}
