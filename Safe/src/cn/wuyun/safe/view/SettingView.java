package cn.wuyun.safe.view;

import cn.wuyun.safe.R;
import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class SettingView extends RelativeLayout {

	private View view;
	private TextView tv_setting_title;
	private ImageView im_setting_toggle;

	/**
	 * 按钮状态
	 */
	private boolean isToggle;

	public SettingView(Context context) {
		// super(context);
		// TODO Auto-generated constructor stub
		this(context, null);
	}

	public SettingView(Context context, AttributeSet attrs) {
		// super(context, attrs);
		// TODO Auto-generated constructor stub
		this(context, attrs, 0);
	}

	public SettingView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		// TODO Auto-generated constructor stub
		initView();
		TypedArray mTa = context.obtainStyledAttributes(attrs,
				R.styleable.StringView);
		String title = mTa.getString(R.styleable.StringView_title);
		int settingBackground = mTa.getInt(
				R.styleable.StringView_settingBackground, 0);
		boolean Istoggle = mTa
				.getBoolean(R.styleable.StringView_istoggle, true);

		tv_setting_title.setText(title);

		switch (settingBackground) {
		case 0:
			view.setBackgroundResource(R.drawable.selector_settingvire_first);
			break;
		case 1:
			view.setBackgroundResource(R.drawable.selector_settingvire_middle);
			break;
		case 2:
			view.setBackgroundResource(R.drawable.selector_settingvire_last);
			break;

		default:
			view.setBackgroundResource(R.drawable.selector_settingvire_first);
			break;
		}

		im_setting_toggle.setVisibility(Istoggle ? View.VISIBLE : View.GONE);

		mTa.recycle();
	}

	/**
	 * 获取自定义控件的内容，但不设置值
	 */
	private void initView() {
		view = View.inflate(getContext(), R.layout.activity_settingview, null);
		this.addView(view);
		tv_setting_title = (TextView) view.findViewById(R.id.tv_setting_title);
		im_setting_toggle = (ImageView) view
				.findViewById(R.id.im_setting_toggle);

	}

	public void setToggle(boolean isToggle) {
		this.isToggle = isToggle;
		if (isToggle) {
			im_setting_toggle.setImageResource(R.drawable.on);

		} else {
			im_setting_toggle.setImageResource(R.drawable.off);

		}
	}

	public boolean getToggle() {
		return isToggle;
	}

	public void toToggle() {
		setToggle(!isToggle);
	}

}
