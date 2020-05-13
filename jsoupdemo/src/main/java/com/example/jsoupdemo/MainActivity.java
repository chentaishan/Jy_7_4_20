package com.example.jsoupdemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button mClick;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        mClick = (Button) findViewById(R.id.click);
        mClick.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.click:
                // TODO 20/05/11
                initJsoup();
                break;
            default:
                break;
        }
    }

    private static final String TAG = "MainActivity";

    private void initJsoup() {

        new Thread() {

            public void run() {
                try {
                    Document doc = Jsoup.connect("https://www.v2ex.com/?tab=0").get();

                    Elements select = doc.select("div.cell.item");


                    for (int i = 0; i < select.size(); i++) {

                        Elements titleElements = select.get(i).select("div.cell.item table tr td span.item_title > a");   //标题
                        Elements imgElements = select.get(i).select("div.cell.item table tr td img.avatar");   //标题

                        if (titleElements.size() > 0) {

                            Element element = titleElements.get(0);

                            String href = element.attr("href");
                            String title = element.text();
                            Log.d(TAG, "element: " + href + "-----title=" + title);
                        }

                        if (imgElements.size() > 0) {
                            Element element = imgElements.get(0);

                            String img = element.attr("src");

                            Log.d(TAG, "element: img=" + img);
                        }
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.start();

    }
}
