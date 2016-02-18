package cn.wuyun.safe.view;

import cn.wuyun.safe.R;
import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

public class Appmarger extends LinearLayout {
	private TextView title;
	private TextView user;
	private TextView total;
	private ProgressBar pro;

	public Appmarger(Context context) {
		
		// TODO Auto-generated constructor stub
		this(context, null);
	}

	public Appmarger(Context context, AttributeSet attrs) {
		
		// TODO Auto-generated constructor stub
		this(context, attrs, 0);
	}

	public Appmarger(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		initView();
		// TODO Auto-generated constructor stub
	}

	private void initView() {
		// TODO Auto-generated method stub
		View view = View.inflate(getContext(), R.layout.sofaware_pro, this);

		title = (TextView) view.findViewById(R.id.tv_appsofaware_titile);
		user = (TextView) view.findViewById(R.id.tv_appmanger_used);
		total = (TextView) view.findViewById(R.id.tv_appmanger_free);
		pro = (ProgressBar) view.findViewById(R.id.progressBar1);

	}

	public void title(String inver) {
		title.setText(inver);
	}

	public void userd(String userd) {
		user.setText(userd);
	}

	public void totaling(String totaling) {
		total.setText(totaling);
	}

	public void prototal(int proing) {
		pro.setProgress(proing);
	}

}
