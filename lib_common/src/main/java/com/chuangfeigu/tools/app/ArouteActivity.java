package com.chuangfeigu.tools.app;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;

import com.chuangfeigu.tools.sdk.aroute.ARouteUtil;

import java.util.List;

/**
 * Created by lshy on 2018-3-12.
 */

public class ArouteActivity extends BaseActivity {

    String TAG = "route";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fenxi();
    }

    @Override
    protected void onNewIntent(Intent intent) {

        fenxi();
        super.onNewIntent(intent);
    }

    private void fenxi() {
        Uri uri = getIntent().getData();
        if (uri != null) {
            // 完整的url信息
            String url = uri.toString();
            Log.e(TAG, "url: " + uri);
            // scheme部分
            String scheme = uri.getScheme();
            Log.e(TAG, "scheme: " + scheme);
            // host部分
            String host = uri.getHost();
            Log.e(TAG, "host: " + host);
            //port部分
            int port = uri.getPort();
            Log.e(TAG, "host: " + port);
            // 访问路劲
            String path = uri.getPath();
            Log.e(TAG, "path: " + path);
            if (!path.startsWith("/")) path = "/" + path;
            ARouteUtil.toactivity(path);
            finish();
            List<String> pathSegments = uri.getPathSegments();
            // Query部分
            String query = uri.getQuery();
            Log.e(TAG, "query: " + query);
            //获取指定参数值
            String goodsId = uri.getQueryParameter("goodsId");
            Log.e(TAG, "goodsId: " + goodsId);
        }
    }


}
