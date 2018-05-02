package com.chuangfeigu.tools.app;

import android.content.Context;
import android.support.v4.app.Fragment;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by lshy on 2017-12-29.
 */

public class BaseFragment extends Fragment implements LoadDataView, OnrefreshListener {
    Set<WatcherLifer> watcherLifers = new HashSet<>();

    public void addWatcherLifer(WatcherLifer watcherLifer) {
        watcherLifers.add(watcherLifer);
    }

    public void removeWatcherLifer(WatcherLifer watcherLifer) {
        watcherLifers.remove(watcherLifer);
    }

    @Override
    public void onResume() {
        super.onResume();
        for (WatcherLifer watcherLifer : watcherLifers) {
            if (watcherLifer != null) {
                watcherLifer.resume();
            }
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        for (WatcherLifer watcherLifer : watcherLifers) {
            if (watcherLifer != null) {
                watcherLifer.stop();
            }
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        for (WatcherLifer watcherLifer : watcherLifers) {
            if (watcherLifer != null) {
                watcherLifer.pause();
            }
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        for (WatcherLifer watcherLifer : watcherLifers) {
            if (watcherLifer != null) {
                watcherLifer.destory();
            }
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        for (WatcherLifer watcherLifer : watcherLifers) {
            if (watcherLifer != null) {
                watcherLifer.start();
            }
        }
    }


    @Override
    public void showLoading() {
        if (getActivity() instanceof BaseActivity)
            ((BaseActivity) getActivity()).showLoading();
    }

    @Override
    public void hideLoading() {
        if (getActivity() instanceof BaseActivity)
            ((BaseActivity) getActivity()).hideLoading();
    }

    @Override
    public void showError(String message) {

    }

    @Override
    public void showRetry() {

    }

    @Override
    public void hideRetry() {

    }

    @Override
    public Context context() {
        return null;
    }

    @Override
    public void error(int errorcode, String from) {
        if (getActivity() instanceof BaseActivity)
            ((BaseActivity) getActivity()).error(errorcode, from);
    }

    @Override
    public void refresh() {

    }


}
