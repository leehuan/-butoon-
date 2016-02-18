package cn.wuyun.safe.view;

import cn.wuyun.safe.R;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager.LayoutParams;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ListView;

public class ChangeBgDialog extends Dialog {

	private ListView mdialog;

	public ChangeBgDialog(Context context) {
		super(context, R.style.Dialogstyle);
		// TODO Auto-generated constructor stub

	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.dialog_change);
		mdialog = (ListView) findViewById(R.id.lv_address_div_dialog);

		Window window = getWindow();

		LayoutParams attributes = window.getAttributes();
		attributes.gravity = Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL;
		window.setAttributes(attributes);
	}

	public void setadapter(BaseAdapter adapter){
		mdialog.setAdapter(adapter);
	}
	public void onItemChlickOne(OnItemClickListener onItemClickListener) {
		mdialog.setOnItemClickListener(onItemClickListener);
	}

}
