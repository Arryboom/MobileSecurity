package com.npp.MobileSecurity;

import android.app.Activity;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

import com.npp.MobileSecurity.ui.SettingItemView;
import com.umeng.socialize.controller.RequestType;
import com.umeng.socialize.controller.UMServiceFactory;
import com.umeng.socialize.controller.UMSocialService;
import com.umeng.socialize.media.UMImage;

public class SettingActivity extends Activity {
	private SettingItemView siv_update;
	private SharedPreferences sp;
	final UMSocialService mController = UMServiceFactory.getUMSocialService("com.umeng.share",
            RequestType.SOCIAL);

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.setting_activity);
		siv_update = (SettingItemView) findViewById(R.id.siv_update);
		sp = getSharedPreferences("config", MODE_PRIVATE);
		boolean update = sp.getBoolean("update", false);
		if (update) {
			siv_update.setChecked(true);

		} else {
			siv_update.setChecked(false);
		}

		siv_update.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Editor editor = sp.edit();
				// TODO Auto-generated method stub
				if (siv_update.isChecked()) {// �Ѿ����Զ�����
					siv_update.setChecked(false);
					editor.putBoolean("update", false);
				} else {// û�д��Զ�����
					siv_update.setChecked(true);
					editor.putBoolean("update", true);
				}
				editor.commit();

			}
		});
	}
	
	public void share(View view)
	{
		//���÷�������
		mController.setShareContent("������ữ�����SDK�����ƶ�Ӧ�ÿ��������罻�����ܣ�http://www.umeng.com/social");
		//���÷���ͼƬ, ����2ΪͼƬ��url��ַ
		mController.setShareMedia(new UMImage(getActivity(), 
		"http://www.umeng.com/images/pic/banner_module_social.png"));
		//Ϊ�˱�֤���˷���ɹ����ܹ���PC��������ʾ��������website                                      
		mController.setAppWebSite(SHARE_MEDIA.RENREN, "http://www.umeng.com/social");
		//���÷���ͼƬ������2Ϊ����ͼƬ����Դ����
		//mController.setShareMedia(new UMImage(getActivity(), R.drawable.icon));
		//���÷���ͼƬ������2Ϊ����ͼƬ��·��(����·��)
		//mController.setShareMedia(new UMImage(getActivity(), 
		//BitmapFactory.decodeFile("/mnt/sdcard/icon.png")));

		//���÷�������
		//UMusic uMusic = new UMusic("http://sns.whalecloud.com/test_music.mp3");
		//uMusic.setAuthor("GuGu");
		//uMusic.setTitle("����֮��");
		//������������ͼ
		//uMusic.setThumb("http://www.umeng.com/images/pic/banner_module_social.png");
		//mController.setShareMedia(uMusic);

		//���÷�����Ƶ
		//UMVideo umVideo = new UMVideo(
		//"http://v.youku.com/v_show/id_XNTE5ODAwMDM2.html?f=19001023");
		//������Ƶ����ͼ
		//umVideo.setThumb("http://www.umeng.com/images/pic/banner_module_social.png");
		//umVideo.setTitle("������ữ����!");
		//mController.setShareMedia(umVideo);
	}


}
