package com.example.jy_7_4_20.base;

import org.reactivestreams.Publisher;

import java.lang.ref.WeakReference;

import io.reactivex.Flowable;
import io.reactivex.FlowableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public abstract class BasePresenter<V extends IBaseView> implements IBasePresenter<V> {
    protected V view;
    private WeakReference<V> vWeakReference;
    private CompositeDisposable compositeDisposable;

    @Override
    public void attachView(V baseView) {
        vWeakReference = new WeakReference<>(baseView);
        view = vWeakReference.get();
    }

    @Override
    public void detachView() {
        if (view!=null){
            view =null;
        }

        if (compositeDisposable == null) {
            compositeDisposable.dispose();
        }
    }



    /**
     * 添加背压机制
     * @param disposable
     */
    public void addSubscribe(Disposable disposable) {

        if (compositeDisposable == null) {

            compositeDisposable = new CompositeDisposable();
        }
        compositeDisposable.add(disposable);

    }
}
