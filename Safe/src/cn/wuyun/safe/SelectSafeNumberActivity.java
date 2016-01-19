package cn.wuyun.safe;

import java.util.List;

import cn.wuyun.safe.bean.SelectSafeAddListInfo;
import cn.wuyun.safe.engine.SelectSafeNumberEngine;
import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.ActionMode;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class SelectSafeNumberActivity extends Activity {

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
		allContans = SelectSafeNumberEngine
				.getAllContans(getApplicationContext());

	}

	private void initView() {
		selectsafenumber = (ListView) findViewById(R.id.lv_selectsafenumber);
		selectsafenumber.setAdapter(new MyAdapter());

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
			
			if (convertView == null) {

				convertView = convertView.inflate(
						SelectSafeNumberActivity.this,
						R.layout.listview_select, null);
			}
			ImageView mimg = (ImageView) convertView
					.findViewById(R.id.iv_listview_select);
			TextView mname = (TextView) convertView
					.findViewById(R.id.tv_listview_name);

			TextView mnumber = (TextView) convertView
					.findViewById(R.id.tv_listview_number);

			SelectSafeAddListInfo listInfo = allContans.get(position);

			mname.setText(listInfo.name);
			mnumber.setText(listInfo.number);
			Bitmap bitmap = SelectSafeNumberEngine.getContansIcon(
					getApplicationContext(), listInfo.Icon);
			mimg.setImageBitmap(bitmap);

			return convertView;
		}

	}

	class   Box {
		ImageView mimg;
		TextView mname;
		TextView mnumber;
	}
}
