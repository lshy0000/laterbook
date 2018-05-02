package com.chuangfeigu.tools.view.datetimeselector;

import android.view.View;
import android.widget.TextView;

import com.chuangfeigu.tools.R;
import com.chuangfeigu.tools.common.DateUtil;

import java.util.Calendar;


public class WheelMain {

    private View view;
    private WheelView wv_year;
    private WheelView wv_month;
    private WheelView wv_day;
    private WheelView wv_hours;
    private WheelView wv_mins;
    private WheelView wv_second;
    private int START_YEAR = 1950, END_YEAR;

    private int theyear;
    private int themonth;


    public View getView() {
        return view;
    }

    public void setView(View view) {
        this.view = view;
    }

    public int getSTART_YEAR() {
        return START_YEAR;
    }

    public void setSTART_YEAR(int sTART_YEAR) {
        START_YEAR = sTART_YEAR;
    }

    public int getEND_YEAR() {
        return END_YEAR;
    }

    public void setEND_YEAR(int eND_YEAR) {
        END_YEAR = eND_YEAR;
    }

    public WheelMain(View view) {
        super();
        this.view = view;
        setView(view);
    }

    public WheelMain(View view, boolean hasSelectTime) {
        super();
        this.view = view;
        setView(view);
    }

    public void initDateTimePicker(int year, int month, int day) {
        this.initDateTimePicker(year, month, day, -1, -1, -1);
    }

    public void initDateTimeSpecial(long time) {
//        int year = -1;
//        int month = -1;
//        int s = -1;
        Calendar cal = Calendar.getInstance();
        if (time > 0)
            cal.setTimeInMillis(time);
        int h = cal.get(Calendar.HOUR_OF_DAY);
        int m = cal.get(Calendar.MINUTE);
        wv_year = (WheelView) view.findViewById(R.id.year);
        wv_month = (WheelView) view.findViewById(R.id.month);
        wv_day = (WheelView) view.findViewById(R.id.day);
        wv_hours = (WheelView) view.findViewById(R.id.hour);
        wv_mins = (WheelView) view.findViewById(R.id.min);
        wv_second = (WheelView) view.findViewById(R.id.second);
        view.findViewById(R.id.text).setVisibility(View.VISIBLE);
        wv_year.setVisibility(View.GONE);
        wv_month.setVisibility(View.GONE);
        wv_day.setCyclic(true);
        wv_day.setAdapter(new DatetimeWheelAdpter());
        long a = (24000 * 3600);
        long b = cal.getTimeInMillis();
        Long c = b / a;
        wv_day.setCurrentItem(c.intValue());
        wv_day.addChangingListener((o, p, q) -> {
            getTimeSpecial();
        });
        wv_hours.setAdapter(new NumericWheelAdapter(0, 23));
        wv_hours.setCyclic(true);
        wv_hours.setLabel("时");
        wv_hours.setCurrentItem(h);
        wv_hours.addChangingListener((o, p, q) -> {
            getTimeSpecial();
        });
        wv_mins.setAdapter(new NumericWheelAdapter(0, 59));
        wv_mins.setCyclic(true);
        wv_mins.setLabel("分");
        wv_mins.setCurrentItem(m);
        wv_mins.addChangingListener((o, p, q) -> {
            getTimeSpecial();
        });
        wv_second.setVisibility(View.GONE);
        getTimeSpecial();

    }

    public long getTimeSpecial() {
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(0);
        cal.add(Calendar.DAY_OF_MONTH, wv_day.getCurrentItem());
        cal.set(Calendar.HOUR_OF_DAY, wv_hours.getCurrentItem());
        cal.set(Calendar.MINUTE, wv_mins.getCurrentItem());
        try {
            ((TextView) view.findViewById(R.id.text)).setText(DateUtil.sdf3.format(cal.getTime()));
        } catch (Exception e) {
        }
        return cal.getTimeInMillis();
    }

