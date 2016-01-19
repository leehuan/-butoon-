package cn.wuyun.safe;

import android.app.Activity;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

public abstract class BaseLastFindSetup extends Activity {

	private GestureDetector gestureDetector;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		gestureDetector = new GestureDetector(this, new MyGestureDetector());
		super.onCreate(savedInstanceState);
	}

	class MyGestureDetector extends SimpleOnGestureListener {

		@Override
		public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
				float velocityY) {
			// TODO Auto-generated method stub
			int downX = (int) e1.getX();
			int upX = (int) e2.getX();
			int y = (int) e1.getY();
			int y2 = (int) e2.getY();
			if (Math.abs(y - y2) > 50) {
				Toast.makeText(BaseLastFindSetup.this, "你小子又乱划！", 0).show();
				return false;
			}

			if (upX - downX > 50) {
				pre();

			} else if (upX - downX < 50) {
				next();
			}

			return super.onFling(e1, e2, velocityX, velocityY);
		}

	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		// TODO Auto-generated method stub
		gestureDetector.onTouchEvent(event);
		return super.onTouchEvent(event);
	}

	public void next(View v) {
		next();
	}

	public void pre(View v) {
		pre();
	}

	public abstract boolean nextEvent();

	public abstract boolean preEvent();

	private void next() {
		if (nextEvent()) {
			return;
		}

		finish();
		overridePendingTransition(R.anim.setup_next_enter,
				R.anim.setup_next_out);

	}

	private void pre() {
		if (preEvent()) {
			return;
		}

		finish();
		overridePendingTransition(R.anim.seup_pre_enter, R.anim.seup_pre_out);
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		if (keyCode == event.KEYCODE_BACK) {
			pre();

		}

		return super.onKeyDown(keyCode, event);
	}
}
