package com.example.tiku_b;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

import com.google.gson.Gson;

import java.io.IOException;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private RecyclerView mRecyclerview;
    private Button mClick1;
    private Button mClick2;
    private Button mClick3;
    private MultiAdapter multiAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initData();
    }

    String url = "http://gank.io/api/data/%E7%A6%8F%E5%88%A9/20/3";

    private void initData() {
        OkHttpClient okHttpClient = new OkHttpClient();
        Request request = new Request.Builder()
                .url(url)
                .build();

        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

                String result = response.body().string();

                InfoBean infoBean = new Gson().fromJson(result, InfoBean.class);

                final List<InfoBean.ResultsBean> results = infoBean.getResults();

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        multiAdapter = new MultiAdapter(MainActivity.this, results);
                        mRecyclerview.setAdapter(multiAdapter);

                    }
                });


            }
        });
    }

    private void initView() {
        mRecyclerview = (RecyclerView) findViewById(R.id.recyclerview);
        mRecyclerview.setLayoutManager(new LinearLayoutManager(this));


        mClick1 = (Button) findViewById(R.id.click1);
        mClick1.setOnClickListener(this);
        mClick2 = (Button) findViewById(R.id.click2);
        mClick2.setOnClickListener(this);
        mClick3 = (Button) findViewById(R.id.click3);
        mClick3.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.click1:
                // TODO 20/05/06
                multiAdapter.setEdit(true);

                break;
            case R.id.click2:
                // TODO 20/05/06
                multiAdapter.deleItem();
                break;
            case R.id.click3:
                // TODO 20/05/06
                multiAdapter.setEdit(false);

                break;
            default:
                break;
        }
    }
}
