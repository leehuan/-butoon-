package cn.wuyun.safe;

import java.util.List;

import cn.wuyun.safe.bean.SelectSafeAddListInfo;
import cn.wuyun.safe.engine.SelectSafeNumberEngine;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.ActionMode;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class SelectSafeNumberActivity extends Activity implements
		OnItemClickListener {

	private static final int RETURNPHONE = 10;
	private ListView selectsafenumber;
	private List<SelectSafeAddListInfo> allContans;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.listview_selectsaenumber);
		filldate();
		initView();
	}

	private void filldate() {
		new Thread(){
			@Override
			public void run() {
				// TODO Auto-generated method stub
				allContans = SelectSafeNumberEngine
						.getAllContans(getApplicationContext());
				super.run();
				runOnUiThread(new Runnable() {
					
					@Override
					public void run() {
						// TODO Auto-generated method stub
						selectsafenumber.setAdapter(new MyAdapter());
						
					}
				});
			}
			
		}.start();

	}

	private void initView() {
		selectsafenumber = (ListView) findViewById(R.id.lv_selectsafenumber);

		selectsafenumber.setOnItemClickListener(this);


	}

	class MyAdapter extends BaseAdapter {
		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return allContans.size();
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return allContans.get(position);
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return position;
		}

		// TODO 无法将表完整的显示到里面
		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub

			View view = new View(SelectSafeNumberActivity.this);
			ViewHelder ViewHelder = new ViewHelder();
			if (convertView == null) {
				view = View.inflate(SelectSafeNumberActivity.this,
						R.layout.listview_select, null);
				ViewHelder.mimg = (ImageView) view
						.findViewById(R.id.iv_listview_select);
				ViewHelder.mname = (TextView) view

				.findViewById(R.id.tv_listview_name);

				ViewHelder.mnumber = (TextView) view
						.findViewById(R.id.tv_listview_number);
				view.setTag(ViewHelder);
			} else {

				view = convertView;
				ViewHelder = (ViewHelder) view.getTag();
			}

			SelectSafeAddListInfo listInfo = allContans.get(position);

			ViewHelder.mname.setText(listInfo.name);
			ViewHelder.mnumber.setText(listInfo.number);
			Bitmap bitmap = SelectSafeNumberEngine.getContansIcon(
					getApplicationContext(), listInfo.Icon);
			ViewHelder.mimg.setImageBitmap(bitmap);

			return view;
		}

	}

	class ViewHelder {
		ImageView mimg;
		TextView mname;
		TextView mnumber;
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int position,
			long arg3) {
		// TODO Auto-generated method stub

		Intent it = new Intent();
		it.putExtra("number", allContans.get(position).number);
		setResult(Activity.RESULT_OK, it);

		finish();

	}
}
