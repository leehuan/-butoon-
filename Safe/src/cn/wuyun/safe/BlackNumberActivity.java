package cn.wuyun.safe;

import java.util.List;

import cn.wuyun.safe.SelectSafeNumberActivity.ViewHelder;
import cn.wuyun.safe.bean.BlackNumberBean;
import cn.wuyun.safe.dao.BlackNumberContants;
import cn.wuyun.safe.dao.db.BlackNumberMethodDao;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

public class BlackNumberActivity extends Activity implements OnClickListener,
		OnItemClickListener {
	private static final int BLACKNUMBER_ADD_CODE = 100;
	private static final int BLACKNUMBER_UPDATE_CODE = 20;
	protected static int StartNumber = 0;
	protected static final int MAXNUMBER = 20;
	private ImageView icon;
	private ListView list;
	private List<BlackNumberBean> selectAllNumber;
	private BlackNumberMethodDao blackdao;
	private MyAdapter myAdapter;
	private LinearLayout loading;
	private ImageView img;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_balcknumber);
		initView();

	}

	private void initView() {
		icon = (ImageView) findViewById(R.id.iv_blacknumber_view_bt_icon);
		list = (ListView) findViewById(R.id.lv_blacknumber_list);
		loading = (LinearLayout) findViewById(R.id.loading);
		img = (ImageView) findViewById(R.id.lv_blacknumber_img);
		icon.setOnClickListener(this);
		filldate();
		itemOnClickUpdate();
		setOnScollec();
	}

	private void setOnScollec() {
		// TODO Auto-generated method stub
		list.setOnScrollListener(new OnScrollListener() {

			@Override
			public void onScroll(AbsListView view, int firstVisibleItem,
					int visibleItemCount, int totalItemCount) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onScrollStateChanged(AbsListView view, int scrollState) {
				// TODO Auto-generated method stub
				if (scrollState == OnScrollListener.SCROLL_STATE_IDLE) {
					int lastVisiblePosition = list.getLastVisiblePosition();
					if (lastVisiblePosition == selectAllNumber.size() - 1) {
						StartNumber += MAXNUMBER;
						filldate();
					}
				}
			}
		});
	}

	private void itemOnClickUpdate() {
		// TODO Auto-generated method stub
		list.setOnItemClickListener(this);
	}

	private void filldate() {
		loading.setVisibility(View.VISIBLE);
		blackdao = new BlackNumberMethodDao(getApplicationContext());
		new Thread() {
			@Override
			public void run() {
				// TODO Auto-generated method stub
				if (selectAllNumber == null) {
					selectAllNumber = blackdao.selectAllNumber(MAXNUMBER,
							StartNumber);
				} else {
					selectAllNumber.addAll(blackdao.selectAllNumber(MAXNUMBER,
							StartNumber));
				}
				runOnUiThread(new Runnable() {

					@Override
					public void run() {
						if (myAdapter == null) {
							myAdapter = new MyAdapter();
							list.setAdapter(myAdapter);

						} else {
							myAdapter.notifyDataSetChanged();
						}
						list.setEmptyView(img);
						loading.setVisibility(View.GONE);
					}
				});
				super.run();

			}

		}.start();

	}

	class MyAdapter extends BaseAdapter {

		private TextView modeview;
		private TextView number;

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			System.out.println(selectAllNumber.size());
			return selectAllNumber.size();
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			System.out.println(selectAllNumber.get(position));
			return selectAllNumber.get(position);
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return position;
		}

		@Override
		public View getView(final int position, View convertView,
				ViewGroup parent) {
			// TODO Auto-generated method stub
			View view;
			viewHeloder viewheloder;
			if (convertView == null) {
				view = View.inflate(getApplicationContext(),
						R.layout.blacknumber_item, null);
				viewheloder = new viewHeloder();
				viewheloder.modeview = (TextView) view
						.findViewById(R.id.tv_black_mode);
				viewheloder.number = (TextView) view
						.findViewById(R.id.tv_black_number);
				viewheloder.delete = (ImageView) view
						.findViewById(R.id.iv_blacknumber_img_delete);
				view.setTag(viewheloder);
			} else {
				view = convertView;
				viewheloder = (viewHeloder) view.getTag();
			}

			final BlackNumberBean blackNumberBean = selectAllNumber
					.get(position);

			viewheloder.number.setText(blackNumberBean.blacknumber);

			int mode2 = blackNumberBean.mode;
			System.out.println(mode2);
			switch (mode2) {
			case BlackNumberContants.BLACKNUMBER_MODE_PHONE:

				viewheloder.modeview.setText("电话拦截");
				break;
			case BlackNumberContants.BLACKNUMBER_MODE_SMS:

				viewheloder.modeview.setText("短信拦截");
				break;
			case BlackNumberContants.BLACKNUMBER_MODE_ALL:

				viewheloder.modeview.setText("全部拦截");
				break;

			}
			viewheloder.delete.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					AlertDialog.Builder builder = new Builder(
							BlackNumberActivity.this);
					builder.setMessage("您是否要删除黑名单"
							+ blackNumberBean.blacknumber);
					builder.setPositiveButton("确定",
							new DialogInterface.OnClickListener() {

								@Override
								public void onClick(DialogInterface dialog,
										int which) {
									// TODO Auto-generated method stub
									boolean delectNumber = blackdao
											.delectNumber(blackNumberBean.blacknumber);
									if (delectNumber) {
										selectAllNumber.remove(position);
										myAdapter.notifyDataSetChanged();
									}

								}
							});
					builder.setNegativeButton("取消", null);

					builder.show();
				}
			});

			return view;
		}

	}

	static class viewHeloder {
		TextView modeview, number;
		ImageView delete;
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		int id = v.getId();
		switch (id) {
		case R.id.iv_blacknumber_view_bt_icon:
			goAddBlackNumer();
			break;

		default:
			break;
		}
	}

	private void goAddBlackNumer() {
		// TODO Auto-generated method stub
		Intent it = new Intent(this, AddBlackNumberActitvity.class);
		startActivityForResult(it, BLACKNUMBER_ADD_CODE);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == BLACKNUMBER_ADD_CODE) {
			switch (resultCode) {
			case Activity.RESULT_OK:
				if (data != null) {
					String number = data.getStringExtra("number");
					int mode = data.getIntExtra("mode", 0);
					BlackNumberBean blackbean = new BlackNumberBean(number,
							mode);
					selectAllNumber.add(0, blackbean);
					myAdapter.notifyDataSetChanged();
				}
				break;

			default:
				break;
			}

		} else if (requestCode == BLACKNUMBER_UPDATE_CODE) {
			switch (resultCode) {
			case Activity.RESULT_OK:
				if (data != null) {
					int mode = data.getIntExtra("mode", 0);
					int position = data.getIntExtra("position", 0);
					selectAllNumber.get(position).mode = mode;
					myAdapter.notifyDataSetChanged();
				}

				break;

			default:
				break;
			}

		}

	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		// TODO Auto-generated method stub
		Intent it = new Intent(BlackNumberActivity.this,
				AddBlackNumberActitvity.class);
		it.setAction("update");
		it.putExtra("number", selectAllNumber.get(position).blacknumber);
		it.putExtra("mode", selectAllNumber.get(position).mode);
		it.putExtra("position", position);
		startActivityForResult(it, BLACKNUMBER_UPDATE_CODE);
	}
}
