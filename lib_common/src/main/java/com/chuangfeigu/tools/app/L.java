package com.chuangfeigu.tools.app;

import android.support.v4.BuildConfig;
import android.util.Log;

/**
 * Created by lshy on 2017-12-29.
 */

public class L {
    public static String tag = "xin.lsz17";

    public static int v(String msg) {
        return Log.v(tag, msg);
    }

    public static int v2(String msg) {
        if (!BuildConfig.DEBUG) {
            return -1;
        }
        return Log.v(tag, msg);
    }

    /**
     * @param msg The message you would like logged.
     */
    public static int d(String msg) {
        return Log.d(tag, msg);
    }

    public static int d2(String msg) {
        if (!BuildConfig.DEBUG) {
            return -1;
        }
        return Log.d(tag, msg);
    }

    /**
     * @param msg The message you would like logged.
     */
    public static int i(String msg) {
        return Log.i(tag, msg);
    }

    public static int i2(String msg) {
        if (!BuildConfig.DEBUG) {
            return -1;
        }
        return Log.i(tag, msg);
    }

    /**
     * @param msg The message you would like logged.
     */
    public static int e(String msg) {
        return Log.e(tag, msg);
    }

    public static int e2(String msg) {
        if (!BuildConfig.DEBUG) {
            return -1;
        }
        return Log.e(tag, msg);
    }

}
