package com.example.geeknews;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.geeknews.bean.DailyListBean;
import com.example.geeknews.ui.DailyDetailsActivity;
import com.youth.banner.Banner;
import com.youth.banner.loader.ImageLoader;

import java.util.ArrayList;
import java.util.List;

public class DailyAdapter extends RecyclerView.Adapter {

    public static final int BANER_STYLE = 0;
    public static final int LIST_STYLE = 1;
    public static final int DATE = 2;

    String date;

    Context context;

    private List<DailyListBean.TopStoriesBean> banner = new ArrayList<>();
    private List<DailyListBean.StoriesBean> storyList = new ArrayList<>();


    public DailyAdapter(Context context) {
        this.context = context;
    }

    public void setBanner(List<DailyListBean.TopStoriesBean> banner) {
        this.banner.addAll(banner);
        notifyDataSetChanged();
    }

    public void setStoryList(List<DailyListBean.StoriesBean> storyList,String date) {
        this.storyList.addAll(storyList);
        this.date = date;
        notifyDataSetChanged();

    }

    /**
     * 处理以前数据
     * @param storyList
     * @param date
     */
    public void setBeforeStoryList(List<DailyListBean.StoriesBean> storyList,String date) {
        this.banner.clear();
        this.storyList.clear();
        this.storyList.addAll(storyList);
        this.date = date;
        notifyDataSetChanged();

    }
    @Override
    public int getItemViewType(int position) {
        if (position == 0 && banner.size() > 0) {
            return BANER_STYLE;
            //分两种情况，
            // 1-有banner 今日新闻占pos=1 位置 date不能为空；
            // 2-banner没数据 且pos=0
        } else if ((banner.size()==0&&position==0)||(banner.size()>0&&!TextUtils.isEmpty(date)&&position==1)) {//20200911
            return DATE;
        } else {
            return LIST_STYLE;
        }

    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int type) {
        if (type == BANER_STYLE) {
            View root = LayoutInflater.from(context).inflate(R.layout.item_top, viewGroup, false);

            return new BannerViewHolder(root);
        } else if (type == DATE) {

            View root = LayoutInflater.from(context).inflate(R.layout.item_date, viewGroup, false);
            return new TextViewHolder(root);
        } else {

            View root = LayoutInflater.from(context).inflate(R.layout.item_daily, viewGroup, false);

            return new ListViewHolder(root);
        }

    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int pos) {

        if (BANER_STYLE == getItemViewType(pos)) {

            ((BannerViewHolder) viewHolder).banner.setImages(banner).setImageLoader(new ImageLoader() {
                @Override
                public void displayImage(Context context, Object path, ImageView imageView) {
                    DailyListBean.TopStoriesBean topStoriesBean = (DailyListBean.TopStoriesBean) path;
                    Glide.with(context).load(topStoriesBean.getImage()).into(imageView);

                }
            }).start();

        } else if (DATE == getItemViewType(pos)) {

            ((TextViewHolder) viewHolder).date.setText(date);
        } else {
            if (banner.size() > 0) {
                pos = pos - 1;
            }
            if (!TextUtils.isEmpty(date)) {
                pos = pos - 1;
            }

            Log.d(TAG, "pos: "+pos);
            final DailyListBean.StoriesBean storiesBean = storyList.get(pos);

//            ((ListViewHolder)viewHolder).img
            if (storiesBean.getImages() != null && storiesBean.getImages().size() > 0) {
                Glide.with(context).load(storiesBean.getImages().get(0)).into(((ListViewHolder) viewHolder).img);

            }
            ((ListViewHolder) viewHolder).title.setText(storiesBean.getTitle());

            ((ListViewHolder) viewHolder).itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, DailyDetailsActivity.class);
                    intent.putExtra("id",storiesBean.getId());
                    context.startActivity(intent);
                }
            });

        }

    }

    private static final String TAG = "DailyAdapter";
    @Override
    public int getItemCount() {
        int size = banner.size() > 0 ? storyList.size() + 1 : storyList.size();
        if (!TextUtils.isEmpty(date)) {
            size = size + 1;
        }

        Log.d(TAG, "getItemCount: "+size);
        return size;
    }

    private class BannerViewHolder extends RecyclerView.ViewHolder {

        Banner banner;

        public BannerViewHolder(View root) {
            super(root);
            banner = root.findViewById(R.id.banner);
        }
    }

    private class ListViewHolder extends RecyclerView.ViewHolder {
        ImageView img;
        TextView title;

        public ListViewHolder(View root) {
            super(root);
            img = root.findViewById(R.id.iv_daily_item_image);
            title = root.findViewById(R.id.tv_daily_item_title);
        }
    }

    private class TextViewHolder extends RecyclerView.ViewHolder {
        TextView date;

        public TextViewHolder(View root) {
            super(root);

            date = root.findViewById(R.id.tv_daily_date);
        }
    }
}
