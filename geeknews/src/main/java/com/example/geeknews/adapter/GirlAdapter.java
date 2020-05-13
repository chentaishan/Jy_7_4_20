package com.example.geeknews.adapter;

import android.content.Context;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.geeknews.R;
import com.example.geeknews.bean.GirlBean;

import java.util.Random;


public class GirlAdapter extends BaseAdapter<GirlBean.ResultsBean> {

    public GirlAdapter(Context context) {
        super(context);
    }

    @Override
    protected int getItemLayout() {
        return R.layout.girl_item;
    }

    @Override
    protected void bindData(BaseViewHolder baseViewHolder, GirlBean.ResultsBean girlBean) {

        final ImageView imageView = (ImageView) baseViewHolder.getViewById(R.id.img);

        Glide.with(context).load(girlBean.getUrl()).into(imageView);
        // 随机高度

        Random random = new Random();
        int i = random.nextInt(600); //0-500
        int height = i<300 ? 300 :i;

        Log.d(TAG, "height: "+height);
        ViewGroup.LayoutParams layoutParams = imageView.getLayoutParams();
        layoutParams.height = height;
        imageView.setLayoutParams(layoutParams);

        // ( pos  1=500  2=400)

        baseViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    private static final String TAG = "GirlAdapter";
}
