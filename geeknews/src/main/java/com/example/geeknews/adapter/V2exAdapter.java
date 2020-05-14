package com.example.geeknews.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.geeknews.R;
import com.example.geeknews.bean.V2exListBean;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by codeest on 16/12/23.
 */

public class V2exAdapter extends RecyclerView.Adapter<V2exAdapter.ViewHolder> {

    private Context mContext;
    private LayoutInflater inflater;
    private List<V2exListBean> mList =new ArrayList<>();

    public V2exAdapter(Context context) {
        this.mContext = context;

        inflater = LayoutInflater.from(mContext);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(inflater.inflate(R.layout.item_vtex, parent, false));
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        V2exListBean bean = mList.get(position);
//        ImageLoader.load(mContext, bean.getImgUrl(), holder.ivTopicFace);

        Glide.with(mContext).load(bean.getImgUrl()).into(holder.ivTopicFace);
        holder.tvTopicName.setText(bean.getName());
        holder.tvTopicTips.setText(bean.getUpdateTime() + " • 最后回复 " + bean.getLastUser());
        holder.tvTopicComment.setText(String.valueOf(bean.getCommentNum()));
        holder.tvTopicNode.setText(bean.getNode());
        holder.tvTopicTitle.setText(bean.getTitle());

    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public void updateData(List<V2exListBean> mList) {
        this.mList .addAll(mList);
        notifyDataSetChanged();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.iv_topic_face)
        ImageView ivTopicFace;
        @BindView(R.id.tv_topic_name)
        TextView tvTopicName;
        @BindView(R.id.tv_topic_tips)
        TextView tvTopicTips;
        @BindView(R.id.tv_topic_comment)
        TextView tvTopicComment;
        @BindView(R.id.tv_topic_node)
        TextView tvTopicNode;
        @BindView(R.id.tv_topic_title)
        TextView tvTopicTitle;

        public ViewHolder(View itemView) {
            super(itemView);
//            ButterKnife.bind(this, itemView);

            ivTopicFace = itemView.findViewById(R.id.iv_topic_face);
            tvTopicName = itemView.findViewById(R.id.tv_topic_name);
            tvTopicTips = itemView.findViewById(R.id.tv_topic_tips);
            tvTopicComment = itemView.findViewById(R.id.tv_topic_comment);
            tvTopicNode = itemView.findViewById(R.id.tv_topic_node);
            tvTopicTitle = itemView.findViewById(R.id.tv_topic_title);
        }
    }
}
