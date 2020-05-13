package com.example.moveswipedemo;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView mRecycler;
    private RecyclerView.Adapter<ViewHolder> adapter;

    List<String>  list = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        for (int i = 0; i < 22; i++) {
            list.add(i+"");
        }

        initView();
    }

    private void initView() {
        mRecycler = (RecyclerView) findViewById(R.id.recycler);
        mRecycler.setLayoutManager(new LinearLayoutManager(this));
        mRecycler.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.VERTICAL));

        adapter = new RecyclerView.Adapter<ViewHolder>() {
            @NonNull
            @Override
            public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
                View root = LayoutInflater.from(MainActivity.this).inflate(R.layout.item, viewGroup, false);
                return new ViewHolder(root);
            }

            @Override
            public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {

                viewHolder.name.setText(i + "");
            }

            @Override
            public int getItemCount() {
                return list.size();
            }
        };
        mRecycler.setAdapter(adapter);
        itemTouchHelper.attachToRecyclerView(mRecycler);
    }

    class ViewHolder  extends RecyclerView.ViewHolder{

        TextView name;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name);
        }
    }

    ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new ItemTouchHelper.Callback(){
        //同来设置 拖拽移动，或移动删除
        @Override
        public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
            int swiped = ItemTouchHelper.RIGHT | ItemTouchHelper.LEFT;
            int   dragFlags= ItemTouchHelper.UP | ItemTouchHelper.DOWN;

            //第一个参数拖动，第二个删除侧滑
            return makeMovementFlags(dragFlags, swiped);
        }
        //在拖动中不断的回调这个方法
        @Override
        public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
            int oldPosition = viewHolder.getAdapterPosition();
            int newPosition = target.getAdapterPosition();
            if (oldPosition < newPosition) {
                for (int i = oldPosition; i < newPosition; i++) {
                    // 改变实际的数据集
                    Collections.swap(list, i, i +1);
                }
            } else {
                for (int i = oldPosition; i > newPosition; i--) {
                    // 改变实际的数据集
                    Collections.swap(list, i, i - 1);
                }
            }
            adapter.notifyItemMoved(oldPosition, newPosition);
            return false;
        }
        //处理侧滑
        @Override
        public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
            int position = viewHolder.getAdapterPosition();
            list.remove(position);
            adapter.notifyItemRemoved(position);
        }

        @Override
        public void onSelectedChanged(RecyclerView.ViewHolder viewHolder, int actionState) {
            if (actionState!=ItemTouchHelper.ACTION_STATE_IDLE){
                viewHolder.itemView.setBackgroundColor(Color.parseColor("#303F9F"));
            }
        }
        @Override
        public void clearView(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
            super.clearView(recyclerView, viewHolder);
            viewHolder.itemView.setBackgroundColor(Color.TRANSPARENT);
        }
    });


}
