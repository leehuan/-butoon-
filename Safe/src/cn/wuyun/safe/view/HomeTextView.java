package cn.wuyun.safe.view;

import android.content.Context;
import android.graphics.Rect;
import android.text.TextUtils.TruncateAt;
import android.util.AttributeSet;
import android.widget.TextView;

public class HomeTextView extends TextView {

	public HomeTextView(Context context) {
		// super(context);
		// TODO Auto-generated constructor stub
		this(context, null);
	}

	public HomeTextView(Context context, AttributeSet attrs) {
		// super(context, attrs);
		// TODO Auto-generated constructor stub
		this(context, attrs, 0);
	}

	/**
	 * 继承了textview，所以可以在参数为3中设置layout中的参数
	 * 
	 * 
	 * 
	 */
	public HomeTextView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		// TODO Auto-generated constructor stub
		/**
		 * android:focusable="true" android:focusableInTouchMode="true"
		 * android:singleLine="true" android:ellipsize="marquee"
		 * android:marqueeRepeatLimit="marquee_forever"
		 */
		setFocusable(true);
		setFocusableInTouchMode(true);
		setSingleLine();
		setEllipsize(TruncateAt.MARQUEE);
		setMarqueeRepeatLimit(-1);

	}

	@Override
	public boolean isFocused() {
		// TODO Auto-generated method stub
		return true;
	}

	/**
	 * 当焦点改变时会调用此方法，使用IF语句进行判断，如果焦点改变Focused为false，然后对继承体系
	 * 进行if语句判断，这样就可以阻止失去焦点，所以焦点就一直都会在textview身上
	 */
	@Override
	protected void onFocusChanged(boolean focused, int direction,
			Rect previouslyFocusedRect) {
		// TODO Auto-generated method stub
		if (focused) {
			super.onFocusChanged(focused, direction, previouslyFocusedRect);
		}
	}
	/**
	 * 当窗口的焦点改变时
	 */
	@Override
	public void onWindowFocusChanged(boolean hasWindowFocus) {
		// TODO Auto-generated method stub
		if (hasWindowFocus) {
			super.onWindowFocusChanged(hasWindowFocus);
		}
	}
}
