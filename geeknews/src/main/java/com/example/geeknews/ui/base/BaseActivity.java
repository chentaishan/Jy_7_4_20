package com.example.geeknews.ui.base;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.geeknews.R;

import butterknife.ButterKnife;

public abstract class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayout());

        ButterKnife.bind(this);

        initView();
        initData();
    }

    protected  void initView(){

    }

    protected abstract void initData();

    protected abstract int getLayout();
}
