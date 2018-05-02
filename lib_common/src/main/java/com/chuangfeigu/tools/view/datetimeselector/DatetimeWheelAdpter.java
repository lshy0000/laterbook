package com.chuangfeigu.tools.view.datetimeselector;

import com.chuangfeigu.tools.common.DateUtil;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by lshy on 2018-4-12.
 */

public class DatetimeWheelAdpter implements WheelAdapter {
    Calendar calendar;

    public DatetimeWheelAdpter() {
        calendar = Calendar.getInstance();

    }


    @Override
    public int getItemsCount() {
        return 50000;
    }

    @Override
    public String getItem(int index) {
        calendar.setTimeInMillis(0);
        calendar.add(Calendar.DAY_OF_MONTH, index);
       return getTimeStr(calendar.getTimeInMillis());
    }


    public String getTimeStr(long time) {
        return DateUtil.sf999.format(new Date(time));
    }

    @Override
    public int getMaximumLength() {
        return 15;
    }
}
