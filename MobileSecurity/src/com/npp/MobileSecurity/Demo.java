package com.npp.MobileSecurity;

import java.lang.reflect.Method;

import javax.xml.transform.ErrorListener;
import javax.xml.transform.TransformerException;

import com.android.volley.RequestQueue;
import com.android.volley.Response.Listener;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;

public class Demo extends Activity{
	
    /* //StringRequest�ĸ���������ֱ���Request���ͣ�url,����������Ӧ�����������������
     mQueue.add(new StringRequest(Method.GET, "http://www.baidu.com/", new Listener<String>(){
*/
         @Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
			RequestQueue mQueue=Volley.newRequestQueue(this);
			StringRequest sr= new StringRequest("http://ping3018.ecjtu.org", new Listener<String>() {

				@Override
				public void onResponse(String arg0) {
					// TODO Auto-generated method stub
					System.out.println(arg0);
					
				}
			}, null);
		mQueue.add(sr);
		
	}	
}
