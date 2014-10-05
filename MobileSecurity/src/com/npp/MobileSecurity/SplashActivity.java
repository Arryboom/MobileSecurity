package com.npp.MobileSecurity;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.widget.TextView;
import android.widget.Toast;

import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.HttpHandler;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.npp.MobileSecurity.utils.StreamTools;

public class SplashActivity extends Activity {

	private TextView tv_version;
	protected static final String tag = "SplashActivity";
	protected static final int ENTER_HOME = 0;
	protected static final int SHOW_UPDATE_DIALOG = 1;
	protected static final int JSON_ERROR = 2;
	protected static final int NETWORK_ERROR = 3;
	protected static final int URL_ERROR = 4;
	private static final String TAG = "SplashActivity";

	private String description;
	private String apkurl;
	private TextView tv_updata_info;
	private SharedPreferences sp;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.splash_acvtivity);
		tv_version = (TextView) findViewById(R.id.tv_version);
		tv_updata_info = (TextView) findViewById(R.id.tv_updata_info);
		sp = getSharedPreferences("config", MODE_PRIVATE);
		tv_version.setText("�汾��:" + getVersionName());

		AlphaAnimation aa = new AlphaAnimation(0.2f, 1.0f);
		aa.setDuration(1000);
		findViewById(R.id.rl_root_splash).setAnimation(aa);
		boolean isupdate = sp.getBoolean("update", false);
		if (isupdate) {
			// �Զ�������
			checkupdate();
		} else {
			// ���������ӳ�2�����������
			handler.postDelayed(new Runnable() {

				@Override
				public void run() {
					// TODO Auto-generated method stub
					EnterHome();

				}
			}, 2000);
		}
		copyDB("antivirus.db");
	}

	private Handler handler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			super.handleMessage(msg);
			switch (msg.what) {
			case ENTER_HOME:// ������ҳ��
				Log.i(tag, "������ҳ��");
				EnterHome();
				break;
			case SHOW_UPDATE_DIALOG:// ��ʾ����
				Toast.makeText(getApplicationContext(), "���°汾", 0).show();
				ShowUpdateDialog();
				break;
			case JSON_ERROR:// Json��������
				Log.i(tag, "Json��������");
				Toast.makeText(getApplicationContext(), "Json��������", 0).show();
				EnterHome();
				break;
			case NETWORK_ERROR:// �����쳣
				Log.i(tag, "�����쳣");
				Toast.makeText(getApplicationContext(), "�����쳣", 0).show();
				EnterHome();
				break;
			case URL_ERROR:// URL����
				Log.i(tag, "URL����");
				Toast.makeText(getApplicationContext(), "URL����", 0).show();
				EnterHome();
				break;
			default:
				break;
			}
		}

	};

	private void EnterHome() {
		// TODO Auto-generated method stub
		Intent intent = new Intent(this, HomeActivity.class);
		startActivity(intent);
		// �رյ�ǰҳ��
		finish();
	}

	protected void ShowUpdateDialog() {
		// TODO Auto-generated method stub
		AlertDialog.Builder builder = new Builder(this);
		builder.setTitle("��ʾ����");
		builder.setMessage(description);
		// builder.setCancelable(false);//ǿ��������ȡ�����ذ�ť�ʹ�����������
		builder.setOnCancelListener(new OnCancelListener() {

			@Override
			public void onCancel(DialogInterface dialog) {
				// TODO Auto-generated method stub
				// �㷵�ؽ�ȥ��ҳ��
				EnterHome();
				dialog.dismiss();

			}
		});
		builder.setPositiveButton("��������", new OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				if (Environment.getExternalStorageState().equals(
						Environment.MEDIA_MOUNTED)) {
					// SD������
					HttpUtils http = new HttpUtils();
					HttpHandler handler = http.download(apkurl, Environment
							.getExternalStorageDirectory().getAbsolutePath()
							+ "/MobileSecurity.apk", false, // ���Ŀ���ļ����ڣ�����δ��ɵĲ��ּ������ء���������֧��RANGEʱ���������ء�
							true, // ��������󷵻���Ϣ�л�ȡ���ļ�����������ɺ��Զ���������
							new RequestCallBack<File>() {

								@Override
								public void onStart() {
									// TODO Auto-generated method stub
									super.onStart();
									tv_updata_info.setVisibility(View.VISIBLE);
									tv_updata_info.setText("conn...");
								}

								@Override
								public void onLoading(long total, long current,
										boolean isUploading) {
									// ��ǰ���ذٷֱ�
									int progress = (int) (current * 100 / total);
									tv_updata_info.setText("���ؽ���" + progress
											+ "%");
								}

								@Override
								public void onSuccess(
										ResponseInfo<File> responseInfo) {
									tv_updata_info.setText("downloaded:"
											+ responseInfo.result.getPath());
									installAPK(responseInfo);
								}

								/**
								 * ��װAPK
								 * 
								 * @param responseInfo
								 */
								private void installAPK(
										ResponseInfo<File> responseInfo) {
									// TODO Auto-generated method stub
									Intent intent = new Intent();
									intent.setAction("android.intent.action.VIEW");
									intent.addCategory("android.intent.category.DEFAULT");
									intent.setDataAndType(
											Uri.fromFile(responseInfo.result),
											"application/vnd.android.package-archive");
									startActivity(intent);

								}

								@Override
								public void onFailure(HttpException error,
										String msg) {
									Toast.makeText(SplashActivity.this,
											"����ʧ����", 0).show();
								}
							});
				} else {
					// SD��������
					Toast.makeText(SplashActivity.this, "SD�������ڣ����´�����", 0)
							.show();
					return;

				}
			}
		});
		builder.setNegativeButton("�´���˵", new OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				dialog.dismiss();
				EnterHome();// ������ҳ��

			}
		});
		builder.show();

		new task().execute();

	}

	class task extends AsyncTask<String, Void, String> {

		@Override
		protected String doInBackground(String... params) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
		}

		@Override
		protected void onProgressUpdate(Void... values) {
			// TODO Auto-generated method stub
			super.onProgressUpdate(values);
		}

	}

	private void checkupdate() {
		// TODO Auto-generated method stub
		new Thread() {
			public void run() {

				Message msg = Message.obtain();
				long startTime = System.currentTimeMillis();
				URL url;
				try {
					url = new URL(getString(R.string.serviceurl));
					HttpURLConnection con = (HttpURLConnection) url
							.openConnection();
					con.setConnectTimeout(4000);
					con.setRequestMethod("GET");
					int ResponseCode = con.getResponseCode();
					if (ResponseCode == 200) {
						InputStream is = con.getInputStream();
						String result = StreamTools.readFromStream(is);
						Log.i(tag, "�����ɹ�" + result);
						// ����json,�õ��������İ汾��Ϣ
						JSONObject obj = new JSONObject(result);
						String version = (String) obj.get("version");
						description = (String) obj.get("description");
						apkurl = (String) obj.get("apkurl");

						// У���Ƿ����°汾
						if (getVersionName().equals(version)) {
							// �汾һ�£�û���°汾��ͼ��ҳ��
							msg.what = ENTER_HOME;
						} else {
							// ���°汾�������Ի���
							msg.what = SHOW_UPDATE_DIALOG;
						}
					} else {
						Log.i(tag, "����ʧ��");
					}
				} catch (MalformedURLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					msg.what = URL_ERROR;
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					msg.what = NETWORK_ERROR;
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					msg.what = JSON_ERROR;
				} finally {
					long endTime = System.currentTimeMillis();
					// ���ǻ��˶���ʱ��
					long dTime = endTime - startTime;
					// 2000
					if (dTime < 2000) {
						try {
							Thread.sleep(2000 - dTime);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
					handler.sendMessage(msg);
				}

			}
		}.start();

	}

	/**
	 * ��ȡ����汾��
	 */
	private String getVersionName() {
		// ���������ֻ���APK
		PackageManager pm = getPackageManager();
		try {
			// �õ�֪��APK�Ĺ����嵥�ļ�
			PackageInfo info = pm.getPackageInfo(getPackageName(), 0);
			return info.versionName;
		} catch (NameNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "";
		}
	}

	/**
	 * //path ��address.db������ݿ⿽����data/data/��������/files/address.db
	 */
	private void copyDB(String filename) {
		// ֻҪ�㿽����һ�Σ��ҾͲ�Ҫ���ٿ�����
		try {
			File file = new File(getFilesDir(), filename);
			if (file.exists() && file.length() > 0) {
				// �����ˣ��Ͳ���Ҫ������
				Log.i(TAG, "�����ˣ��Ͳ���Ҫ������");
			} else {
				InputStream is = getAssets().open(filename);
				FileOutputStream fos = new FileOutputStream(file);
				byte[] buffer = new byte[1024];
				int len = 0;
				while ((len = is.read(buffer)) != -1) {
					fos.write(buffer, 0, len);
				}
				is.close();
				fos.close();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
