package com.chuangfeigu.tools.common;

import android.util.Log;

import java.util.HashMap;

/**
 * Created by lshy on 2018-3-30.//页面之间传递非基本类型数据解决方案
 */

public class TipsUtils {

    //跨模块通信键名
    public static final String KEY_USERID = "userid";
    public static final String ARCTICLEID = "articleId";
    public static final String CHATUSERID = "identify";
    public static final String CHATTYPE = "chattype";

    public static String type_Invalid = "Invalid";
    public static String type_C2C = "C2C";
    public static String type_Group = "Group";
    public static String type_System = "System";
    public static HashMap<String, Object> re = new HashMap<>();

    public static boolean putOb(String key, Object ob) {
        if (ob != null) {
            re.put(key, ob);
        }
        return true;
    }

    public static <T> T getOb(String key) {
        Object r = re.get(key);
        T rr;
        if (r == null) {
            Log.e("TipsError", "null value for" + key);
            return null;
        }
        try {
            rr = (T) r;
        } catch (ClassCastException e) {
            Log.e("TipsError", "for" + key + ": " + e.toString());
            return null;
        }
        re.remove(key);
        return rr;
    }


}
