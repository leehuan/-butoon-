package com.wunyun.myapplication;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.widget.TextView;
import android.widget.Toast;

import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;
import com.wuyun.myapplication.utils.PackageCodeAndNames;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;

public class Splashctivity extends Activity {

    private static final String url = "http://169.254.252.1:8080/update.html";
    private String verSion;
    private TextView splashversion;
    private String msg;
    private String apkurl;
    private int code;
    private HttpUtils httpUtils;
    private ProgressDialog progressDialog;
    private int PACKAGE_INSTALL_CODE = 30;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splashctivity);
        initViews();

    }

    /**
     * 初始化控件
     */
    private void initViews() {


        splashversion = (TextView) findViewById(R.id.splash_tv_version);


        splashversion.setText("版本:" + PackageCodeAndNames.getPackageName(this));
        //设置一个计时器
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                checkVersion();
            }
        }, 3000);


    }

    /**
     * 进去主界面
     */
    private void enterHome() {
        Intent it = new Intent(this, MainActivity.class);
        startActivity(it);
        finish();
    }

    /**
     * 链接服务区，检查版本是否一致
     */
    private void checkVersion() {
        httpUtils = new HttpUtils(3000);

        httpUtils.send(HttpRequest.HttpMethod.GET, url, new RequestCallBack<String>() {

            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                String result = responseInfo.result;
                Toast.makeText(Splashctivity.this, "contaion successful", 0).show();
                processJson(result);
            }

            @Override
            public void onFailure(HttpException e, String s) {
                Toast.makeText(Splashctivity.this, "无法连接到服务器，请检查您的网络", 0).show();
                enterHome();
            }

            //解析json对象

        });
    }

    /**
     * 解析json对象，注意传值
     *
     * @param result
     */
    private void processJson(String result) {

        try {
            JSONObject jsonObject = new JSONObject(result);
            int code = jsonObject.getInt("code");
            apkurl = jsonObject.getString("apkurl");

            msg = jsonObject.getString("msg");
            if (code == PackageCodeAndNames.getPackageCode(Splashctivity.this)) {
                enterHome();
                Toast.makeText(Splashctivity.this, "版本一致", 0).show();
            } else {
                shoDialog();

            }


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 弹出对话框
     */
    private void shoDialog() {

        AlertDialog.Builder builder = new AlertDialog.Builder(Splashctivity.this);
        builder.setTitle("发现新版本");
        builder.setIcon(R.drawable.ic_launcher);
        builder.setMessage(msg);
        builder.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                enterHome();
            }
        });
        builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                downloadAPK();
            }
        });
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                enterHome();
            }
        });
        builder.show();
    }

    /**
     * 下载apk，并设置进度条
     */
    private void downloadAPK() {
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {

            progressDialog = new ProgressDialog(Splashctivity.this);
            progressDialog.setCancelable(false);
            progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
            progressDialog.show();

            httpUtils.download(apkurl, "/mnt/sdcard/safe.apk", new RequestCallBack<File>() {
                @Override
                public void onLoading(long total, long current, boolean isUploading) {
                    super.onLoading(total, current, isUploading);
                    progressDialog.setMax((int) total);
                    progressDialog.setProgress((int) current);

                }

                @Override
                public void onSuccess(ResponseInfo<File> responseInfo) {
                    installApk();
                    progressDialog.dismiss();
                }

                @Override
                public void onFailure(HttpException e, String s) {
                    Toast.makeText(Splashctivity.this, "服务器被炸，稍后尝试", 0).show();
                    enterHome();
                }
            });
        } else {
            Toast.makeText(Splashctivity.this, "没用可用的sd卡", 0).show();
        }
    }

    /**
     * 使用隐式意图来安装软件
     */
    private void installApk() {
        /**
         *  <intent-filter>
         <action android:name="android.intent.action.VIEW" />
         <category android:name="android.intent.category.DEFAULT" />
         <data android:scheme="content" />
         <data android:scheme="file" />
         <data android:mimeType="application/vnd.android.package-archive" />
         </intent-filter>
         */

        Intent it = new Intent();
        it.setAction("android.intent.action.VIEW");
        it.addCategory("android.intent.category.DEFAULT");
        it.setDataAndType(Uri.fromFile(new File("/mnt/sdcard/safe.apk")), "application/vnd.android.package-archive");
        startActivityForResult(it, PACKAGE_INSTALL_CODE);


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        enterHome();

    }
}
