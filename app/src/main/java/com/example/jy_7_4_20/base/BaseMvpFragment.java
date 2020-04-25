package com.example.jy_7_4_20.base;

import android.view.View;

public abstract class BaseMvpFragment<P extends IBasePresenter> extends BaseFragment {

   protected P presenter;

    @Override
    protected void initView(View root) {

        presenter= initPresenter();
        if (presenter!=null){

            presenter.attachView(this);
        }
    }

    protected abstract P initPresenter();


    @Override
    public void onDestroy() {
        super.onDestroy();
        if (presenter!=null){

            presenter.detachView();
        }
    }
}
