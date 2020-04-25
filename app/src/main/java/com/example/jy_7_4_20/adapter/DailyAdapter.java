package com.example.jy_7_4_20.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.jy_7_4_20.R;
import com.example.jy_7_4_20.bean.DailyListBean;
import com.youth.banner.Banner;
import com.youth.banner.loader.ImageLoader;

import java.util.ArrayList;
import java.util.List;

public class DailyAdapter extends RecyclerView.Adapter {

    Context context;

    List<DailyListBean.StoriesBean> stories = new ArrayList<>();
    List<DailyListBean.TopStoriesBean> topStoriesBeans = new ArrayList<>();

    String title ="今日新闻";

    public DailyAdapter(Context context) {
        this.context = context;
    }



    public void initData(List<DailyListBean.StoriesBean> stories) {

        this.stories = stories;
        notifyDataSetChanged();

    }
    public void initTopData(List<DailyListBean.TopStoriesBean> topStoriesBeans) {

        this.topStoriesBeans = topStoriesBeans;
        notifyDataSetChanged();

    }

    public enum ITEM_TYPE {
        ITEM_TOP,       //滚动栏
        ITEM_DATE,      //日期
        ITEM_CONTENT    //内容
    }

    @Override
    public int getItemViewType(int position) {
        if (position==0&&topStoriesBeans.size() > 0) {
            return ITEM_TYPE.ITEM_TOP.ordinal();
        } else if (position==1&&title != null) {
            return ITEM_TYPE.ITEM_DATE.ordinal();

        } else {
            return ITEM_TYPE.ITEM_CONTENT.ordinal();
        }

    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        if (i== ITEM_TYPE.ITEM_TOP.ordinal()){

            final View inflate = LayoutInflater.from(context).inflate(R.layout.item_top, viewGroup,false);
            return new TopViewHolder(inflate);
        }else if(i== ITEM_TYPE.ITEM_DATE.ordinal()){
            final View inflate = LayoutInflater.from(context).inflate(R.layout.item_date,  viewGroup,false);
            return new DateViewHolder(inflate);
        }else{
            final View inflate = LayoutInflater.from(context).inflate(R.layout.item_daily,  viewGroup,false);
            return new ListViewHolder(inflate);
        }

    }

    @Override
    public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder viewHolder, int i) {

        if (viewHolder instanceof TopViewHolder){

            ((TopViewHolder) viewHolder).banner.setImages(topStoriesBeans);

            ((TopViewHolder) viewHolder).banner.setImageLoader(new ImageLoader() {
                @Override
                public void displayImage(Context context, Object path, ImageView imageView) {
                    DailyListBean.TopStoriesBean topStoriesBean = (DailyListBean.TopStoriesBean) path;

                    Glide.with(context).load(topStoriesBean.getImage()).into(imageView);
                }
            }).start();

        }else if(viewHolder instanceof DateViewHolder){

            ((DateViewHolder) viewHolder).date.setText(title);
        }else if (viewHolder instanceof ListViewHolder){


            if (topStoriesBeans.size()>0){
                i = i-1;
            }
            if (title!=null){
                i= i-1;
            }
            if (stories.size()==0||stories.size()<=i){
                return;
            }
            final DailyListBean.StoriesBean storiesBean = stories.get(i);
            Glide.with(context).load(storiesBean.getImages().get(0)).into(((ListViewHolder) viewHolder).imageView);
            ((ListViewHolder) viewHolder).title.setText(storiesBean.getTitle());

            viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    final Intent intent = new Intent(context, ZhiDetailActivity.class);
//
//                    intent.putExtra("id",storiesBean.getId());
//                    context.startActivity(intent);
                }
            });
        }

    }



    @Override
    public int getItemCount() {
        int size = 0;
        if (title!=null){
            size= stories.size()+topStoriesBeans.size()+1;
        }
        return size;
    }

    public class TopViewHolder extends RecyclerView.ViewHolder {


        Banner banner;
        public TopViewHolder(@NonNull View itemView) {
            super(itemView);

            banner = itemView.findViewById(R.id.banner);

        }
    }
    public class DateViewHolder extends RecyclerView.ViewHolder {


        TextView date;

        public DateViewHolder(@NonNull View itemView) {
            super(itemView);
            date = itemView.findViewById(R.id.tv_daily_date);

        }
    }
    public class ListViewHolder extends RecyclerView.ViewHolder {

        ImageView imageView;

        TextView title;

        public ListViewHolder(View inflate) {
            super(inflate);

            imageView = itemView.findViewById(R.id.iv_daily_item_image);
            title = itemView.findViewById(R.id.tv_daily_item_title);
        }
    }
}
