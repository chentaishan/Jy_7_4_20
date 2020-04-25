package com.example.geeknews.ui.base;

import android.view.View;

import com.example.geeknews.base.BasePresenter;

/**
 * 基于Presenter的引用
 * view的绑定和解绑
 * context 的应用
 */
public abstract class BaseMvpFragment<P extends BasePresenter> extends BaseFragment {

    protected P presenter;
    @Override
    protected void initView(View root) {
        presenter= initPresent();
        if (presenter!=null){
            presenter.attachView(this);
        }
    }

    protected abstract P initPresent();

    @Override
    public void onDestroyView() {
        super.onDestroyView();

        if (presenter!=null){
            presenter.detachView();
        }
    }
}
