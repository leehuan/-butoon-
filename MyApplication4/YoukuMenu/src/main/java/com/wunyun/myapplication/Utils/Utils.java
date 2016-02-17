package com.wunyun.myapplication.Utils;

import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;

/**
 * Created by LEE on 2016-02-16.
 */
public class Utils {
        private static int count=0;
    /**
     * 设置一个标示来记录是否动画在执行
     */

    /**
     * 隐藏控件
     *
     * @param menu
     */
    public static void hidoMenu(View menu) {
        SetAnimation(menu, 0, -180, 0);
        isAable(menu, false);


    }

    public static void hidoMenu(View menu, long setOffset) {
        SetAnimation(menu, 0, -180, setOffset);
        isAable(menu, false);


    }

    /**
     * 显示控件
     *
     * @param menu
     */
    public static void showMenu(View menu) {
        SetAnimation(menu, -180, 0, 0);
        isAable(menu, true);
    }

    /**
     * 将显示和隐藏的方法抽取出来
     *
     * @param menu
     * @param fromAngel
     * @param toAngel
     */
    private static void SetAnimation(View menu, float fromAngel, float toAngel, long setOffset) {

      RotateAnimation rotateAnimation = new RotateAnimation(fromAngel, toAngel, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 1f);
        rotateAnimation.setDuration(500);
        //执行完成后是否保持其状态
        rotateAnimation.setFillAfter(true);
        rotateAnimation.setStartOffset(setOffset);
        rotateAnimation.setAnimationListener(animationListener);
        menu.startAnimation(rotateAnimation);
    }

   static Animation.AnimationListener animationListener = new Animation.AnimationListener() {
        @Override
        public void onAnimationStart(Animation animation) {
            count++;
        }

        @Override
        public void onAnimationEnd(Animation animation) {
            count--;
        }

        @Override
        public void onAnimationRepeat(Animation animation) {

        }
    };

    /**
     * 设置能否被点击
     *
     * @param menu
     * @param b
     */
    private static void isAable(View menu, boolean b) {
        menu.setClickable(b);
        if (menu instanceof ViewGroup) {
            ViewGroup view = (ViewGroup) menu;
            for (int i = 0; i < view.getChildCount(); i++) {
                View childAt = view.getChildAt(i);
                childAt.setClickable(b);

            }
        }
    }
    /**
     * 设置动画是否在执行
     */
    public static boolean isAnimationcc(){
        return count>0;
    }
}
