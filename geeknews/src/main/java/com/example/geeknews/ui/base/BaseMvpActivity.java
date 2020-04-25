package com.example.geeknews.ui.base;

import com.example.geeknews.base.BasePresenter;

/**
 * 此基类 主要处理MVP的逻辑
 */
public abstract class BaseMvpActivity<P extends BasePresenter> extends BaseActivity {

    protected P presenter;

    @Override
    protected void initView() {
        super.initView();

        presenter =initPresenter();
        if (presenter!=null){
            presenter.attachView(this);
        }
    }

    protected abstract P initPresenter();

    @Override
    protected void onDestroy() {
        super.onDestroy();

        // 解绑view,避免内存泄漏
        if (presenter!=null){
            presenter.detachView();
        }
    }
}
