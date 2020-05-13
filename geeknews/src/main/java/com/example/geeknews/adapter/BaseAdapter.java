package com.example.geeknews.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * 基类 baseAdapter  支持单一数据
 * @param <T>
 */
public abstract class BaseAdapter<T> extends RecyclerView.Adapter {

    Context context;
    List<T> datalist = new ArrayList<>();

    public BaseAdapter(Context context) {
        this.context = context;
    }

    public void resetDataList(List<T> datalist) {
        this.datalist.clear();
        this.datalist.addAll(datalist);
        notifyDataSetChanged();
    }

    public void addDataList(List<T> datalist) {
        this.datalist.addAll(datalist);

        notifyDataSetChanged();

    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(  ViewGroup parent, int viewType) {
        View root = LayoutInflater.from(context).inflate(getItemLayout(),parent,false);

        return new BaseViewHolder(root);
    }

    protected abstract int getItemLayout();

    @Override
    public void onBindViewHolder( RecyclerView.ViewHolder holder, final int position) {
        final BaseViewHolder baseViewHolder = (BaseViewHolder) holder;

        bindData(baseViewHolder,datalist.get(position));

        baseViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (iOnClickItem!=null){
                    iOnClickItem.clickItem(baseViewHolder,position);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return datalist.size();
    }

    protected abstract void bindData(BaseViewHolder baseViewHolder, T t);

    static class BaseViewHolder extends RecyclerView.ViewHolder {


        public BaseViewHolder(  View itemView) {
            super(itemView);

        }

        public View getViewById(int id){

            return itemView.findViewById(id);
        }
    }


    IOnClickItem iOnClickItem;

    public void setiOnClickItem(IOnClickItem iOnClickItem) {
        this.iOnClickItem = iOnClickItem;
    }

    interface IOnClickItem{
        void clickItem(BaseViewHolder viewHolder,int pos);
    }
}

