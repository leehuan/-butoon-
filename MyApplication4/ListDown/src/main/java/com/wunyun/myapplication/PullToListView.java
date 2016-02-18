package com.wunyun.myapplication;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

/**
 * Created by LEE on 2016-02-18.
 */
public class PullToListView extends ListView {

    private int downY;
    private int moveY;
    private View view;
    private int measuredHeight;

    private int NOW_STATE = NEXT_FALSH;
    private static final int NEXT_FALSH = 1;
    private static final int LOOSEN_FALSH = 2;
    private static final int PROCESS_FALSH = 3;
    private TextView textview;
    private ProgressBar progressbar;
    private ImageView imgview;

    public PullToListView(Context context) {
//        super(context);
        this(context, null);
    }


    public PullToListView(Context context, AttributeSet attrs) {
//        super(context, attrs);
        this(context, attrs, 0);
    }

    public PullToListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        setHandView();


    }

    private void setHandView() {
        view = View.inflate(getContext(), R.layout.list_hander, null);
        textview = (TextView) view.findViewById(R.id.ListDown_tv);
        progressbar = (ProgressBar) view.findViewById(R.id.listdown_bar);
        imgview = (ImageView) view.findViewById(R.id.listdown_iv);
        view.measure(0, 0);
        view.measure(0, 0);//请就系统替我们测量刷新头的高和宽
        measuredHeight = view.getMeasuredHeight();
        view.setPadding(0, -measuredHeight, 0, 0);
        addHeaderView(view);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                downY = (int) ev.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                moveY = (int) ev.getY();
                moveY = moveY - downY;
                if (moveY > 0 && getFirstVisiblePosition() == 0) {
                    int i = moveY - measuredHeight;
                    view.setPadding(0, i, 0, 0);
                    if (moveY > 0 && NOW_STATE == NEXT_FALSH) {
                        //TODO 设置下拉刷新有问题，进行改变
                        NOW_STATE = LOOSEN_FALSH;
                        switchPosition(NOW_STATE);
                    }
                    if (moveY > measuredHeight && NOW_STATE == LOOSEN_FALSH) {

                        NOW_STATE = NEXT_FALSH;
                        switchPosition(LOOSEN_FALSH);
                    }
                }
                break;
            case MotionEvent.ACTION_UP:
                view.setPadding(0, -measuredHeight, 0, 0);

                break;
        }


        return super.onTouchEvent(ev);
    }


    private void switchPosition(int statr) {
        switch (statr) {
            case NEXT_FALSH:
                textview.setText("下拉刷新");
                break;
            case LOOSEN_FALSH:
                textview.setText("松开刷新");
                break;
            case PROCESS_FALSH:
                textview.setText("正在刷新");
                break;

        }
    }
}
