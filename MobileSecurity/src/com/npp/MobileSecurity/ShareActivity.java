package com.npp.MobileSecurity;

import android.app.Activity;

import com.umeng.socialize.controller.RequestType;
import com.umeng.socialize.controller.UMServiceFactory;
import com.umeng.socialize.controller.UMSocialService;
import com.umeng.socialize.media.UMImage;

public class ShareActivity extends Activity {
	// ����������Activity��������³�Ա����
	final UMSocialService mController = UMServiceFactory.getUMSocialService(
			"com.umeng.share", RequestType.SOCIAL);

	protected void onCreate(android.os.Bundle savedInstanceState) {
		// setContentView(layoutResID);
		// ���÷�������
		mController.setShareContent("�������ʹ�����ѵļҰ����Ǿ��ÿ�");
		// ���÷���ͼƬ, ����2ΪͼƬ��url��ַ
		mController.setShareMedia(new UMImage(this,
				"http://www.umeng.com/images/pic/banner_module_social.png"));
	};

	// ���÷���ͼƬ������2Ϊ����ͼƬ����Դ����
	// mController.setShareMedia(new UMImage(getActivity(), R.drawable.icon));
	// ���÷���ͼƬ������2Ϊ����ͼƬ��·��(����·��)
	// mController.setShareMedia(new UMImage(getActivity(),
	// BitmapFactory.decodeFile("/mnt/sdcard/icon.png")));

	// ���÷�������
	// UMusic uMusic = new UMusic("http://sns.whalecloud.com/test_music.mp3");
	// uMusic.setAuthor("GuGu");
	// uMusic.setTitle("����֮��");
	// ������������ͼ
	// uMusic.setThumb("http://www.umeng.com/images/pic/banner_module_social.png");
	// mController.setShareMedia(uMusic);

	// ���÷�����Ƶ
	// UMVideo umVideo = new UMVideo(
	// "http://v.youku.com/v_show/id_XNTE5ODAwMDM2.html?f=19001023");
	// ������Ƶ����ͼ
	// umVideo.setThumb("http://www.umeng.com/images/pic/banner_module_social.png");
	// umVideo.setTitle("������ữ����!");
	// mController.setShareMedia(umVideo);

}
