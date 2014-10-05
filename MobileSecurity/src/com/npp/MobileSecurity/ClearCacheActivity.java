package com.npp.MobileSecurity;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

import android.app.Activity;
import android.content.pm.ApplicationInfo;
import android.content.pm.IPackageDataObserver;
import android.content.pm.IPackageStatsObserver;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.pm.PackageStats;
import android.os.Bundle;
import android.os.RemoteException;
import android.text.format.Formatter;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.npp.MobileSecurity.ui.SearchDevicesView;

public class ClearCacheActivity extends Activity {
	public static final String TAG = "ClearCacheActivity";
	private ProgressBar pb;
	private TextView tv_scan_statue;
	private PackageManager pm;
	private LinearLayout ll_container;
	private SearchDevicesView search_device_view;
	// ȫ�ֱ��������浱ǰ��ѯ������Ϣ
	private long cachesize; // �����С
	private long datasize; // ���ݴ�С
	private long codesize; // Ӧ�ó����С

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.clearcache_activity);
		pb = (ProgressBar) findViewById(R.id.pb);
		ll_container = (LinearLayout) findViewById(R.id.ll_container);
		tv_scan_statue = (TextView) findViewById(R.id.tv_scan_statue);
		search_device_view = (SearchDevicesView) findViewById(R.id.search_device_view);
		search_device_view.setWillNotDraw(false);
		ScanCache();
	}

	/**
	 * ɨ������Ӧ�ó���Ļ�����Ϣ
	 */
	private void ScanCache() {
		// TODO Auto-generated method stub
		pm = getPackageManager();
		search_device_view.setSearching(true);
		new Thread(new Runnable() {
			@Override
			public void run() {
				// TODO Auto-generated method stub
				Method getPackageSizeInfoMethod = null;
				Method[] methods = PackageManager.class.getMethods();
				for (Method method : methods) {
					if ("getPackageSizeInfo".equals(method.getName())) {
						// �õ�����
						getPackageSizeInfoMethod = method;

					}
				}

				List<PackageInfo> ackageInfos = pm.getInstalledPackages(0);
				pb.setMax(ackageInfos.size());
				int progress = 0;
				for (PackageInfo packinfo : ackageInfos) {
					getPackageINfo(packinfo.packageName);
					progress++;
					try {
						Thread.sleep(50);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					pb.setProgress(progress);
				}
				runOnUiThread(new Runnable() {
					@Override
					public void run() { // TODO Auto-generated method stub
						tv_scan_statue.setText("ɨ�����");
						search_device_view.setSearching(false);

					}
				});

			}
		}).start();

	}

	// ��ȡӦ�ó�����Ϣ
	public void getPackageINfo(String pkg) {
		try {
			Method getPackageSizeInfo = pm.getClass().getMethod(
					"getPackageSizeInfo", String.class,
					IPackageStatsObserver.class);
			getPackageSizeInfo.invoke(pm, pkg, new PkgSizeObserver());
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	// aidl�ļ��γɵ�Bindler���Ʒ�����
	class PkgSizeObserver extends IPackageStatsObserver.Stub {

		public void onGetStatsCompleted(PackageStats pStats, boolean succeeded) {
			cachesize = pStats.cacheSize; // �����С
			datasize = pStats.codeSize; // ���ݴ�С
			codesize = pStats.codeSize; // Ӧ�ó����С
			final String packageName = pStats.packageName;
			final ApplicationInfo appInfo;
			try {
				appInfo = pm.getApplicationInfo(packageName, 0);

				runOnUiThread(new Runnable() {
					@Override
					public void run() { // TODO Auto-generated method stub
						tv_scan_statue.setText("����ɨ�裺" + appInfo.loadLabel(pm));
						if (cachesize > 0) {
							View view = View.inflate(getApplicationContext(),
									R.layout.list_item_cacheinfo, null);
							ImageView iv_icon = (ImageView) view
									.findViewById(R.id.iv_icon);
							TextView tv_cacheName = (TextView) view
									.findViewById(R.id.tv_cacheName);
							TextView tv_cacheSize = (TextView) view
									.findViewById(R.id.tv_cacheSize);
							ImageView iv_deletecache = (ImageView) view
									.findViewById(R.id.iv_deletecache);

							tv_cacheName.setText(appInfo.loadLabel(pm));
							tv_cacheSize
									.setText("�����С: "
											+ Formatter.formatFileSize(
													getApplicationContext(),
													cachesize));
							iv_icon.setImageDrawable(appInfo.loadIcon(pm));
							iv_deletecache
									.setOnClickListener(new OnClickListener() {

										@Override
										public void onClick(View v) {
											// TODO Auto-generated method stub
										
										}});

							// �������ļ���ӵ�LinearLayout��
							ll_container.addView(view, 0);
						}
					}
				});
			} catch (NameNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	private class myDataObserver extends IPackageDataObserver.Stub
	{

		@Override
		public void onRemoveCompleted(String packageName, boolean succeeded)
				throws RemoteException {
			// TODO Auto-generated method stub
			System.out.println(packageName+"####"+succeeded);
			
		}
		
	}
	
	public void clearAllCache(View view)
	{
		Method[] methods = PackageManager.class.getMethods();
		for (Method method : methods) {
			if ("freeStorageAndNotify".equals(method.getName())) {
				
				// �õ�����
				try {
					method.invoke(pm, Long.MAX_VALUE,new myDataObserver());
				} catch (IllegalArgumentException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (InvocationTargetException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
}
