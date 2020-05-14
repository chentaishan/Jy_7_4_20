package com.example.geeknews.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.support.v4.util.ArrayMap;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.example.geeknews.R;
import com.example.geeknews.utils.ScreenUtils;
import com.example.geeknews.view.FlowLayout;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by codeest on 16/12/29.
 */

public class NodeAdapter extends RecyclerView.Adapter<NodeAdapter.ViewHolder> {

    private Context mContext;
    private LayoutInflater inflater;
    private ArrayMap<String, ArrayMap<String, String>> mMap;

    public NodeAdapter(Context context, ArrayMap<String, ArrayMap<String, String>> mMap) {
        this.mContext = context;
        this.mMap = mMap;
        inflater = LayoutInflater.from(mContext);
    }

    @Override
    public NodeAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(inflater.inflate(R.layout.item_node, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.tvTitle.setText(mMap.keyAt(position));
        holder.flContent.removeAllViews();
        ArrayMap<String, String> mNodeBlock = mMap.valueAt(position);
        for (ArrayMap.Entry<String, String> node : mNodeBlock.entrySet()) {
            TextView tvNode = new TextView(mContext);
            tvNode.setText(node.getValue());
            tvNode.setTextColor(ContextCompat.getColor(mContext, R.color.colorText));
            int size = ScreenUtils.dip2px(mContext, 6f);
            tvNode.setPadding(size,size,size,size);

            holder.flContent.addView(tvNode);
        }
    }

    @Override
    public int getItemCount() {
        return mMap.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_node_title)
        TextView tvTitle;
        @BindView(R.id.fl_node_content)
        FlowLayout flContent;

        public ViewHolder(View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tv_node_title);
            flContent = itemView.findViewById(R.id.fl_node_content);
        }
    }


}
