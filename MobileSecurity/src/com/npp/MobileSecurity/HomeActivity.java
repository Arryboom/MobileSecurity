package com.npp.MobileSecurity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.npp.MobileSecurity.ui.CustomDialog;
import com.npp.MobileSecurity.utils.MD5Utils;

public class HomeActivity extends Activity {

	private GridView list_home;
	private Myadapter adapter;
	private SharedPreferences sp;
	private AlertDialog dialog;
	private Intent intent;
	private ImageView anim_rotation;
	private ImageView anim_main;
	private EditText et_setup_pwd;
	private EditText et_setup_confirm;
	private Button ok;
	private Button cancel;
	private static String[] names = { "�ֻ�����", "ͨѶ��ʿ", "�������", "���̹���", "����ͳ��",
			"�ֻ�ɱ��", "��������", "�߼�����", "��������" };
	private static int[] icos = { R.drawable.safe, R.drawable.callmsgsafe,
			R.drawable.app, R.drawable.taskmanager, R.drawable.netmanager,
			R.drawable.trojan, R.drawable.sysoptimize, R.drawable.atools,
			R.drawable.settings };

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.home_activity);
		list_home = (GridView) findViewById(R.id.list_home);
		anim_rotation=(ImageView) findViewById(R.id.anim_rotation);
		anim_main=(ImageView) findViewById(R.id.anim_main);
		sp = getSharedPreferences("config", MODE_PRIVATE);
		adapter = new Myadapter();
		list_home.setAdapter(adapter);
		list_home.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				switch (position) {
				case 8://��������
					intent = new Intent(HomeActivity.this,
							SettingActivity.class);
					startActivity(intent);
					break;
				case 6://��������
					intent = new Intent(HomeActivity.this,
							ClearCacheActivity.class);
					startActivity(intent);
					break;
				case 5://������ɱ
					intent = new Intent(HomeActivity.this,
							VirusActivity.class);
					startActivity(intent);
					break;
				case 2://������ɱ
					intent = new Intent(HomeActivity.this,
							AppManageActivity.class);
					startActivity(intent);
					break;
				case 3://���̹���
					intent = new Intent(HomeActivity.this,
							TaskManageAcivity.class);
					startActivity(intent);
					break;
				case 0:
					ShowLostfindDialog();
					break;

				default:
					break;
				}

			}
		});
		Animation hyperspaceJumpAnimation=AnimationUtils.loadAnimation(this, R.anim.av_main_rotation);
		anim_rotation.startAnimation(hyperspaceJumpAnimation);
		Animation hyperspaceJump=AnimationUtils.loadAnimation(this, R.anim.av_main_bright);
		anim_main.startAnimation(hyperspaceJump);
		anim_main.startAnimation(hyperspaceJump);
	}

	protected void ShowLostfindDialog() {
		// TODO Auto-generated method stub
		if (IsSetupPwd()) {// ���ù�����
			ShowEnterDialog();
		} else {
			// û���ù����롣�������ÿ�Dialog
			ShowSetupPwdDialog();
		}

	}

	private void ShowSetupPwdDialog() {
		AlertDialog.Builder builder = new Builder(HomeActivity.this);
		// �Զ���һ�������ļ�
		View view = View.inflate(HomeActivity.this, R.layout.dialog_setup_pwd,
				null);
		et_setup_pwd = (EditText) view.findViewById(R.id.et_setup_pwd);
		et_setup_confirm = (EditText) view.findViewById(R.id.et_setup_confirm);
		ok = (Button) view.findViewById(R.id.ok);
		cancel = (Button) view.findViewById(R.id.cancel);
		cancel.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// ������Ի���ȡ����
				dialog.dismiss();
			}
		});
		ok.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// ȡ������
				String password = et_setup_pwd.getText().toString().trim();
				String password_confirm = et_setup_confirm.getText().toString()
						.trim();
				if (TextUtils.isEmpty(password)
						|| TextUtils.isEmpty(password_confirm)) {
					Toast.makeText(HomeActivity.this, "����Ϊ��", 0).show();
					return;
				}
				// �ж��Ƿ�һ�²�ȥ����
				if (password.equals(password_confirm)) {
					// һ�µĻ����ͱ������룬�ѶԻ�����������Ҫ�����ֻ�����ҳ��
					Editor editor = sp.edit();
					editor.putString("password", MD5Utils.md5Password(password));// ������ܺ��
					editor.commit();
					dialog.dismiss();
					Intent intent = new Intent(HomeActivity.this,
							LostFindActivity.class);
					startActivity(intent);
				} else {

					Toast.makeText(HomeActivity.this, "���벻һ��", 0).show();
					return;
				}

			}
		});
		dialog = builder.create();
		dialog.setView(view, 0, 0, 0, 0);
		dialog.show();

	}

	private void ShowEnterDialog() {
		AlertDialog.Builder builder = new Builder(HomeActivity.this);
		// �Զ���һ�������ļ�
		View view = View.inflate(HomeActivity.this,
				R.layout.dialog_enter_password, null);
		et_setup_pwd = (EditText) view.findViewById(R.id.et_setup_pwd);
		ok = (Button) view.findViewById(R.id.ok);
		cancel = (Button) view.findViewById(R.id.cancel);
		cancel.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				dialog.dismiss();
			}
		});
		ok.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// ȡ������
				String password = et_setup_pwd.getText().toString().trim();
				String savePassword = sp.getString("password", "");// ȡ�����ܺ��
				if (TextUtils.isEmpty(password)) {
					Toast.makeText(HomeActivity.this, "����Ϊ��", 1).show();
					return;
				}

				if (MD5Utils.md5Password(password).equals(savePassword)) {
					// �������������֮ǰ���õ�����
					// �ѶԻ���������������ҳ�棻
					dialog.dismiss();

					Intent intent = new Intent(HomeActivity.this,
							LostFindActivity.class);
					startActivity(intent);

				} else {
					Toast.makeText(HomeActivity.this, "�������", 1).show();
					et_setup_pwd.setText("");
					return;
				}

			}
		});
		dialog = builder.create();
		dialog.setView(view, 0, 0, 0, 0);
		dialog.show();
	}

	/**
	 * �ж��Ƿ����ù�����
	 * 
	 * @return
	 */
	private boolean IsSetupPwd() {
		String pwd = sp.getString("password", null);
		return !TextUtils.isEmpty(pwd);
	}

	private class Myadapter extends BaseAdapter {

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return names.length;
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub
			View view = View.inflate(HomeActivity.this,
					R.layout.list_home_item, null);
			ImageView iv_item = (ImageView) view.findViewById(R.id.iv_item);
			TextView tv_item = (TextView) view.findViewById(R.id.tv_item);
			iv_item.setImageResource(icos[position]);
			tv_item.setText(names[position]);
			return view;
		}

	}

}
