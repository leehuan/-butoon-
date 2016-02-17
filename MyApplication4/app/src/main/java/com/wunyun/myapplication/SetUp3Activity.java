package com.wunyun.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class SetUp3Activity extends AppCompatActivity implements View.OnClickListener {

    private Button next;
    private Button pre;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setup3);
        initView();
    }

    private void initView() {
        next = (Button) findViewById(R.id.bt_setup_next);
        pre = (Button) findViewById(R.id.bt_setup_pre);
        next.setOnClickListener(this);
        pre.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {

            case R.id.bt_setup_next:

                startActivity(new Intent(SetUp3Activity.this, SetUp4Activity.class));
                break;
            case R.id.bt_setup_pre:
                startActivity(new Intent(SetUp3Activity.this, SetUp2Activity.class));
                break;
        }
    }
}
