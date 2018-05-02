package com.chuangfeigu.tools.app;

import android.content.Context;

/**
 * @author 姜泰阳
 *         Created by 姜泰阳 on 2017/10/26.
 */

public interface LoadDataView {

    /**
     * Show a view with a progress bar indicating a loading process.
     */
    void showLoading();

    /**
     * Hide a loading view.
     */
    void hideLoading();
    /**
     * Show an error message
     *
     * @param message A string representing an error.
     */
    void showError(String message);

    /**
     * Show a retry view in case of an error when retrieving data.
     */
    void showRetry();

    /**
     * Hide a retry view shown if there was an error when retrieving data.
     */
    void hideRetry();

    /**
     * Get a {@link android.content.Context}.
     */
    Context context();


    void error(int errorcode,String from);
}
