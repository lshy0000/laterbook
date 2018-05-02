package com.lsy.laterbook.ui.ac;

import android.content.Context;
import android.graphics.Canvas;

import com.bestxty.ai.data.DataInit;
import com.bestxty.ai.domain.bean.A;
import com.bestxty.ai.domain.bean.B;
import com.bestxty.ai.domain.bean.BookInfo;
import com.bestxty.ai.domain.bean.BooksEntity;
import com.bestxty.ai.domain.bean.ChapterList;
import com.bestxty.ai.domain.bean.D;
import com.bestxty.ai.domain.bean.Details;
import com.bestxty.ai.domain.bean.E;
import com.bestxty.ai.domain.bean.F;
import com.bestxty.ai.domain.bean.RankList;
import com.bestxty.ai.domain.bean.Record;
import com.bestxty.ai.domain.bean.SearchList;
import com.bestxty.ai.domain.bean.Source;
import com.chuangfeigu.tools.app.BaseActivity;
import com.chuangfeigu.tools.app.T;
import com.chuangfeigu.tools.common.StringUtils;
import com.lsy.laterbook.contract.AllContract;
import com.lsy.laterbook.contract.DaggerActivityComp;
import com.lsy.laterbook.ui.view.BookPageFactory;
import com.lsy.laterbook.ui.view.BookPageFactoryInterface;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

/**
 * Created by lshy on 2018-4-30.
 */

public class NetFactory extends BookPageFactory implements BookPageFactoryInterface, AllContract.View {
    @Inject
    AllContract.Presenter presenter;
    Record record;
    BaseActivity activity;
    String current;
    String next;
    String last;
    int idet;
    int theee = 0;
    List<List<String>> m_lineses = new ArrayList<>();
    private int csx;

    @Override
    public void setIndex(int index) {
        if (index > -1 && index < record.getChapterList().getChapters().size()) {
            record.setReadIndex(index);
            loadthe(0);
        } else {
            T.showToast("错误的index");
        }

    }

    public NetFactory(int width, int height, Callx callx, BaseActivity activity) {
        super(width, height, callx);
        this.activity = activity;
        this.presenter = presenter;
        initInject();
    }

    private void initInject() {
        DaggerActivityComp.builder()
                .applicationComponent(DataInit.getApplicationComponent())
                .build().inject(this);
        presenter.setView(this);
    }

    @Override
    public boolean isfirstPage() {
        return false;
    }

    @Override
    public void nextPage() {
        theee++;

        if (next == null) loadthe(1);

        boolean b = theee > m_lineses.size() - 1;
        if (b) {
            theee = m_lineses.size() - 1;
        }
        if (b && next != null) {
            nextzhang();
        }
    }

    private void nextzhang() {
        last = current;
        current = next;
        next = null;
        fenxi();
        theee = 0;
        record.setReadIndex((record.getReadIndex() + 1 < record.getChapterList().getChapters().size()) ? (record.getReadIndex() + 1) : record.getChapterList().getChapters().size() + 1);

    }

    @Override
    public boolean islastPage() {
        return false;
    }

    Canvas mNextPageCanvas;

    public Canvas getmNextPageCanvas() {
        return mNextPageCanvas;
    }

    public void setmNextPageCanvas(Canvas mNextPageCanvas) {
        this.mNextPageCanvas = mNextPageCanvas;
    }


    @Override
    public void onDraw(Canvas c) {
        if (current == null) {
            return;
        }
        if (theee >= 0 && theee < m_lineses.size()) {
            drawText(m_lineses.get(theee), c);
            //计算999.9%所占的像素宽度
            int nPercentWidth = (int) mPaint.measureText("999.9%") + 1;
            c.drawText(theee + 1 + "/" + m_lineses.size(), mWidth - nPercentWidth, mHeight - 5, mPaint);
            c.drawText(record.getChapterList().getChapters().get(record.getReadIndex()).getTitle(), 0 + 52, 0 + 52, mPaint);

        }
    }

    @Override
    public void prePage() {
        theee--;
        if (last == null) loadthe(-1);
        boolean a = theee < 0;
        if (a) {
            theee = 0;
        }
        if (a && last != null) {
            lastzhang();
        }

    }

    private void lastzhang() {
        next = current;
        current = last;
        last = null;
        fenxi();
        theee = m_lineses.size() - 1;
        record.setReadIndex((record.getReadIndex() - 1 > -1) ? (record.getReadIndex() - 1) : 0);
    }

    @Override
    public void init(Record s) {
        this.record = s;
        loadthe(0);
    }

    private void loadthe(int ix) {
        if (record.getReadIndex() + ix > -1 && record.getReadIndex() + ix < record.getChapterList().getChapters().size()) {
            presenter.getChapterDetails(record.getChapterList().getChapters().get(record.getReadIndex() + ix).getLink());
            idet = ix;
        }
    }

    @Override
    public int getReadindex() {
        return record.getReadIndex();
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void showError(String message) {

    }

    @Override
    public void showRetry() {

    }

    @Override
    public void hideRetry() {

    }

    @Override
    public Context context() {
        return null;
    }

    @Override
    public void error(int errorcode, String from) {

    }

    @Override
    public void getFenleil(A a) {

    }

    @Override
    public void getFenleixl(D d) {

    }

    @Override
    public void getPaihangl(B b) {

    }

    @Override
    public void getPaihangd(RankList rankList) {

    }

    @Override
    public void getList(E e) {

    }

    @Override
    public void getBookInfo(BookInfo bookInfo) {

    }

    @Override
    public void getSource(List<Source> sources) {

    }

    @Override
    public void getChapters(ChapterList chapterList) {

    }

    @Override
    public void getSearchWord(F f) {

    }

    @Override
    public void getSearchList(SearchList searchList) {

    }

    @Override
    public void destory() {
        presenter.destory();
    }

    @Override
    public void getChapterDetails(Details details) {
        if (idet == 0) {
            current = getde(details);
            csx = gettheli(current) - 1;
            if (csx < 20) csx = 22;
            loadthe(-1);
            fenxi();
            onDraw(mNextPageCanvas);
            callx.dosomething();
        } else if (idet < 0) {
            last = getde(details);
        } else {
            next = getde(details);
        }
    }

    private String getde(Details details) {
        String re = details.getChapter().getCpContent();
        if (re == null || re.length() < 1) {
            re = details.getChapter().getBody() + "  ";
        }
        return re;
    }

    private void fenxi() {
        if (current == null) {
            return;
        }
        String[] a = current.replaceAll("\n\n", "\n").split("\n");
        List<String> li = new ArrayList<>();
        for (String s : a) {
            String[] b = StringUtils.stringSpilt(s, csx);
            for (String s1 : b) {
                if (s1 != null) {
                    li.add(s1);
                }
            }
        }
        m_lineses.clear();
        for (int i = 0; i < li.size(); i = i + mLineCount) {
            m_lineses.add(li.subList(i, (i + mLineCount) < li.size() ? i + mLineCount : li.size()));
        }
    }

    @Override
    public void save(Boolean b) {

    }

    @Override
    public void delete(Boolean b) {

    }

    @Override
    public void getRecords(List<Record> records) {

    }

    @Override
    public void getRecommend(List<BooksEntity> records) {

    }
}
