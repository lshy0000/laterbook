package com.chuangfeigu.tools.sdk.aroute;

import com.alibaba.android.arouter.core.LogisticsCenter;
import com.alibaba.android.arouter.facade.Postcard;
import com.alibaba.android.arouter.launcher.ARouter;

import java.util.Iterator;
import java.util.Map;

/**
 * Created by lshy on 2018-2-1.
 */

public class ARouteUtil {
    public static <T> T getObject(String str) {
        T a = ((T) ARouter.getInstance().build(str).navigation());
        return a;
    }

    /**
     * @param str
     * @param i    无意义
     * @param str2
     */
    public static void toactivity(String str, int i, String str2) {
        try {
            String[] strs = str.split("/");
            String group = strs[0];
            try {
                LogisticsCenter.completion(new Postcard(str, group));
                ARouter.getInstance().build(str).navigation();
            } catch (Exception e) {
                ARouter.getInstance().build(str2).navigation();
            }
        } catch (Exception e) {

        }
    }

    public static void toactivity(String str) {
        try {
            ARouter.getInstance().build(str).navigation();
        } catch (Exception e) {
        }
    }

    public static void toactivity(String str, Object... value) {
        try {
            Postcard a = ARouter.getInstance().build(str);
            Object[] a2 = value;
            for (int i = 0; i < a2.length; i = i + 2) {
                if (a2[i + 1] instanceof String)
                    a = a.withString(((String) a2[i]), ((String) a2[i + 1]));
                else if (a2[i + 1] instanceof Long)
                    a = a.withLong(((String) a2[i]), ((Long) a2[i + 1]));
                else if (a2[i + 1] instanceof Integer)
                    a = a.withInt(((String) a2[i]), ((Integer) a2[i + 1]));
                else if (a2[i + 1] instanceof Boolean)
                    a = a.withBoolean(((String) a2[i]), ((Boolean) a2[i + 1]));
                else if (a2[i + 1] instanceof Double)
                    a = a.withDouble(((String) a2[i]), ((Double) a2[i + 1]));
                else if (a2[i + 1] instanceof Character)
                    a = a.withChar(((String) a2[i]), ((Character) a2[i + 1]));
                else if (a2[i + 1] instanceof Short)
                    a = a.withShort(((String) a2[i]), ((Short) a2[i + 1]));
                else if (a2[i + 1] instanceof Byte)
                    a = a.withByte(((String) a2[i]), ((Byte) a2[i + 1]));
                else if (a2[i + 1] instanceof Float)
                    a = a.withFloat(((String) a2[i]), ((Float) a2[i + 1]));
            }
            a.navigation();
        } catch (Exception e) {
        }
    }

    public static void toactivity(String str, Map<String, String> p) {
        try {
            Postcard a = ARouter.getInstance().build(str);
            Iterator pkeyset = p.keySet().iterator();
            while (pkeyset.hasNext()) {
                String cc = ((String) pkeyset.next());
                a.withString(cc, p.get(cc));
            }
            a.navigation();
        } catch (Exception e) {
        }
    }
}