    /**
     * 加载时间选择器,可选显示，不使用传-1
     *
     * @param year
     * @param month
     * @param day
     * @param h
     * @param m
     */
    public void initDateTimePicker(int year, int month, int day, int h, int m, int s) {

        wv_year = (WheelView) view.findViewById(R.id.year);
        wv_month = (WheelView) view.findViewById(R.id.month);
        wv_day = (WheelView) view.findViewById(R.id.day);
        wv_hours = (WheelView) view.findViewById(R.id.hour);
        wv_mins = (WheelView) view.findViewById(R.id.min);
        wv_second = (WheelView) view.findViewById(R.id.second);

        // 年
        if (year != -1) {
            wv_year.setAdapter(new NumericWheelAdapter(START_YEAR, END_YEAR));// 设置"年"的显示数据
            wv_year.setCyclic(true);// 可循环滚动
            wv_year.setLabel("年");// 添加文字
            wv_year.setCurrentItem(year - START_YEAR);// 初始化时显示的数据
            // 添加"年"监听
            OnWheelChangedListener wheelListener_year = new OnWheelChangedListener() {
                public void onChanged(WheelView wheel, int oldValue, int newValue) {
                    theyear = newValue + START_YEAR;
                    // 判断大小月及是否闰年,用来确定"日"的数据
                    wv_day.setAdapter(new NumericWheelAdapter(1, DateUtil.getMouthDay(theyear, themonth)));
                }
            };
            wv_year.addChangingListener(wheelListener_year);
        } else {
            wv_year.setVisibility(View.GONE);
        }
        // 月
        if (month != -1) {
            wv_month.setAdapter(new NumericWheelAdapter(1, 12));
            wv_month.setCyclic(true);
            wv_month.setLabel("月");
            wv_month.setCurrentItem(month);
            // 添加"月"监听
            OnWheelChangedListener wheelListener_month = new OnWheelChangedListener() {
                public void onChanged(WheelView wheel, int oldValue, int newValue) {
                    themonth = newValue;
                    wv_day.setAdapter(new NumericWheelAdapter(1, DateUtil.getMouthDay(theyear, themonth)));
                }
            };
            wv_month.addChangingListener(wheelListener_month);
        } else {
            wv_month.setVisibility(View.GONE);
        }

        // 日
        if (day != -1) {
            wv_day.setCyclic(true);
            wv_day.setAdapter(new NumericWheelAdapter(1, DateUtil.getMouthDay(theyear, themonth)));
            wv_day.setLabel("日");
            wv_day.setCurrentItem(day - 1);
        } else {
            wv_day.setVisibility(View.GONE);
        }

        // 时
        if (h != -1) {
            wv_hours.setAdapter(new NumericWheelAdapter(0, 23));
            wv_hours.setCyclic(true);
            wv_hours.setLabel("时");
            wv_hours.setCurrentItem(h);
        } else {
            wv_hours.setVisibility(View.GONE);
        }

        // 分
        if (m != -1) {
            wv_mins.setAdapter(new NumericWheelAdapter(0, 59));
            wv_mins.setCyclic(true);
            wv_mins.setLabel("分");
            wv_mins.setCurrentItem(m);
        } else {
            wv_mins.setVisibility(View.GONE);
        }

        // 秒
        if (s != -1) {
            wv_second.setAdapter(new NumericWheelAdapter(0, 59));
            wv_second.setCyclic(true);
            wv_second.setLabel("秒");
            wv_second.setCurrentItem(s);
        } else {
            wv_second.setVisibility(View.GONE);
        }

    }

    public void initDateTimePicker() {
        Calendar cal = Calendar.getInstance();
        initDateTimePicker(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH), cal.get(Calendar.HOUR_OF_DAY), cal.get(Calendar.MINUTE), -1);
    }

    public long getTime() {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, wv_year.getCurrentItem() + START_YEAR);
        cal.set(Calendar.MONTH, wv_month.getCurrentItem());
        cal.set(Calendar.DAY_OF_MONTH, wv_day.getCurrentItem() + 1);
        cal.set(Calendar.HOUR_OF_DAY, wv_hours.getCurrentItem());
        cal.set(Calendar.MINUTE, wv_mins.getCurrentItem());
        return cal.getTimeInMillis();
    }

    /**
     * 获得选中时间
     *
     * @param strYear   间开符号
     * @param strMon
     * @param strDay
     * @param strHour
     * @param strMins
     * @param strSecond
     * @return
     */
    public String getTime(String strYear, String strMon, String strDay, String strHour, String strMins, String strSecond) {
        StringBuffer sb = new StringBuffer();
        String year = "";
        String mon = "";
        String day = "";
        String hour = "";
        String mins = "";
        String second = "";

        if (wv_year.getVisibility() != View.GONE) {
            year = String.valueOf(wv_year.getCurrentItem() + START_YEAR);
            year = new StringBuffer(year + strYear).toString();
        }
        if (wv_month.getVisibility() != View.GONE) {
            mon = String.valueOf(wv_month.getCurrentItem() + 1);
            if (wv_month.getCurrentItem() + 1 <= 9) {
                mon = new StringBuffer("0" + mon).toString(); // 前面加0
            }
            mon = new StringBuffer(mon + strMon).toString();
        }
        if (wv_day.getVisibility() != View.GONE) {
            day = String.valueOf(wv_day.getCurrentItem() + 1);
            if (wv_day.getCurrentItem() + 1 <= 9) {
                day = new StringBuffer("0" + day).toString();
            }
            day = new StringBuffer(day + strDay).toString();
        }
        if (wv_hours.getVisibility() != View.GONE) {
            hour = String.valueOf(wv_hours.getCurrentItem());
            if (wv_hours.getCurrentItem() <= 9) {
                hour = new StringBuffer("0" + hour).toString();
            }
            hour = new StringBuffer(hour + strHour).toString();
        }
        if (wv_mins.getVisibility() != View.GONE) {
            mins = String.valueOf(wv_mins.getCurrentItem());
            if (wv_mins.getCurrentItem() <= 9) {
                mins = new StringBuffer("0" + mins).toString();
            }
            mins = new StringBuffer(mins + strMins).toString();
        }
        if (wv_second.getVisibility() != View.GONE) {
            second = String.valueOf(wv_second.getCurrentItem());
            if (wv_second.getCurrentItem() <= 9) {
                second = new StringBuffer("0" + second).toString();
            }
            second = new StringBuffer(second + strSecond).toString();
        }

        sb.append(year).append(mon).append(day).append(hour).append(mins).append(second);
        return sb.toString();
    }
}
