package com.lsy.laterbook.contract;

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
import com.chuangfeigu.tools.app.LoadDataView;
import com.chuangfeigu.tools.app.WatcherLifer;

import java.util.List;

/**
 * Created by lshy on 2018-4-29.
 */

public interface AllContract {
    interface View extends LoadDataView {
        void getFenleil(A a);

        void getFenleixl(D d);

        void getPaihangl(B b);

        void getPaihangd(RankList rankList);

        void getList(E e);

        void getBookInfo(BookInfo bookInfo);

        void getSource(List<Source> sources);

        void getChapters(ChapterList chapterList);

        void getSearchWord(F f);

        void getSearchList(SearchList searchList);

        void getChapterDetails(Details details);

        void save(Boolean b);

        void delete(Boolean b);

        void getRecords(List<Record> records);

        void getRecommend(List<BooksEntity> records);

    }

    interface Presenter extends WatcherLifer {
        void getFenleil();

        void getFenleixl();

        void getPaihangl();

        void getPaihangd(String rankId);

        void getList(String gender, String type, String major, int page, int pageSize);

        void getBookInfo(String booId);

        void getSource(String bookId);

        void getChapters(String sourceId);

        void getSearchWord(String query);

        void getSearchList(String query);

        void getChapterDetails(String link);

        void save(BookInfo bookInfo, ChapterList chapterList, int index,boolean isnet);
        void save(Record record);
        void delete(String sourceId);

        void getRecords();

        void setView(View view);

        void getRecommend();
    }

}
