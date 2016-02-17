package com.wuyun.myapplication.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.wunyun.myapplication.R;

/**
 * Created by LEE on 2016-02-12.
 */
public class SettingUi extends RelativeLayout {

    private View view;
    private ImageView img;
    private TextView text;

    public SettingUi(Context context) {
//        super(context);
        this(context, null);
    }

    public SettingUi(Context context, AttributeSet attrs) {
//        super(context, attrs);
        this(context, attrs, 0);
    }


    public SettingUi(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.SettingUi);
        String title = typedArray.getString(R.styleable.SettingUi_titletext);
        int imgtwo = typedArray.getInt(R.styleable.SettingUi_img, 0);
        boolean aBoolean = typedArray.getBoolean(R.styleable.SettingUi_istooles, true);
        text.setText(title);
        switch (imgtwo) {
            case 0:
                view.setBackgroundResource(R.drawable.selector_setting_first);
                break;
            case 1:
                view.setBackgroundResource(R.drawable.selector_setting_mid);
                break;
            case 2:
                view.setBackgroundResource(R.drawable.selector_setting_last);
                break;
            default:
                view.setBackgroundResource(R.drawable.selector_setting_first);
                break;
        }
        img.setVisibility(aBoolean ? View.VISIBLE : View.GONE);

    }

    private void initView() {
        view = View.inflate(getContext(), R.layout.setting_view, this);
        img = (ImageView) view.findViewById(R.id.setting_iv_img);
        text = (TextView) view.findViewById(R.id.setting_tv_text);
    }
}
