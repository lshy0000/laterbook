package com.lsy.laterbook;

import android.content.Context;

import com.chuangfeigu.tools.app.App;
import com.chuangfeigu.tools.app.T;
import com.tencent.bugly.Bugly;
import com.tencent.bugly.beta.Beta;

/**
 * Created by lshy on 2018-5-1.
 */

public class MApp extends App {
    @Override
    public void onCreate() {
        super.onCreate();
        T.showToast("android学习之路永无止境");
        Bugly.init(this, getMetaData(this,"BUGLY_APPID"), false);
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        Beta.installTinker();
    }
}
