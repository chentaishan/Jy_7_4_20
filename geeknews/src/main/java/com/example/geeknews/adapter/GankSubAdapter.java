package com.example.geeknews.adapter;

import android.content.Context;
import android.widget.TextView;

import com.example.geeknews.R;
import com.example.geeknews.bean.GankBean;

public class GankSubAdapter extends BaseAdapter<GankBean.ResultsBean> {

    public GankSubAdapter(Context context) {
        super(context);
    }

    @Override
    protected int getItemLayout() {
        return R.layout.gank_sub_item;
    }

    @Override
    protected void bindData(BaseViewHolder baseViewHolder, GankBean.ResultsBean resultsBean) {

        TextView name = (TextView) baseViewHolder.getViewById(R.id.name);

        name.setText(resultsBean.getDesc());
    }
}
