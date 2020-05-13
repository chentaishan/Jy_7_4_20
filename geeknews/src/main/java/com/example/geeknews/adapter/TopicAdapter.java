package com.example.geeknews.adapter;

import android.content.Context;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.geeknews.R;
import com.example.geeknews.bean.SectionListBean;
import com.example.geeknews.utils.ScreenUtils;


public class TopicAdapter extends BaseAdapter<SectionListBean.DataBean> {

    int width;

    public TopicAdapter(Context context) {
        super(context);
        width = ScreenUtils.getScreenWidth(context);
    }

    @Override
    protected int getItemLayout() {
        return R.layout.topic_item;
    }

    @Override
    protected void bindData(BaseViewHolder baseViewHolder, SectionListBean.DataBean dataBean) {

        ImageView img = (ImageView) baseViewHolder.getViewById(R.id.img);
        TextView name = (TextView) baseViewHolder.getViewById(R.id.name);

        ViewGroup.LayoutParams layoutParams = img.getLayoutParams();
        //条目高度是 屏幕宽度的一半
        layoutParams.height = width / 2;
        layoutParams.width = width / 2;
        img.setLayoutParams(layoutParams);

        name.setText(dataBean.getName());
        Glide.with(context).load(dataBean.getThumbnail()).into(img);
    }
}
