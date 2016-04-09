/**
 * @author wenford.li
 * @email  26597925@qq.com
 * @remark 应用启动
 */
package com.mylove.happy.tv;

import android.os.Bundle;
import android.provider.Settings.Secure;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.mylove.happy.tv.util.LogUtil;

public class MainActivity extends AndroidApplication {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String android_id = Secure.getString(getContext().getContentResolver(),Secure.ANDROID_ID);
        LogUtil.e(this, android_id);
        AndroidApplicationConfiguration cfg = new AndroidApplicationConfiguration();
        cfg.useAccelerometer = true;
        cfg.useCompass = true;
        initialize(new InGame(), cfg);
    }
    //加一个定时器，连续按两次退出
    /*public void onBackPressed() {
    	return;
    }*/
}