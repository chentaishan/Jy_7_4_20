package com.example.geeknews.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SwitchCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.example.geeknews.R;
import com.example.geeknews.utils.Constants;

import java.util.List;


/**
 * Created by codeest on 16/11/27.
 */

public class GoldManagerAdapter extends RecyclerView.Adapter<GoldManagerAdapter.ViewHolder> {

    Context context;

    public GoldManagerAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View root = LayoutInflater.from(context).inflate(R.layout.item_gold_manager,viewGroup,false);
        return new ViewHolder(root);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int i) {

        String title = Constants.title.get(i);
        viewHolder.title.setText(title);
        viewHolder.switchCompat.setChecked(Constants.isSelected.get(i));

        viewHolder.switchCompat.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Constants.isSelected.set(i,isChecked);
            }
        });

    }

    @Override
    public int getItemCount() {
        return Constants.title.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView title;
        SwitchCompat  switchCompat;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            title = itemView.findViewById(R.id.tv_gold_manager_type);
            switchCompat = itemView.findViewById(R.id.sc_gold_manager_switch);
        }
    }
}
