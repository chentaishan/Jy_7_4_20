package com.example.tiku_b;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

public class MultiAdapter extends RecyclerView.Adapter<MultiAdapter.ViewHolder> {

//    编辑
    boolean isEdit;

    Context context;
    List<InfoBean.ResultsBean> results = new ArrayList<>();

    List<Boolean> status = new ArrayList<>();

    public MultiAdapter(Context context, List<InfoBean.ResultsBean> results) {
        this.context = context;
        this.results = results;

        if (results.size() > 0) {
            for (InfoBean.ResultsBean result : results) {
                status.add(false);
            }
        }

    }

    public void setEdit(boolean edit) {
        isEdit = edit;
        notifyDataSetChanged();
    }

    /**
     * 删除勾选的item
     * 0----9-10
     * 10-0
     */
    public void deleItem(){

        for (int i = status.size()-1; i >=0; i--) {
            Log.d(TAG, "deleItem: i="+i);
            if (status.get(i)){
                results.remove(i);
                status.remove(i);
            }
        }

        notifyDataSetChanged();

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View root = LayoutInflater.from(context).inflate(R.layout.recy_item, viewGroup, false);
        return new ViewHolder(root);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, final int i) {

        InfoBean.ResultsBean resultsBean = results.get(i);

        Glide.with(context).load(resultsBean.getUrl()).into(viewHolder.img);

        if (isEdit){

            viewHolder.checkBox.setVisibility(View.VISIBLE);
        }else{
            viewHolder.checkBox.setVisibility(View.GONE);

        }

        viewHolder.checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Boolean aBoolean = status.get(i);

                if (aBoolean) {
                    viewHolder.checkBox.setChecked(false);
                    status.set(i, false);
                } else {
                    status.set(i, true);
                    viewHolder.checkBox.setChecked(true);
                }
            }
        });

        final Boolean aBoolean = status.get(i);

        Log.d(TAG, "onBindViewHolder: pos=" + i + "----status=" + aBoolean);
        //从数据源 拿出 是否被选择的状态
        viewHolder.checkBox.setChecked(aBoolean);

    }


    private static final String TAG = "MultiAdapter";

    @Override
    public int getItemCount() {
        return results.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        CheckBox checkBox;
        ImageView img;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            checkBox = itemView.findViewById(R.id.checkbox);
            img = itemView.findViewById(R.id.img);
        }
    }
}
