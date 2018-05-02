package com.chuangfeigu.tools.app;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatDelegate;
import android.view.WindowManager;
import android.widget.Toast;

import com.chuangfeigu.tools.R;
import com.jaeger.library.StatusBarUtil;
import com.zhy.autolayout.AutoLayoutActivity;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import pub.devrel.easypermissions.AppSettingsDialog;
import pub.devrel.easypermissions.EasyPermissions;


/**
 * Created by fro-soft on 2017-11-28.
 */

public class BaseActivity extends AutoLayoutActivity implements EasyPermissions.PermissionCallbacks, LoadDataView {
    private static List<String> stic_permissions;
    private static BaseActivity base;
    public static int current;//当前存活的activity 的个数
    Set<WatcherLifer> watcherLifers = new HashSet<>();

    public void addWatcherLifer(WatcherLifer watcherLifer) {
        watcherLifers.add(watcherLifer);
    }

    public void removeWatcherLifer(WatcherLifer watcherLifer) {
        watcherLifers.remove(watcherLifer);
    }

    public static Activity getBase() {
        return base;
    }

    @Override
    protected void onStart() {
        super.onStart();
        for (WatcherLifer watcherLifer : watcherLifers) {
            if (watcherLifer != null) {
                watcherLifer.start();
            }
        }
    }


    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        // Forward results to EasyPermissions
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

//    public boolean isnotnullBundleKey(String key) {
//        return getIntent() != null && getIntent().getExtras() != null && getIntent().getExtras().getString(key) != null;
//    }

//    public String getBundleValue(String key) {
//        if (isnotnullBundleKey(key)) {
//            return getIntent().getExtras().getString(key);
//        }
//        return null;
//    }

    public <C> C getBundleValue(String key, C t) {
        Object c = null;
        if (getIntent() != null && getIntent().getExtras() != null && t != null) {
            Bundle a = getIntent().getExtras();
            if (t instanceof Long) {
                c = a.getLong(key, (Long) t);
            } else if (t instanceof String) {
                c = a.getString(key, (String) t);
            } else if (t instanceof Integer) {
                c = a.getInt(key, (Integer) t);
            } else if (t instanceof Double) {
                c = a.getDouble(key, (Double) t);
            } else if (t instanceof Float) {
                c = a.getFloat(key, (Float) t);
            } else if (t instanceof Short) {
                c = a.getShort(key, (Short) t);
            } else if (t instanceof Boolean) {
                c = a.getBoolean(key, (Boolean) t);
            } else if (t instanceof Byte) {
                c = a.getByte(key, (Byte) t);
            } else if (t instanceof Character) {
                c = a.getChar(key, (Character) t);
            }
        }
        if (c == null) {
            return null;
        }
        return (C) c;
    }

    public void darkActivity(float f) {
        WindowManager.LayoutParams a = getWindow().getAttributes();
        a.alpha = f;
        getWindow().setAttributes(a);
    }

    public int getColornoTheme(int resid) {
        return getResources().getColor(resid);
    }

    public String getStingnoThemt(int resid) {
        return getResources().getString(resid);
    }

    @Override
    public void onPermissionsGranted(int requestCode, List<String> perms) {

    }

    @Override
    public void onPermissionsDenied(int requestCode, List<String> perms) {
        for (String perm : perms) {
            if (isNeed(perm)) {
                stic_permissions = perms;
                new AppSettingsDialog.Builder(this)
                        .setRationale("没有" + perm + "权限，此应用程序可能无法正常工作。打开应用设置屏幕以修改应用权限")
                        .setTitle("必需权限")
                        .build()
                        .show();
                break;
            }
        }

    }


    @Override
    protected void onResume() {
        super.onResume();
        base = this;
        for (WatcherLifer watcherLifer : watcherLifers) {
            if (watcherLifer != null) {
                watcherLifer.resume();
            }
        }
    }

//    public int getColor(int resid) {
//        return getResources().getColor(resid);
//    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
        String[] perms = {Manifest.permission.WRITE_EXTERNAL_STORAGE};
        if (EasyPermissions.hasPermissions(this, perms)) {

        } else {
            EasyPermissions.requestPermissions(this, "需要存储权限", 0
                    , perms);
        }

        base = this;
        current++;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        base = this;
        current++;
    }

    @Override
    protected void onPause() {
        super.onPause();
        base = null;
        for (WatcherLifer watcherLifer : watcherLifers) {
            if (watcherLifer != null) {
                watcherLifer.pause();
            }
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        for (WatcherLifer watcherLifer : watcherLifers) {
            if (watcherLifer != null) {
                watcherLifer.start();
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        current--;
        for (WatcherLifer watcherLifer : watcherLifers) {
            if (watcherLifer != null) {
                watcherLifer.destory();
            }
        }
    }

    protected boolean isNeed(String perm) {
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == AppSettingsDialog.DEFAULT_SETTINGS_REQ_CODE) {
            // Do something after user returned from app settings screen, like showing a Toast.
            // 当用户从应用设置界面返回的时候，可以做一些事情，比如弹出一个土司。
            if (stic_permissions != null) {
                int i = -1;
                for (String permission : stic_permissions) {
                    if (!EasyPermissions.hasPermissions(this, permission)) {
                        i = 0;
                        break;
                    }
                }
                if (i != -1)
                    Toast.makeText(this, "动态权限申请成功", Toast.LENGTH_SHORT)
                            .show();
            }
        }
    }

    @Override
    public void setContentView(int id) {
        super.setContentView(id);
        StatusBarUtil.setColor(this, getResources().getColor(R.color.mytheme_bg_main), 0);
    }

    @Override
    public void showLoading() {
        ProgressBarU.showprogressdialog();
    }

    @Override
    public void hideLoading() {
        ProgressBarU.dismissprogressdialog();
    }

    @Override
    public void showError(String message) {
        T.showToast(message);
    }

    @Override
    public void showRetry() {

    }

    @Override
    public void hideRetry() {

    }

    @Override
    public Context context() {
        return this;
    }

    @Override
    public void error(int errorcode, String from) {
        if (from != null && from.contains("SocketTimeoutException")) {
            T.showToast("网络异常");
        } else if (App.isApkDebugable(this)) {
            T.showToast(from);
        }
    }
}
