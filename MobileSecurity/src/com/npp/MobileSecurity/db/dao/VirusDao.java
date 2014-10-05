package com.npp.MobileSecurity.db.dao;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class VirusDao {
	/**
	 * �����ݿ���ȥ����һ��md5�Ƿ����
	 * 
	 * @param md5
	 * @return
	 */

	public static boolean IsVirus(String md5) {
		boolean result = false;
		String path = "/data/data/com.npp.MobileSecurity/files/antivirus.db";
		SQLiteDatabase db = SQLiteDatabase.openDatabase(path, null,
				SQLiteDatabase.OPEN_READONLY);
		Cursor cursor = db.rawQuery("select * from datable where md5=?", new String[]{md5});
		if (cursor.moveToNext()) {
			result = true;
		}
		cursor.close();
		db.close();
		return result;

	}
}
