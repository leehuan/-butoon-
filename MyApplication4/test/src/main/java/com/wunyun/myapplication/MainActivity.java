package com.wunyun.myapplication;

import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    private ViewParent viewById;
    private ViewPager viewById1;
    private int[] imageResIds = {
            R.drawable.a,
            R.drawable.b,
            R.drawable.c,
            R.drawable.d,
            R.drawable.e,
    };

    private String[] descs = {
            "巩俐不低俗，我就不能低俗",
            "扑树又回来啦！再唱经典老歌引万人大合唱",
            "揭秘北京电影如何升级",
            "乐视网TV版大派送",
            "热血屌丝的反杀",
    };

    private ImageView[] imageViews = new ImageView[imageResIds.length];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        viewById1 = (ViewPager) findViewById(R.id.view_pager);
        viewById1.setAdapter(pagerAdapter);
        for (int i = 0; i < imageResIds.length; i++) {
            setImager(i);
        }
    }

    private void setImager(int i) {
        imageViews[i] = new ImageView(this);
        imageViews[i].setBackgroundResource(imageResIds[i]);

    }

    PagerAdapter pagerAdapter = new PagerAdapter() {
        @Override
        public int getCount() {
            return imageResIds.length;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            ImageView imageView = imageViews[position];
            //ViewGroup 将获取的每一个图片添加到ViewGroup中
            container.addView(imageView);


            return imageView;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            super.destroyItem(container, position, object);
        }
    };
}
