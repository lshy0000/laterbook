package com.lsy.laterbook.contract;

import android.app.Activity;

import com.bestxty.ai.data.net.PerActivity;

import dagger.Module;
import dagger.Provides;

/**
 * Created by lshy on 2018-1-30.
 */
@Module
public class ActivityMod {
    private final Activity activity;

    public ActivityMod(Activity activity) {
        this.activity = activity;
    }

    @Provides
    @PerActivity
    Activity activity() {
        return this.activity;
    }
}
