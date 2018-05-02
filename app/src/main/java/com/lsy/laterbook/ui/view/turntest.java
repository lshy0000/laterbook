package com.lsy.laterbook.ui.view;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import com.bestxty.ai.domain.bean.Record;
import com.chuangfeigu.tools.app.Const;
import com.lsy.laterbook.R;
import com.lsy.laterbook.ui.ac.NetFactory;

public class turntest {

    /**
     * Called when the activity is first created.
     */
    private PageWidget mPageWidget;
    Bitmap mCurPageBitmap, mNextPageBitmap;
    Canvas mCurPageCanvas, mNextPageCanvas;
    BookPageFactoryInterface pagefactory;

    public View onCreate(Activity context, Record record, BookPageFactoryInterface pagefactory) {

        mPageWidget = new PageWidget(context, Const.screenwidth, Const.screenheight);
        mCurPageBitmap = Bitmap.createBitmap(Const.screenwidth, Const.screenheight, Bitmap.Config.ARGB_8888);
        mNextPageBitmap = Bitmap
                .createBitmap(Const.screenwidth, Const.screenheight, Bitmap.Config.ARGB_8888);

        mCurPageCanvas = new Canvas(mCurPageBitmap);
        mNextPageCanvas = new Canvas(mNextPageBitmap);
        this.pagefactory = pagefactory;
        pagefactory.setBgBitmap(BitmapFactory.decodeResource(
                context.getResources(), R.drawable.bg));//设置背景图片
        try {
            pagefactory.init(record);//打开文件
            (pagefactory).setmNextPageCanvas(mCurPageCanvas);
            if ((pagefactory instanceof NetFactory)) {

            } else {
                pagefactory.onDraw(mCurPageCanvas);//将文字绘于手机屏幕
            }
        } catch (Exception e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
            Toast.makeText(context, "电子书不存在",
                    Toast.LENGTH_SHORT).show();
        }

        mPageWidget.setBitmaps(mCurPageBitmap, mCurPageBitmap);

        mPageWidget.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent e) {
                // TODO Auto-generated method stub

                boolean ret = false;
                if (v == mPageWidget) {
                    if (e.getAction() == MotionEvent.ACTION_DOWN) {
                        //停止动画。与forceFinished(boolean)相反，Scroller滚动到最终x与y位置时中止动画。
                        mPageWidget.abortAnimation();
                        //计算拖拽点对应的拖拽角
                        mPageWidget.calcCornerXY(e.getX(), e.getY());
                        //将文字绘于当前页
                        pagefactory.onDraw(mCurPageCanvas);
                        if (mPageWidget.DragToRight()) {
                            //是否从左边翻向右边
                            try {
                                //true，显示上一页
                                pagefactory.prePage();
                            } catch (Exception e1) {
                                // TODO Auto-generated catch block
                                e1.printStackTrace();
                            }
                            if (pagefactory.isfirstPage()) return false;
                            pagefactory.onDraw(mNextPageCanvas);
                        } else {
                            try {
                                //false，显示下一页
                                pagefactory.nextPage();
                            } catch (Exception e1) {
                                // TODO Auto-generated catch block
                                e1.printStackTrace();
                            }
                            if (pagefactory.islastPage()) return false;
                            pagefactory.onDraw(mNextPageCanvas);
                        }
                        mPageWidget.setBitmaps(mCurPageBitmap, mNextPageBitmap);
                    }

                    ret = mPageWidget.doTouchEvent(e);
                    return ret;
                }
                return false;
            }

        });
        return mPageWidget;
    }

    public void refresh() {
        if (mPageWidget != null) {
            mPageWidget.invalidate();
        }
    }

    public void setIndex(int index) {
        pagefactory.setIndex(index);
    }

    public void setNight(boolean isnight) {
        pagefactory.setNight(isnight);
    }
}
