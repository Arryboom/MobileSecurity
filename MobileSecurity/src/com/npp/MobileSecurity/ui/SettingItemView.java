package com.npp.MobileSecurity.ui;

import com.npp.MobileSecurity.R;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.CheckBox;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class SettingItemView extends RelativeLayout {
	private TextView tv_desc;
	private TextView tv_title;
	private CheckBox cb_status;

	/**
	 * �Զ�����Ͽռ䡣������������Textview��һ��Checkbox��һ��view
	 * 
	 * @param context
	 * @param attrs
	 * @param defStyle
	 */
	public SettingItemView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		// TODO Auto-generated constructor stub
		initView(context);
	}

	public SettingItemView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		initView(context);
	}

	public SettingItemView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		initView(context);
	}

	/**
	 * ��ʼ�������ļ�
	 * 
	 * @param context
	 */
	private void initView(Context context) {
		// TODO Auto-generated method stub
		// ��һ�������ļ�������SettingItemView��
		View.inflate(context, R.layout.setting_item, this);
		cb_status = (CheckBox) this.findViewById(R.id.cb_status);
		tv_desc=(TextView) this.findViewById(R.id.tv_desc);
		tv_title=(TextView) this.findViewById(R.id.tv_title);
		

	}

	/**
	 * �ж���Ͽؼ��Ƿ��з���
	 */
	public boolean isChecked() {
		return cb_status.isChecked();
	}	
	/**
	 * ������Ϳؼ���״̬
	 */
	public void setChecked(boolean checked){
		cb_status.setChecked(checked);
	}
	/**
	 * ����������Ϣ
	 */
	public void setDesc(String desc)
	{
		tv_desc.setText(desc);
	}

}
