package com.example.geeknews.ui;

import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;

import com.example.geeknews.R;
import com.example.geeknews.adapter.GoldManagerAdapter;
import com.example.geeknews.bean.TabEvent;
import com.example.geeknews.ui.base.BaseActivity;
import com.example.geeknews.utils.Constants;

import org.greenrobot.eventbus.EventBus;

import java.util.Collections;


/**
 * Created by codeest on 16/11/27.
 */

public class GoldManagerActivity extends BaseActivity {


    private Toolbar mToolbar;
    private RecyclerView mGoldManagerListRv;
    private GoldManagerAdapter goldManagerAdapter;


    ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new ItemTouchHelper.Callback() {
        @Override
        public int getMovementFlags(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder) {
            int dragFlags = ItemTouchHelper.UP|ItemTouchHelper.DOWN;
            return makeMovementFlags(dragFlags,0);
        }

        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder targetViewHolder) {

            int oldPos = viewHolder.getAdapterPosition();
            int newPos = targetViewHolder.getAdapterPosition();

            // 更新数据集合里的位置
            Collections.swap(Constants.title,oldPos,newPos);
            Collections.swap(Constants.isSelected,oldPos,newPos);

            // 适配器 局部刷新
            goldManagerAdapter.notifyItemMoved(oldPos,newPos);


            return false;
        }

        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int i) {

        }
    });

    @Override
    protected void initData() {

    }

    @Override
    protected int getLayout() {
        return R.layout.activity_gold_manager;
    }

    public void initView() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mToolbar.setTitle("管理页面");
        setSupportActionBar(mToolbar);
        mGoldManagerListRv = (RecyclerView) findViewById(R.id.rv_gold_manager_list);
        mGoldManagerListRv.setLayoutManager(new LinearLayoutManager(this));

        goldManagerAdapter = new GoldManagerAdapter(this);
        mGoldManagerListRv.setAdapter(goldManagerAdapter);


        //itemTouchHelper绑定到recyclerview
        itemTouchHelper.attachToRecyclerView(mGoldManagerListRv);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        // TODO 通知上个fragment重新适配tab

        EventBus.getDefault().post(new TabEvent());

    }
}
