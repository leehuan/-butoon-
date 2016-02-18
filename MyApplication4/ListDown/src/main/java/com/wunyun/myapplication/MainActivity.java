package com.wunyun.myapplication;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private PullToListView pullview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {

        pullview = (PullToListView) findViewById(R.id.pulllistview);
        ArrayList<String> arrayList = new ArrayList<String>();
        for (int i = 0; i <= 30; i++) {
            arrayList.add("我有" + i + "万");
        }
        ArrayAdapter arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, arrayList);

        pullview.setAdapter(arrayAdapter);


    }
}
