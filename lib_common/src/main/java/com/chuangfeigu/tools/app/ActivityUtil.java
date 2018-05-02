package com.chuangfeigu.tools.app;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by lshy on 2018-3-2.
 */

/**
 * 保持当前activity示例
 */
public class ActivityUtil implements Application.ActivityLifecycleCallbacks {
    private static ActivityUtil instance;
    public Set<Activity> activities = new HashSet<>();

    public Set<Activity> getActivities() {
        return activities;
    }

    public Activity getCurrentact() {

        return currentact;
    }

    static Activity currentact;

    @Override
    public void onActivityCreated(Activity activity, Bundle bundle) {
        currentact = activity;
        activities.add(currentact);
    }

    @Override
    public void onActivityStarted(Activity activity) {

    }

    private ActivityUtil() {
    }

    @Override
    public void onActivityResumed(Activity activity) {
        currentact = activity;
    }

    @Override
    public void onActivityPaused(Activity activity) {
        currentact = null;
    }


    @Override
    public void onActivityStopped(Activity activity) {

    }

    @Override
    public void onActivitySaveInstanceState(Activity activity, Bundle bundle) {

    }

    @Override
    public void onActivityDestroyed(Activity activity) {
        activities.remove(activity);
    }

    public static ActivityUtil getInstance() {
        if (instance == null) {
            instance = new ActivityUtil();
        }
        return instance;
    }

    public void finishAll() {
        for (Activity activity : ActivityUtil.getInstance().getActivities()) {
            activity.finish();
        }
    }

    public void finishAllun(List<Class> list) {
        for (Activity activity : ActivityUtil.getInstance().getActivities()) {
            if (!list.contains(activity.getClass())) {
                activity.finish();
            }
        }
    }
}
