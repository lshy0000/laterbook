package com.bestxty.ai.domain;


import android.util.Log;

import com.chuangfeigu.tools.app.App;
import com.chuangfeigu.tools.app.ProgressBarU;

import io.reactivex.observers.DisposableObserver;

/**
 * Created by lshy on 2018-1-31.
 */

public abstract class Obse<T> extends DisposableObserver<T> {

    @Override
    public void onError(Throwable e) {
        if (App.isApkDebugable(App.getApp())) {
            e.printStackTrace();
        }
        ProgressBarU.dismissprogressdialog();
        Log.e("rxjava", e.toString());
    }

    @Override
    public void onComplete() {

    }

    @Override
    public void onNext(T t) {

    }
}
