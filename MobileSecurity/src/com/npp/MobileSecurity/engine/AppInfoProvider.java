package com.npp.MobileSecurity.engine;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;

import com.npp.MobileSecurity.domain.AppInfo;

/**
 * 
 * @Description TODO
 * @author NingPingPing
 * @date 2014��10��5�� ����10:46:20
 */
public class AppInfoProvider {
	/**
	 * ��ȡ�����ֻ�Ӧ����Ϣ
	 * 
	 * @return
	 */
	public static List<AppInfo> getAppInfos(Context context) {
		PackageManager pm = context.getPackageManager();
		List<PackageInfo> PackageInfos = pm.getInstalledPackages(0);
		List<AppInfo> AppInfos = new ArrayList<AppInfo>();
		for (PackageInfo packinfo : PackageInfos) {
			AppInfo appInfo = new AppInfo();
			String packageName = packinfo.packageName;// ����
			long last_UpdateTime = packinfo.lastUpdateTime;// ������ʱ��
			String versionname = packinfo.versionName;// �汾��
			Drawable app_icon = packinfo.applicationInfo.loadIcon(pm);// Ӧ��ͼ��
			String app_name = packinfo.applicationInfo.loadLabel(pm).toString();// Ӧ������

			// ��ȡӦ�ó����Ƿ��ǵ�����Ӧ�ó���
			appInfo.setUserApp(filterApp(packinfo.applicationInfo));
			appInfo.setApp_icon(app_icon);
			appInfo.setApp_name(app_name);
			appInfo.setApp_PackName(packageName);
			appInfo.setLastupdate_time(last_UpdateTime);
			appInfo.setVersionname(versionname);
			AppInfos.add(appInfo);
		}
		return AppInfos;
	}

	/**
	 * ����Ӧ�ó���Ĺ�����
	 * 
	 * @param info
	 * @return true ����Ӧ�� false ϵͳӦ��
	 */
	public static boolean filterApp(ApplicationInfo info) {
		if ((info.flags & ApplicationInfo.FLAG_UPDATED_SYSTEM_APP) != 0) {
			// �������ϵͳ��Ӧ��,���Ǳ��û�������. �û�Ӧ��
			return true;
		} else if ((info.flags & ApplicationInfo.FLAG_SYSTEM) == 0) {
			// ������û���Ӧ��
			return true;
		}
		return false;
	}

}
