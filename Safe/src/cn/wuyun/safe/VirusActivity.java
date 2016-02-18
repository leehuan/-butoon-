package cn.wuyun.safe;

import java.util.ArrayList;
import java.util.List;

import org.w3c.dom.ls.LSException;

import com.github.lzyzsd.circleprogress.ArcProgress;

import cn.wuyun.safe.Utils.MD5Utils;
import cn.wuyun.safe.bean.AppInfo;
import cn.wuyun.safe.bean.AppVirusBean;
import cn.wuyun.safe.bean.CacheBean;
import cn.wuyun.safe.dao.db.VirusDao;
import cn.wuyun.safe.view.Appmarger;

import android.animation.Animator;
import android.animation.Animator.AnimatorListener;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.content.pm.PackageManager.NameNotFoundException;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class VirusActivity extends Activity {
	private ListView listview;
	private PackageManager pm;
	private List<ApplicationInfo> packges;
	private List<AppVirusBean> list;
	private Button virus;
	private ArcProgress progress;
	private TextView antivirus;
	private MyAdapter myAdapter;
	private LinearLayout progressllout;
	private RelativeLayout againscan;
	private int count;
	private AppVirusBean deleteAntivirusInfo;
	private TextView title;
	private UninstallReceiver uninstallReceiver;
	private LinearLayout mllprogress;
	private ImageView left;
	private ImageView right;
	private int width;
	private RelativeLayout mllscan;
	private LinearLayout mlimage;

	private class UninstallReceiver extends BroadcastReceiver {

		@Override
		public void onReceive(Context context, Intent intent) {
			// TODO Auto-generated method stub
			list.remove(deleteAntivirusInfo);
			myAdapter.notifyDataSetChanged();
			count--;
			if (count > 0) {
				title.setText("您的手机中有" + count + "个病毒,请及时清理");
			} else {
				title.setText("您的手机很安全");
			}
		}

	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_virus);
		initView();
		IntentFilter filter = new IntentFilter();
		filter.addAction(Intent.ACTION_PACKAGE_REMOVED);
		filter.addDataScheme("package");
		filter.setPriority(1000);
		uninstallReceiver = new UninstallReceiver();
		registerReceiver(uninstallReceiver, filter);

	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		unregisterReceiver(uninstallReceiver);
	}

	private void initView() {
		listview = (ListView) findViewById(R.id.lv_virus_listview);
		virus = (Button) findViewById(R.id.bt_virus_bt);
		progress = (ArcProgress) findViewById(R.id.arc_progress);
		antivirus = (TextView) findViewById(R.id.tv_antivirus_packagename);
		progressllout = (LinearLayout) findViewById(R.id.ll_antivirus_progressbar);
		againscan = (RelativeLayout) findViewById(R.id.Rl_title_again_scan);
		title = (TextView) findViewById(R.id.tv_virus_title);
		mllprogress = (LinearLayout) findViewById(R.id.ll_antivirus_progressbar);
		left = (ImageView) findViewById(R.id.iv_antivirust_left);
		right = (ImageView) findViewById(R.id.iv_antivirust_right);
		mllscan = (RelativeLayout) findViewById(R.id.Rl_title_again_scan);
		mlimage = (LinearLayout) findViewById(R.id.ll_antivrus_imageview);
		scan();

	}

	/**
	 * 扫描 packges:获得所有的安装的应用程序
	 */
	private void scan() {
		// TODO Auto-generated method stub

		count = 0;

		pm = getPackageManager();

		list = new ArrayList<AppVirusBean>();
		virus.setEnabled(false);
		mllprogress.setVisibility(View.VISIBLE);
		againscan.setVisibility(View.GONE);
		mllscan.setVisibility(View.GONE);
		mlimage.setVisibility(View.GONE);
		new Thread() {

			public void run() {
				packges = pm
						.getInstalledApplications(PackageManager.GET_SIGNATURES);

				int max = packges.size();
				int pro = 0;

				for (final ApplicationInfo pack : packges) {
					SystemClock.sleep(150);
					pro++;
					final int surprogress = (int) (pro * 100f / max + 0.5f);
					runOnUiThread(new Runnable() {
						public void run() {
							progress.setProgress(surprogress);
							antivirus.setText(pack.packageName);

						}
					});
					try {
						ApplicationInfo applicationInfo = pm
								.getApplicationInfo(pack.packageName, 0);
						Drawable icon = applicationInfo.loadIcon(pm);
						String name = applicationInfo.loadLabel(pm).toString();
						AppVirusBean appvirus = new AppVirusBean();
						appvirus.name = name;
						appvirus.icon = icon;
						appvirus.packageName = pack.packageName;
						// 通过packmanger中的getpackgetInfo来获取软件的标示
						PackageInfo packageInfo = pm
								.getPackageInfo(pack.packageName,
										PackageManager.GET_SIGNATURES);

						Signature[] singatures = packageInfo.signatures;
						String charsstring = singatures[0].toCharsString();
						String md5 = MD5Utils.MD5UtilsPassword(charsstring);
						boolean virus2 = VirusDao.isVirus(
								getApplicationContext(), md5);
						if (virus2) {
							list.add(0, appvirus);
							count++;
							appvirus.isAntivirus = true;

						} else {
							appvirus.isAntivirus = false;
							list.add(appvirus);
						}

					} catch (NameNotFoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					runOnUiThread(new Runnable() {

						public void run() {
							if (myAdapter == null) {
								myAdapter = new MyAdapter();
								listview.setAdapter(myAdapter);
							} else {
								myAdapter.notifyDataSetChanged();

							}

							listview.smoothScrollToPosition(myAdapter
									.getCount() - 1);
						}
					});
					runOnUiThread(new Runnable() {
						public void run() {
							if (list.size() == packges.size()) {
								listview.smoothScrollToPosition(0);
								virus.setEnabled(true);
								progressllout.setVisibility(View.GONE);
								againscan.setVisibility(View.VISIBLE);
								mllscan.setVisibility(View.VISIBLE);
								// 图片的布局显示
								mlimage.setVisibility(View.VISIBLE);

								if (count > 0) {
									title.setText("您的手机中有" + count
											+ "个病毒,请及时清理");
								} else {
									title.setText("您的手机很安全");
								}
								// mlimage.setVisibility(View.GONE);
							}
							startAnimation();
							// 获取到了原来进度条的副本，同时将副本图片进行分割，创建出两个
							mllprogress.setDrawingCacheEnabled(true);
							mllprogress
									.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);
							Bitmap drawingCache = mllprogress.getDrawingCache();

							Bitmap leftmap = getLeftmap(drawingCache);
							Bitmap getrightmap = getrightmap(drawingCache);

							left.setImageBitmap(leftmap);
							right.setImageBitmap(getrightmap);

							virus.setOnClickListener(new OnClickListener() {

								@Override
								public void onClick(View v) {
									// TODO Auto-generated method stub
									virus.setClickable(false);
									backAnimation();
								}

							});
						}

					});
				}

			};

		}.start();
	}

	private void startAnimation() {
		// TODO Auto-generated method stub
		AnimatorSet animatorSet = new AnimatorSet();
		animatorSet.playTogether(
				ObjectAnimator.ofFloat(left, "translationX", 0, -width),
				ObjectAnimator.ofFloat(right, "translationX", 0, width),
				ObjectAnimator.ofFloat(left, "alpha", 1, 0),
				ObjectAnimator.ofFloat(right, "alpha", 1, 0),
				ObjectAnimator.ofFloat(mllscan, "alpha", 0, 1));
		animatorSet.setDuration(3000);
		animatorSet.start();
	}

	private void backAnimation() {
		// TODO Auto-generated method stub
		AnimatorSet animatorSet = new AnimatorSet();
		animatorSet.playTogether(
				ObjectAnimator.ofFloat(left, "translationX", -width, 0),
				ObjectAnimator.ofFloat(right, "translationX", width, 0),
				ObjectAnimator.ofFloat(left, "alpha", 0, 1),
				ObjectAnimator.ofFloat(right, "alpha", 0, 1),
				ObjectAnimator.ofFloat(mllscan, "alpha", 1, 0));

		animatorSet.addListener(new AnimatorListener() {

			@Override
			public void onAnimationStart(Animator animation) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onAnimationRepeat(Animator animation) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onAnimationEnd(Animator animation) {
				// TODO Auto-generated method stub
				scan();
			}

			@Override
			public void onAnimationCancel(Animator animation) {
				// TODO Auto-generated method stub

			}
		});

		animatorSet.setDuration(2000);
		animatorSet.start();
	}

	private Bitmap getLeftmap(Bitmap getleft) {

		width = (int) (getleft.getWidth() / 2 + 0.5f);
		int height = getleft.getHeight();

		Bitmap createBitmap = Bitmap.createBitmap(width, height,
				getleft.getConfig());

		Canvas canvas = new Canvas(createBitmap);
		Paint paint = new Paint();
		Matrix matrix = new Matrix();
		canvas.drawBitmap(getleft, matrix, paint);

		return createBitmap;

	}

	private Bitmap getrightmap(Bitmap getleft) {

		width = (int) (getleft.getWidth() / 2 + 0.5f);
		int height = getleft.getHeight();

		Bitmap createBitmap = Bitmap.createBitmap(width, height,
				getleft.getConfig());

		Canvas canvas = new Canvas(createBitmap);
		Paint paint = new Paint();
		Matrix matrix = new Matrix();
		matrix.setTranslate(-width, 0);

		canvas.drawBitmap(getleft, matrix, paint);

		return createBitmap;

	}

	private class MyAdapter extends BaseAdapter {

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return list.size();
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return list.get(position);
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub
			ViewHolder viewholder;
			if (convertView == null) {

				convertView = View.inflate(getApplicationContext(),
						R.layout.cache_listview, null);

				viewholder = new ViewHolder();

				viewholder.iv_cache_img = (ImageView) convertView
						.findViewById(R.id.iv_cache_img);
				viewholder.tv_cache_title = (TextView) convertView
						.findViewById(R.id.tv_cache_title);
				viewholder.tv_cache_sd = (TextView) convertView
						.findViewById(R.id.tv_cache_sd);
				viewholder.iv_cache_clear_bt = (ImageView) convertView
						.findViewById(R.id.iv_cache_clear_bt);

				convertView.setTag(viewholder);
			} else {
				viewholder = (ViewHolder) convertView.getTag();
			}

			final AppVirusBean appVirusBean = list.get(position);
			viewholder.iv_cache_img.setImageDrawable(appVirusBean.icon);
			viewholder.tv_cache_title.setText(appVirusBean.packageName);
			if (appVirusBean.isAntivirus) {
				viewholder.tv_cache_sd.setText("病毒");
				viewholder.tv_cache_sd.setTextColor(Color.RED);
				viewholder.iv_cache_clear_bt.setVisibility(View.VISIBLE);
			} else {
				viewholder.tv_cache_sd.setText("安全");

				viewholder.tv_cache_sd.setTextColor(Color.GREEN);
				viewholder.iv_cache_clear_bt.setVisibility(View.GONE);
			}
			viewholder.iv_cache_clear_bt
					.setOnClickListener(new OnClickListener() {

						@Override
						public void onClick(View v) {
							// TODO Auto-generated method stub
							// 卸载操作
							Intent intent = new Intent();
							intent.setAction("android.intent.action.DELETE");
							intent.addCategory("android.intent.category.DEFAULT");
							intent.setData(Uri.parse("package:"
									+ appVirusBean.packageName));
							startActivity(intent);
							// 执行卸载操作，将卸载应用的信息保存到标示中

							// TODO 广播进行卸载应用程序操作

							deleteAntivirusInfo = appVirusBean;
						}
					});
			return convertView;
		}

	}

	class ViewHolder {
		ImageView iv_cache_img, iv_cache_clear_bt;
		TextView tv_cache_title, tv_cache_sd;

	}
}
