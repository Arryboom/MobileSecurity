package com.npp.MobileSecurity;

import android.app.Activity;
import android.os.Bundle;
import android.os.Environment;
import android.os.StatFs;
import android.text.format.Formatter;
import android.widget.TextView;

public class AppManageActivity extends Activity {
	private TextView tv_ROMsize;
	private TextView tv_SDsize;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_appmanage);
		tv_ROMsize = (TextView) findViewById(R.id.tv_ROMsize);
		tv_SDsize = (TextView) findViewById(R.id.tv_Sdsize);
		// ��ÿ����ڴ�
		long RomSize = getPathAvlibeSpace(Environment.getDataDirectory()
				.getAbsolutePath());
		// ��ÿ������
		long SDSize = getPathAvlibeSpace(Environment
				.getExternalStorageDirectory().getAbsolutePath());
		tv_ROMsize.setText("ʣ��ROM:" + Formatter.formatFileSize(this, RomSize));
		tv_SDsize.setText("ʣ��SD��:" + Formatter.formatFileSize(this, SDSize));
	}

	/**
	 * ��ȡ�����ڴ��С=����ĸ���*����Ĵ�С ��ȡ�����ڴ��С=��������ĸ���*����Ĵ�С
	 * 
	 * @param path
	 * @return
	 */
	public long getPathAvlibeSpace(String path) {
		StatFs sf = new StatFs(path);
		sf.getBlockCount();// ��ȡ����ĸ���
		long size = sf.getBlockSize();// ��ȡ����Ĵ�С
		long count = sf.getAvailableBlocks();// ��ȡ���õ��������
		return size * count;
	}

}
