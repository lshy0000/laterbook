package com.chuangfeigu.tools.view.datetimeselector;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.view.View;

import com.chuangfeigu.tools.R;

import java.util.Calendar;

@SuppressLint("SimpleDateFormat")
public class TimePickerShow {

    private Context context;
    private WheelMain wheelMain;

    public TimePickerShow(Context context) {
        super();
        this.context = context;
    }


    public long getTxtTime() {
        return wheelMain.getTime();
    }


    public View timePickerView(long time) {
        View timepickerview = View.inflate(context, R.layout.timepicker, null);
        wheelMain = new WheelMain(timepickerview);
        wheelMain.initDateTimeSpecial(time);
        return timepickerview;
    }

    /**
     * 时间选择控件 v控制需要显示的视图
     *
     * @return
     */
    public View timePickerView(long time, boolean... v) {
        View timepickerview = View.inflate(context, R.layout.timepicker, null);
        wheelMain = new WheelMain(timepickerview);
        // 获取当前时间
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int min = calendar.get(Calendar.MINUTE);
        int second = calendar.get(Calendar.SECOND);
        wheelMain.setSTART_YEAR(year - 50);
        wheelMain.setEND_YEAR(year + 50);

        boolean[] r = v;
        for (int i = 0; i < r.length; i++) {
            if (i == 0) {
                year = -1;
            } else if (i == 1) {
                month = -1;
            } else if (i == 2) {
                day = -1;
            } else if (i == 3) {
                hour = -1;
            } else if (i == 4) {
                min = -1;
            } else if (i == 5) {
                second = -1;
            }
        }
        wheelMain.initDateTimePicker(year, month, day, hour, min, second);

        return timepickerview;
    }

    /**
     * alertDialog时间选择
     */
    public Dialog timePickerAlertDialog(Context context, String title, long time, OnTimeChangedListener listener) {
        AlertDialog.Builder dialog = new AlertDialog.Builder(context);
        dialog.setTitle(title);
        dialog.setView(timePickerView(time));
        dialog.setNegativeButton(context.getText(R.string.do_cancel), (v, b) -> v.dismiss());
        dialog.setPositiveButton(context.getText(R.string.do_sure), (v, b) -> listener.onSelectTime(wheelMain.getTimeSpecial()));
        Dialog a = dialog.create();
        a.show();
        return  a;
    }

    public interface OnTimeChangedListener {
        void onSelectTime(long txtTime);
    }
}
