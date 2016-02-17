package com.wunyun.myapplication;

import android.animation.ObjectAnimator;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.wuyun.myapplication.bean.MainItemInfo;
import com.wuyun.myapplication.utils.Contants;
import com.wuyun.myapplication.utils.SharedPreferenceUtils;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends Activity implements AdapterView.OnItemClickListener, View.OnClickListener {

    private final String[] TITLES = new String[]{"手机防盗", "骚扰拦截", "软件管家",
            "进程管理", "流量统计", "手机杀毒", "缓存清理", "常用工具"};
    private final String[] DESCS = new String[]{"远程定位手机", "全面拦截骚扰", "管理您的软件",
            "管理运行进程", "流量一目了然", "病毒无处藏身", "系统快如火箭", "工具大全"};
    private final int[] ICONS = new int[]{R.drawable.sjfd, R.drawable.srlj,
            R.drawable.rjgj, R.drawable.jcgl, R.drawable.lltj, R.drawable.sjsd,
            R.drawable.hcql, R.drawable.szzx};
    private ImageView log;
    private GridView gvlist;
    private List<MainItemInfo> list;
    private ImageView setting;
    private AlertDialog alertDialog;
    private EditText testpassword;
    private AlertDialog build;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
    }

    private void initViews() {

        log = (ImageView) findViewById(R.id.main_iv_log);
        gvlist = (GridView) findViewById(R.id.main_gv_list);
        setting = (ImageView) findViewById(R.id.mian__iv_setting);

        setting.setOnClickListener(this);

        logAnimation();
        filldata();
        gvlist.setOnItemClickListener(this);

    }

    private void filldata() {
        getList();
        MyAdapter myAdapter = new MyAdapter();
        gvlist.setAdapter(myAdapter);

    }

    private void getList() {
        list = new ArrayList<MainItemInfo>();
        for (int i = 0; i < TITLES.length; i++) {
            MainItemInfo mainItemInfo = new MainItemInfo();
            mainItemInfo.img = ICONS[i];
            mainItemInfo.msg = DESCS[i];
            mainItemInfo.title = TITLES[i];
            list.add(mainItemInfo);


        }

    }


    private void logAnimation() {

        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(log, "rotationY", 0, 90f, 270f, 360f);
        objectAnimator.setRepeatMode(ObjectAnimator.RESTART);
        objectAnimator.setRepeatCount(ObjectAnimator.INFINITE);
        objectAnimator.setDuration(3000);
        objectAnimator.start();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        switch (position) {
            case 0:
                String password = SharedPreferenceUtils.getString(getApplicationContext(), Contants.PASSWORD, "");
                if (TextUtils.isEmpty(password)) {
                    initPassword();

                } else {
                    testing();
                }
                break;

            case 1:
                break;
            case 2:
                break;
            case 3:
                break;
            case 4:
                break;
            case 5:
                break;
            case 6:
                break;
            case 7:

                break;
        }
    }


    private void testing() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);

        View view = View.inflate(getApplicationContext(), R.layout.main_testingpassword, null);
        final EditText testpassword = (EditText) view.findViewById(R.id.et_input_test_password);
        Button mOk = (Button) view.findViewById(R.id.bt_test_config);
        Button Mconcanl = (Button) view.findViewById(R.id.bt_test_concanl);
        mOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String password = testpassword.getText().toString().trim();
                if (TextUtils.isEmpty(password)) {
                    Toast.makeText(getApplicationContext(), "请输入密码", Toast.LENGTH_SHORT).show();
                    return;
                }
                String inputpassword = SharedPreferenceUtils.getString(getApplicationContext(), Contants.PASSWORD, "");

                if (TextUtils.equals(password, inputpassword)) {
                    goSetup();
                    build.dismiss();
                } else {
                    Toast.makeText(getApplicationContext(), "输入密码错误!", Toast.LENGTH_SHORT).show();
                    return;
                }


            }
        });
        Mconcanl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                build.dismiss();
            }
        });

        builder.setView(view);
        build = builder.create();
        build.show();

    }

    private void goSetup() {

        startActivity(new Intent(MainActivity.this, SetUpActivity.class));

    }


    private void initPassword() {
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        View view = View.inflate(this, R.layout.main_setuppassword, null);
        final EditText password = (EditText) view.findViewById(R.id.et_input_password);
        final EditText agpassword = (EditText) view.findViewById(R.id.et_input_agpassword);
        Button config = (Button) view.findViewById(R.id.bt_config);
        Button concanl = (Button) view.findViewById(R.id.bt_concanl);

        config.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String firstpassword = password.getText().toString().trim();
                String secondagpassword = agpassword.getText().toString().trim();


                if (TextUtils.isEmpty(firstpassword) || TextUtils.isEmpty(secondagpassword)) {
                    Toast.makeText(MainActivity.this, "密码不能为空", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.equals(firstpassword, secondagpassword)) {
                    SharedPreferenceUtils.saveString(getApplicationContext(), Contants.PASSWORD, firstpassword);
                    Toast.makeText(MainActivity.this, "设置成功", Toast.LENGTH_SHORT).show();
                    alertDialog.dismiss();

                } else {
                    Toast.makeText(MainActivity.this, "两次密码不一致", Toast.LENGTH_SHORT).show();

                }

            }
        });


        concanl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });


        builder.setView(view);
        alertDialog = builder.create();
        alertDialog.show();

    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.mian__iv_setting:
                startActivity(new Intent(MainActivity.this, SettingActivity.class));

        }
    }

    class MyAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public Object getItem(int position) {
            return list.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            ViewHolder viewHolder;
            if (convertView == null) {

                convertView = View.inflate(MainActivity.this, R.layout.main_gvitem, null);
                viewHolder = new ViewHolder();
                viewHolder.main_tv_item_msg = (TextView) convertView.findViewById(R.id.main_tv_item_msg);
                viewHolder.main_tv_item_title = (TextView) convertView.findViewById(R.id.main_tv_item_title);
                viewHolder.mian_iv_gv_item_img = (ImageView) convertView.findViewById(R.id.mian_iv_gv_item_img);
                convertView.setTag(viewHolder);

            } else {
                viewHolder = (ViewHolder) convertView.getTag();

            }
            MainItemInfo mainItemInfo = list.get(position);
            viewHolder.mian_iv_gv_item_img.setImageResource(mainItemInfo.img);
            viewHolder.main_tv_item_title.setText(mainItemInfo.title);
            viewHolder.main_tv_item_msg.setText(mainItemInfo.msg);


            return convertView;
        }
    }

    class ViewHolder {
        TextView main_tv_item_title, main_tv_item_msg;
        ImageView mian_iv_gv_item_img;
    }
}
