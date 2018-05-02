package com.chuangfeigu.tools.app;

import android.app.Activity;
import android.app.Application;
import android.app.ProgressDialog;
import android.os.Bundle;

/**
 * Created by lshy on 2018-1-31.
 * 注意会自动在activity的onpause消失
 */

public class ProgressBarU implements Application.ActivityLifecycleCallbacks {

    private static ProgressBarU instance;

    public static ProgressBarU getInstance() {
        if (instance == null) {
            instance = new ProgressBarU();
        }
        return instance;
    }

    private ProgressBarU() {

    }

    static ProgressDialog bar;

    @Override
    public void onActivityCreated(Activity activity, Bundle bundle) {
    }

    @Override
    public void onActivityStarted(Activity activity) {

    }

    @Override
    public void onActivityResumed(Activity activity) {
    }

    @Override
    public void onActivityPaused(Activity activity) {
        dismissprogressdialog();
        bar = null;

    }


    @Override
    public void onActivityStopped(Activity activity) {

    }

    @Override
    public void onActivitySaveInstanceState(Activity activity, Bundle bundle) {

    }

    @Override
    public void onActivityDestroyed(Activity activity) {

    }


    public static void dismissprogressdialog() {
        if (bar != null && bar.getOwnerActivity() == ActivityUtil.getInstance().getCurrentact() && bar.isShowing()) {
            bar.dismiss();
        }

    }

    public static void showprogressdialog() {
        if (bar == null) {
            bar = new ProgressDialog
                    (ActivityUtil.getInstance().getCurrentact());
            bar.setOwnerActivity(ActivityUtil.getInstance().getCurrentact());
//            bar.setTitle("正在夕阳下奔跑……");
            bar.setMessage("正在加载.....");
            bar.setCancelable(true);//如果是false，点击其他位置或者返回键无效，默认为true
        }
        bar.show();
    }

}
