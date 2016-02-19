package com.wunyun.myapplication.ui;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by LEE on 2016-02-17.
 */
public class MyButton extends View {

    private Bitmap icon;
    private Bitmap background;
    private int sileft;
    private int scallMax;
    private boolean isUp;

    public MyButton(Context context) {
//        super(context);
        this(context, null);
    }

    public MyButton(Context context, AttributeSet attrs) {
//        super(context, attrs);
        this(context, attrs, 0);
    }


    public MyButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
//        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.MyButton);
//        int resourceId = typedArray.getResourceId(R.styleable.MyButton_backgroundtototo, -1);
//        int resourceId1 = typedArray.getResourceId(R.styleable.MyButton_Icon, -1);
//        boolean aBoolean = typedArray.getBoolean(R.styleable.MyButton_starttoot, false);
//        if (resourceId != -1 && resourceId1 != -1) {
//            setBackgroundImager(resourceId, resourceId1);
//
//        }
//        setStart(aBoolean);

    }

    public void setBackgroundImager(int backgroundImager, int buttonIcon) {
        background = BitmapFactory.decodeResource(getResources(), backgroundImager);
        icon = BitmapFactory.decodeResource(getResources(), buttonIcon);
    }


    public void setStart(boolean start) {
        isUp = true;
        if (start) {
            sileft = scallMax;
        } else {
            sileft = 0;
        }
        invalidate();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
//        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(background.getWidth(), background.getHeight());
        scallMax = background.getWidth() - icon.getWidth();
    }

    @Override
    protected void onDraw(Canvas canvas) {
//        super.onDraw(canvas);
        canvas.drawBitmap(background, 0, 0, null);
        if (sileft < 0) {
            sileft = 0;
        } else if (sileft > scallMax) {
            sileft = scallMax;
        }
        boolean isOpen = sileft > 0;
        if (isUp) {
            if (setontoast != null) {
                setontoast.setToast(isOpen);
            }
            isUp = false;
        }


        canvas.drawBitmap(icon, sileft, 0, null);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                sileft = (int) (event.getX() - icon.getWidth() / 2);
                break;
            case MotionEvent.ACTION_MOVE:
                sileft = (int) (event.getX() - icon.getWidth() / 2);
                break;
            case MotionEvent.ACTION_UP:
                isUp = true;
                if (event.getX() < background.getWidth() / 2) {
                    sileft = 0;
                } else if (event.getX() > background.getWidth() / 2) {
                    sileft = scallMax;
                }
                break;
        }

        invalidate();

        return true;
    }

    private setOnToast setontoast;

    public void setTogaller(setOnToast setontoast) {

        this.setontoast = setontoast;
    }


    public interface setOnToast {
        void setToast(boolean isOpen);
    }


}
