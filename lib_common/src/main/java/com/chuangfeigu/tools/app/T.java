package com.chuangfeigu.tools.app;

import android.content.Context;
import android.os.Looper;
import android.support.v4.BuildConfig;
import android.widget.Toast;


/**
 * Created by fro-soft on 2017-11-29.
 */

public class T {

    public static void showToast(final Context context, final String str) {

        new android.os.Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                Context context2 = null;
                if (context == null) {
                    context2 = App.getApp();
                } else {
                    context2 = context;
                }
                Toast.makeText(context2, str, Toast.LENGTH_SHORT).show();
            }
        });
    }

    public static void showToast(String str) {
        showToast(App.getApp(), str);
    }

    public static void showToast(String s, boolean b) {
        if (b && BuildConfig.DEBUG) {
            showToast(s);
        }
    }

    public static void showToast2(String s) {
        showToast(s, true);
    }

    public static void showShort(CharSequence message) {
        if (App.isApkDebugable(App.getApp()))
            showToast(message.toString());
    }
}
