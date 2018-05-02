package com.chuangfeigu.tools.view;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.text.Html;
import android.text.Spanned;
import android.widget.TextView;

import com.chuangfeigu.tools.common.StringUtils;

import java.util.List;

/**
 * Created by lshy on 2018-4-21.
 */
//图文混排辅助类
public class TextDrawable {

    public static Drawable getDrawableText(TextView textview, String text, int color, int textColor) {
        if (textview == null || textview.getContext() == null) {
            return null;
        }
        Rect re = new Rect();
        textview.getPaint().getTextBounds(text, 0, text.length(), re);
        com.amulyakhare.textdrawable.TextDrawable textDrawable = com.amulyakhare.textdrawable.TextDrawable.builder().beginConfig()
                .textColor(textColor)
                .fontSize(Float.valueOf(textview.getTextSize()).intValue() - 4)/* size in px */
                .bold()
                .height(re.height() + 5)
                .width(re.width() + 10)
                .endConfig()
                .buildRoundRect(text, color, 6);
        textDrawable.setBounds(0, 0, textDrawable.getIntrinsicWidth(), textDrawable.getIntrinsicHeight());
        return textDrawable;
    }

    public static Drawable getDrawableText(TextView textview, String text, int color) {
        return getDrawableText(textview, text, color, Color.WHITE);
    }

    public static String setHtmlimage(int star, String image, String text) {
        StringBuilder re = new StringBuilder();
        String regex = "<img src.*?>";
        String[] oop = text.split(regex);
        String[] oop2 = StringUtils.spiltsp(text, regex);
        int i = 0, p = star;
        for (int j = 0; j < oop.length; j++) {
            if (p > oop[j].length()) {
                i++;
                p = p - oop[j].length();
            } else {
                break;
            }
        }
        StringBuilder re2 = new StringBuilder(oop2[i]);
        re2.insert(p, "<img src='" + image + "'/> ");
        oop2[i] = re2.toString();
        for (int j = 0; j < oop2.length; j++) {
            re.append(oop2[j]);
        }
        return re.toString();
    }


    public static void setTextview(TextView textview, Context context, String content, List<ChaParams> chaParams) {
        textview.setText(getImageSpan(textview, context, content, chaParams));
    }

    private static Spanned getImageSpan(TextView textView, Context context, String content, List<ChaParams> chaParams) {
        String re = content;
        for (ChaParams chaParam : chaParams) {
            re = setHtmlimage(chaParam.index, chaParam.getName(), re);
        }
        Spanned hotSpan = Html.fromHtml(re, new Html.ImageGetter() {
            @Override
            public Drawable getDrawable(String source) {
                if (source != null && source.length() > 0) {
                    //根据id从资源文件中获取图片对象
                    ChaParams thchaParam = null;
                    Drawable d = null;
                    for (ChaParams chaParam : chaParams) {
                        if (source.equals(chaParam.getName())) {
                            thchaParam = chaParam;
                        }
                    }
                    if (chaParams != null) {
                        d = getDrawableText(textView, thchaParam.getName(), thchaParam.getBgcolor(), thchaParam.getTextcolor());
                        if (d != null) {
                            d.setBounds(0, 0, d.getIntrinsicWidth(), d.getIntrinsicHeight());
                        }
                    }
                    return d;
                }
                return null;
            }
        }, null);
        return hotSpan;
    }

    public static class ChaParams {

        int index;//位置
        String name;//插入词
        int bgcolor;
        int textcolor;

        public ChaParams(int index, String name, int bgcolor, int textcolor) {
            this.index = index;
            this.name = name;
            this.bgcolor = bgcolor;
            this.textcolor = textcolor;
        }

        public int getIndex() {
            return index;
        }

        public void setIndex(int index) {
            this.index = index;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getBgcolor() {
            return bgcolor;
        }

        public void setBgcolor(int bgcolor) {
            this.bgcolor = bgcolor;
        }

        public int getTextcolor() {
            return textcolor;
        }

        public void setTextcolor(int textcolor) {
            this.textcolor = textcolor;
        }
    }

}
