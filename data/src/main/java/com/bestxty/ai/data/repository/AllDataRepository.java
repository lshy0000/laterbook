package com.bestxty.ai.data.repository;

import com.bestxty.ai.data.net.api.DetailsApi;
import com.bestxty.ai.data.net.api.SoureApi;
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
import com.bestxty.ai.domain.repository.AllRepository;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Observable;

/**
 * Created by lshy on 2018-4-29.
 */
@Singleton
public class AllDataRepository implements AllRepository {
    final SoureApi soureApi;
    final DetailsApi detailsApi;


    @Inject
    public AllDataRepository(SoureApi soureApi, DetailsApi detailsApi) {
        this.soureApi = soureApi;
        this.detailsApi = detailsApi;

    }

    @Override
    public Observable<A> getFenleil() {
        return soureApi.getFenleil();
    }

    @Override
    public Observable<D> getFenleixl() {
        return soureApi.getFenleixl();
    }

    @Override
    public Observable<B> getPaihangl() {
        return soureApi.getPaihangl();
    }

    @Override
    public Observable<RankList> getPaihangd(String rankId) {
        return soureApi.getPaihangd(rankId);
    }

    @Override
    public Observable<E> getList(String gender, String type, String major, int page, int pageSize) {
        return soureApi.getList(gender, type, major, page * pageSize, pageSize);
    }


    @Override
    public Observable<BookInfo> getBookInfo(String booId) {
        return soureApi.getBookInfo(booId);
    }

    @Override
    public Observable<List<Source>> getSource(String bookId) {
        return soureApi.getSource("summary", bookId);
    }

    @Override
    public Observable<ChapterList> getChapters(String bookId) {
        return soureApi.getChapters(bookId, "chapters");
    }

    @Override
    public Observable<F> getSearchWord(String query) {
        return soureApi.getSearchWord(query);
    }

    @Override
    public Observable<SearchList> getSearchList(String query) {
        return soureApi.getSearchList(query);
    }

    @Override
    public Observable<Details> getChapterDetails(String link) {
        return detailsApi.getChapterDetails(link);
    }

}
