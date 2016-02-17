package com.wunyun.myapplication;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.RelativeLayout;

import com.wunyun.myapplication.Utils.Utils;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageButton menu;
    private RelativeLayout menu2;
    private RelativeLayout menu3;
    private ImageButton home;
    private boolean isShowMenu = true;
    private boolean isShowMenu2 = true;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {

        menu = (ImageButton) findViewById(R.id.menu);
        menu2 = (RelativeLayout) findViewById(R.id.menu2);
        menu3 = (RelativeLayout) findViewById(R.id.menu3);
        home = (ImageButton) findViewById(R.id.home);

        home.setOnClickListener(this);
        menu.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.home:
                if(Utils.isAnimationcc()){
                    return;
                }
                if (isShowMenu) {
                    //显示--隐藏
                    Utils.hidoMenu(menu3);
                    //隐藏中部菜单
                    isShowMenu = false;
                    Utils.hidoMenu(menu2, 300l);
                } else if (isShowMenu2) {
                    //显示--隐藏
                    Utils.hidoMenu(menu2);

                } else {
                    //隐藏--显示
                    Utils.showMenu(menu2);

                }
                isShowMenu2 = !isShowMenu2;
                break;
            case R.id.menu:
                if(Utils.isAnimationcc()){
                    return;
                }
                if (isShowMenu) {
                    //显示--隐藏
                    Utils.hidoMenu(menu3);
                } else {
                    //隐藏--显示
                    Utils.showMenu(menu3);
                }
                isShowMenu = !isShowMenu;
                break;
        }
    }
}
