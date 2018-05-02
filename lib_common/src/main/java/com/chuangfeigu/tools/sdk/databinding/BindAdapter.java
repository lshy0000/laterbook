package com.chuangfeigu.tools.sdk.databinding;

import android.databinding.BindingAdapter;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import com.chuangfeigu.tools.common.StringUtils;

/**
 * Created by lshy on 2017-12-29.
 */

public class BindAdapter {

    @BindingAdapter({"image_url"})
    public static void loadImage(ImageView view, String url){
        if(!StringUtils.isEmpty(url)) {
            Glide.with(view.getContext()).load(url).into(view);  //设置全缓存.diskCacheStrategy(DiskCacheStrategy.ALL)
        }
    }

}
