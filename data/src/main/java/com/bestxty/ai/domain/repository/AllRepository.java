package com.bestxty.ai.domain.repository;

import com.bestxty.ai.domain.bean.A;
import com.bestxty.ai.domain.bean.B;
import com.bestxty.ai.domain.bean.BookInfo;
import com.bestxty.ai.domain.bean.ChapterList;
import com.bestxty.ai.domain.bean.D;
import com.bestxty.ai.domain.bean.Details;
import com.bestxty.ai.domain.bean.E;
import com.bestxty.ai.domain.bean.F;
import com.bestxty.ai.domain.bean.RankList;
import com.bestxty.ai.domain.bean.SearchList;
import com.bestxty.ai.domain.bean.Source;

import java.util.List;

import io.reactivex.Observable;

/**
 * Created by lshy on 2018-4-29.
 */

public interface AllRepository {

    Observable<A> getFenleil();

    Observable<D> getFenleixl();

    Observable<B> getPaihangl();

    Observable<RankList> getPaihangd(String rankId);

    Observable<E> getList(String gender, String type, String major, int page, int pageSize);

    Observable<BookInfo> getBookInfo(String booId);

    Observable<List<Source>> getSource(String bookId);

    Observable<ChapterList> getChapters(String sourceId);

    Observable<F> getSearchWord(String query);

    Observable<SearchList> getSearchList(String query);

    Observable<Details> getChapterDetails(String link);
}
