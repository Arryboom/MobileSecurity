package com.npp.MobileSecurity.WB;

import android.app.Activity;
import android.os.Bundle;

import com.sina.weibo.sdk.auth.Oauth2AccessToken;
import com.sina.weibo.sdk.auth.WeiboAuth;
import com.sina.weibo.sdk.auth.sso.SsoHandler;

/**
 * 
 * @author NingPingPing
 * 
 */
public class WBAuthActivity extends Activity {

    /** ΢�� Web ��Ȩ�࣬�ṩ��½�ȹ��� */
    private WeiboAuth mWeiboAuth;

    /** ��װ�� "access_token"��"expires_in"��"refresh_token"�����ṩ�����ǵĹ����� */
    private Oauth2AccessToken mAccessToken;

    /** ע�⣺SsoHandler ���� SDK ֧�� SSO ʱ��Ч */
    private SsoHandler mSsoHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
    }

}
