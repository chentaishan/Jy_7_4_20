package com.example.geeknews.ui;

import android.content.res.XmlResourceParser;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.util.ArrayMap;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.geeknews.R;
import com.example.geeknews.adapter.NodeAdapter;
import com.example.geeknews.utils.XmlUtil;

public class NodeActivity extends AppCompatActivity {

    private ArrayMap<String, ArrayMap<String, String>> map;
    private Toolbar mToolbar;
    private RecyclerView mContentRv;
    private TextView mNodeTitleTv;
    private NodeAdapter mAdapter;
    private LinearLayoutManager mManager;
    private int mTitleHeight;
    private int mCurrentPosition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_node);


        XmlResourceParser xmlParser = this.getResources().getXml(R.xml.nodes);
        try {
            map = XmlUtil.parseNodes(xmlParser);


        } catch (Exception e) {
            e.printStackTrace();
        }

        initView();
    }

    private static final String TAG = "NodeActivity";
    private void initView() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        mContentRv = (RecyclerView) findViewById(R.id.rv_content);
        mNodeTitleTv = (TextView) findViewById(R.id.tv_node_title);

        mAdapter = new NodeAdapter(this , map);
        mManager = new LinearLayoutManager(this);
        mContentRv.setLayoutManager(mManager);
        mContentRv.setAdapter(mAdapter);
        mContentRv.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);

                mTitleHeight = mNodeTitleTv.getHeight();

            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);


                View view = mManager.findViewByPosition(mCurrentPosition + 1);
                if (view != null) {
                    if (view.getTop() <= mTitleHeight) {
                        mNodeTitleTv.setY(-(mTitleHeight - view.getTop()));
                        Log.d(TAG, "onScrolled: "+(mTitleHeight - view.getTop()));
                    } else {
                        Log.d(TAG, "onScrolled: setY(0);");
                        mNodeTitleTv.setY(0);
                    }
                }

                if (mCurrentPosition != mManager.findFirstVisibleItemPosition()) {
                    mCurrentPosition = mManager.findFirstVisibleItemPosition();
                    mNodeTitleTv.setY(0);
                    if (map != null) {
                        mNodeTitleTv.setText(map.keyAt(mCurrentPosition));

                        Log.d(TAG, "onScrolled:map.keyAt(mCurrentPosition)"+map.keyAt(mCurrentPosition));
                    }
                }
            }
        });
        mNodeTitleTv.setText(map.keyAt(0));
    }
}
