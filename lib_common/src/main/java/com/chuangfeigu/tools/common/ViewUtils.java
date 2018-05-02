package com.chuangfeigu.tools.common;

import android.text.InputFilter;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.Filter;

import java.lang.reflect.Field;

/**
 * 提供视图有关的静态方法
 *
 * @author lshy
 */
public class ViewUtils {
    public static boolean inRangeOfView(View view, MotionEvent ev) {
        int[] location = new int[2];
        view.getLocationOnScreen(location);
        int x = location[0];
        int y = location[1];
        if (ev.getRawX() < x || ev.getRawX() > (x + view.getWidth()) || ev.getRawY() < y
                || ev.getRawY() > (y + view.getHeight())) {
            return false;
        }
        return true;
    }

    public static View getClickChild(ViewGroup group, MotionEvent ev) {
        try {

            int ccc = group.getChildCount();
            for (int i = 0; i < ccc; i++) {
                View vchild = group.getChildAt(i);
                if (inRangeOfView(vchild, ev)) {
                    return vchild;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 不过滤任何数据的过滤器
     *
     * @author lshy
     */
    public static class DefaultArrayFilter extends Filter {
        private BaseAdapter bsadapter;

        public DefaultArrayFilter(BaseAdapter bsadapter) {
            this.bsadapter = bsadapter;
        }

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults results = new FilterResults();
            results.values = null;
            results.count = 1;
            return results;
        }

        // 在这里返回过滤结果
        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            if (bsadapter != null)
                bsadapter.notifyDataSetChanged();
        }

    }

    public static int getRawX(View view) {
        int[] location = new int[2];
        view.getLocationInWindow(location); //获取在当前窗口内的绝对坐标
        view.getLocationOnScreen(location);//获取在整个屏幕内的绝对坐标
        return location[0];
    }

    public static int getRawY(View view) {
        int[] location = new int[2];
        view.getLocationInWindow(location); //获取在当前窗口内的绝对坐标
        view.getLocationOnScreen(location);//获取在整个屏幕内的绝对坐标
        return location[1];
    }

    /**
     * 获取设置的最大长度
     *
     * @return
     */
    public static int getMaxLength(EditText et) {
        int length = 0;
        try {
            InputFilter[] inputFilters = et.getFilters();
            for (InputFilter filter : inputFilters) {
                Class<?> c = filter.getClass();
                if (c.getName().equals("android.text.InputFilter$LengthFilter")) {
                    Field[] f = c.getDeclaredFields();
                    for (Field field : f) {
                        if (field.getName().equals("mMax")) {
                            field.setAccessible(true);
                            length = (Integer) field.get(filter);
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return length;
    }




}
