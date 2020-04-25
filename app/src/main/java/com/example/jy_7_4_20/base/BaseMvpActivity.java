package com.example.jy_7_4_20.base;

public abstract class BaseMvpActivity<P extends IBasePresenter> extends BaseActivity {

    protected P presenter;
    @Override
    protected void initView() {

        presenter = initPresenter();
        if (presenter!=null){
            presenter.attachView(this);
        }
    }

    protected abstract P initPresenter();

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (presenter!=null){
            presenter.detachView();
        }
    }
}
