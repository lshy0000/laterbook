package com.chuangfeigu.tools.app;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.Application;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.multidex.MultiDex;
import android.util.DisplayMetrics;
import android.util.Log;

import com.alibaba.android.arouter.launcher.ARouter;

import java.util.List;

/**
 * Created by fro-soft on 2017-11-27.
 */

public class App extends Application implements Application.ActivityLifecycleCallbacks {
    private static App app;

    public App getApplication() {
        return app;
    }

    public static Activity getFrontContext() {
        return com.chuangfeigu.tools.app.BaseActivity.getBase();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        getScreenDensity_ByResources();
        registerActivityLifecycleCallbacks(this);
        registerActivityLifecycleCallbacks(ProgressBarU.getInstance());
        registerActivityLifecycleCallbacks(ActivityUtil.getInstance());
//        refWatcher = initializeLeakDetection();
        if (isApkDebugable(this)) {
            ARouter.openDebug();
            ARouter.openLog();
        }
        ARouter.init(this);
        app = this;
        T.showShort("手机信息:" + "Product Model: " + android.os.Build.MODEL + ","
                + android.os.Build.VERSION.SDK_INT + ","
                + android.os.Build.CPU_ABI
                + android.os.Build.VERSION.RELEASE);
    }


    public static String getMetaData(Context context, String key) {
        ApplicationInfo info = null;
        String c = null;
        try {
            info = context.getPackageManager()
                    .getApplicationInfo(context.getPackageName(),
                            PackageManager.GET_META_DATA);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        if (info != null) {
            c = info.metaData.getString(key, null);
        }
        Log.i("matadate", "key" + key + "value" + c);
        return c;
    }


    public void getScreenDensity_ByResources() {
        DisplayMetrics mDisplayMetrics = getResources().getDisplayMetrics();
        Const.screenwidth = mDisplayMetrics.widthPixels;
        Const.screenheight = mDisplayMetrics.heightPixels;
        Const.screendensity = mDisplayMetrics.density;
        Const.screendensityDpi = mDisplayMetrics.densityDpi;
        T.showToast2("宽" + Const.screenwidth + "\n" + Const.screenheight + "fdsalkjf" + Const.screendensity);

    }

    /**
     * 程序是否在前台运行
     *
     * @return
     */
    public static boolean isAppOnForeground() {
        // Returns a list of application processes that are running on the
        // device
        ActivityManager activityManager = (ActivityManager) app.getSystemService(Context.ACTIVITY_SERVICE);
        String packageName = app.getPackageName();
        List<ActivityManager.RunningAppProcessInfo> appProcesses = activityManager
                .getRunningAppProcesses();
        if (appProcesses == null)
            return false;
        for (ActivityManager.RunningAppProcessInfo appProcess : appProcesses) {
            // The name of the process that this object is associated with.
            if (appProcess.processName.equals(packageName)
                    && appProcess.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND) {
                return true;
            }
        }
        return false;
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(base);
    }


    public static App getApp() {
        return app;
    }

    /**
     * 获取版本号
     *
     * @return 当前应用的版本号
     */
    public String getVersion() {
        try {
            PackageManager manager = this.getPackageManager();
            PackageInfo info = manager.getPackageInfo(this.getPackageName(), 0);
            String version = info.versionName;
            return version;
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    public int getVersionCode() {
        try {
            PackageManager manager = this.getPackageManager();
            PackageInfo info = manager.getPackageInfo(this.getPackageName(), 0);
            int version = info.versionCode;
            return version;
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        unregisterActivityLifecycleCallbacks(this);
        unregisterActivityLifecycleCallbacks(ProgressBarU.getInstance());
        unregisterActivityLifecycleCallbacks(ActivityUtil.getInstance());
    }

    @Override
    public void onActivityCreated(Activity activity, Bundle bundle) {

    }

    @Override
    public void onActivityStarted(Activity activity) {
        if (activityAount == 0) {
        }
        activityAount++;

    }

    private int activityAount = 0;

    @Override
    public void onActivityResumed(Activity activity) {

    }

    @Override
    public void onActivityPaused(Activity activity) {
    }

    @Override
    public void onActivityStopped(Activity activity) {
        activityAount--;
    }

    @Override
    public void onActivitySaveInstanceState(Activity activity, Bundle bundle) {

    }

    @Override
    public void onActivityDestroyed(Activity activity) {
        if (activityAount == 0) {

        }
    }

    //是否是调试模式
    public static boolean isApkDebugable(Context context) {
        try {
            ApplicationInfo info = context.getApplicationInfo();
            boolean re = (info.flags & ApplicationInfo.FLAG_DEBUGGABLE) != 0;
            Log.i("matadate", "是否调试模式：" + re);
            return re;
        } catch (Exception e) {

        }
        return false;
    }

    public String getBaseUrl() {
        return Const.BASEURL;
    }

}
