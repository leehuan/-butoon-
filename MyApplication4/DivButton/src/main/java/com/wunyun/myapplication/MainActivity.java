package com.wunyun.myapplication;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.wunyun.myapplication.ui.MyButton;

public class MainActivity extends AppCompatActivity {

    private MyButton mButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        mButton = (MyButton) findViewById(R.id.mybutton);
       mButton.setBackgroundImager(R.drawable.slide_background,R.drawable.slide_icon);

       mButton.setStart(false);
        mButton.setTogaller(new MyButton.setOnToast() {
            @Override
            public void setToast(boolean isOpen) {
                Toast.makeText(getApplicationContext(), isOpen ? "开启" : "关闭", Toast.LENGTH_SHORT).show();
            }
        });

    }
}
