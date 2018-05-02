package com.chuangfeigu.tools.sdk.glide;

import android.content.Context;
import android.graphics.Bitmap;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.chuangfeigu.tools.R;
import com.makeramen.roundedimageview.RoundedImageView;

/**
 * Created by lshy on 2018-1-7.
 */

public class GlideUtil {


//    public static void loadView(String imageurl, ImageView imageView) {
//        Glide.with(imageView.getContext()).load(imageurl).centerCrop().into(imageView);
//    }

    public static void loadView(String url, ImageView img, int i) {
        if (img == null || img.getContext() == null) {
            return;
        }
//        if (img instanceof CircleImageView) {
//            img.setImageResource(defaultid);
//            Glide.with(context)
//                    .load(url)
//                    .placeholder(defaultid)
//                    .error(errorid)
//                    .centerCrop()
//                    .into(new SimpleTarget<GlideDrawable>() {
//                        @Override
//                        public void onResourceReady(GlideDrawable resource,
//                                                    GlideAnimation<? super GlideDrawable> glideAnimation) {
//                            img.setImageDrawable(resource);
//                        }
//                    });
//        } else
        Context context = img.getContext();
        int defaulltimageid = R.mipmap.touxiang104;
        int defaulltimageid2 = R.mipmap.slmrtp_02;
        int defaulltimageid3 = R.mipmap.slmrtp;
        int defaultid;
        int errorid;
        if (i == 1) {
            defaultid = defaulltimageid;
            errorid = defaulltimageid;
        } else if (i == 2) {
            defaultid = defaulltimageid2;
            errorid = defaulltimageid2;
        } else {
            defaultid = defaulltimageid3;
            errorid = defaulltimageid3;
        }
        if (img.getWidth() > (img.getHeight() * 1.2)) {
            defaultid = defaulltimageid3;
            errorid = defaulltimageid3;
        }
        if (img instanceof RoundedImageView) {
            Glide.with(context)
                    .load(url)
                    .asBitmap()
                    .placeholder(defaultid)
                    .error(errorid)
                    .into(new BitmapImageViewTarget(img) {
                        @Override
                        protected void setResource(Bitmap resource) {
                            img.setImageBitmap(resource);
                        }
                    });
        } else {
            Glide.with(context)
                    .load(url)
                    .placeholder(defaultid)
                    .error(errorid)
                    .into(img);
        }
    }
}
