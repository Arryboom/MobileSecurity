package com.npp.MobileSecurity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

public class LostFindActivity extends Activity {
	private SharedPreferences sp;
	private Intent intent;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
	
		//�ж��Ƿ����������򵼡�
		sp=getSharedPreferences("config", MODE_PRIVATE);
		boolean configed =sp.getBoolean("configed", false);
		if(configed)
		{
			//���ù��뵽���뵽�����ҳ��
			setContentView(R.layout.activity_lostfind);
			
		}
		else
		{
			intent=new Intent(this, SetupOneActivity.class);
			startActivity(intent);
			finish();
		}
	}

}
