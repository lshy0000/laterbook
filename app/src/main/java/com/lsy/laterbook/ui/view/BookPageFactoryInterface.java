package com.lsy.laterbook.ui.view;

import android.graphics.Bitmap;
import android.graphics.Canvas;

import com.bestxty.ai.domain.bean.Record;

/**
 * Created by lshy on 2018-4-30.
 */

public interface BookPageFactoryInterface {
    public void setBgBitmap(Bitmap bitmap) ;

    public boolean isfirstPage() ;

    public void nextPage() ;

    public boolean islastPage() ;

    public void onDraw(Canvas mNextPageCanvas) ;

    public void prePage();

    public void init(Record s);

    int getReadindex();

    void setIndex(int index);

    void setNight(boolean isnight);

    void setmNextPageCanvas(Canvas mCurPageCanvas);

    void destory();

    int getTotal();

    int current();
}
