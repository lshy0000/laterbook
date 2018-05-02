package com.chuangfeigu.tools.common;

import android.os.Handler;
import android.os.Looper;

/**
 * 简单线程切换
 * Created by fro-soft on 2017-12-8.
 */

public class ThreadUtils {

    //切换到主线程(加了一个主线程的判断)
    public static void runOnUiThread(Runnable runnable) {
        if ((Thread.currentThread() != Looper.getMainLooper().getThread())) {
            UiThreadExecutorrunTask("", runnable, 0L);
        } else {
            runnable.run();
        }
    }

    private static void UiThreadExecutorrunTask(String s, final Runnable runnable, long l) {
        new Handler(Looper.getMainLooper()).post(runnable);
    }

    //切换到非主线程(加了一个主线程的判断)
    public static void runOnBackg(Runnable runnable) {
        if ((Thread.currentThread() == Looper.getMainLooper().getThread())) {
            BackgroundExecutorexecute(runnable);
        } else {
            runnable.run();
        }
    }

    private static void BackgroundExecutorexecute(Runnable runnable) {
        new Thread(runnable).start();
    }

}
