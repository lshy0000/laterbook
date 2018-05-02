package com.bestxty.ai.domain.interactor;

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
import com.bestxty.ai.domain.executor.BehindSchedulerProvider;
import com.bestxty.ai.domain.executor.PostExecutionThread;
import com.bestxty.ai.domain.repository.AllRepository;
import com.bestxty.ai.domain.repository.RecordRepository;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;

/**
 * Created by lshy on 2018-4-29.
 */
public class AllCase extends UseCase2 {
    final AllRepository allRepository;
    final RecordRepository recordRepository;

    @Inject
    AllCase(BehindSchedulerProvider behindSchedulerProvider, PostExecutionThread postExecutionThread, AllRepository allRepository, RecordRepository recordRepository) {
        super(behindSchedulerProvider, postExecutionThread);
        this.allRepository = allRepository;
        this.recordRepository = recordRepository;
    }

    public Observable<A> getFenleil() {
        return allRepository.getFenleil();
    }

    public Observable<D> getFenleixl() {
        return allRepository.getFenleixl();
    }

    public Observable<B> getPaihangl() {
        return allRepository.getPaihangl();
    }

    public Observable<RankList> getPaihangd(String rankId) {
        return allRepository.getPaihangd(rankId);
    }

    public Observable<E> getList(String gender, String type, String major, int page, int pageSize) {
        return allRepository.getList(gender, type, major, page, pageSize);
    }

    public Observable<BookInfo> getBookInfo(String booId) {
        return allRepository.getBookInfo(booId);
    }

    public Observable<List<Source>> getSource(String bookId) {
        return allRepository.getSource(bookId);
    }

    public Observable<ChapterList> getChapters(String bookId) {
        return recordRepository.getRecord(bookId).map(v -> {
            v.getChapterList().setRead(true);
            return v.getChapterList();
        })
                .onErrorResumeNext(allRepository.getChapters(bookId));
    }

    public Observable<F> getSearchWord(String query) {
        return allRepository.getSearchWord(query);
    }

    public Observable<SearchList> getSearchList(String query) {
        return allRepository.getSearchList(query);
    }

    public Observable<Details> getChapterDetails(String link) {
        return allRepository.getChapterDetails(link);
    }

    public Observable<Boolean> save(BookInfo bookInfo, ChapterList chapterList, int index,boolean isnet) {
        return recordRepository.save(bookInfo, chapterList, index,isnet);
    }

    public Observable<Boolean> save(Record record) {
        return recordRepository.save(record);
    }

    public Observable<Boolean> delete(String sourceId) {
        return recordRepository.delete(sourceId);
    }

    public Observable<List<Record>> getRecords() {
        return recordRepository.getRecords();
    }

    public Observable<List<BooksEntity>> getRecommmoned() {
        return allRepository.getPaihangl().flatMap(v -> allRepository.getPaihangd(v.getMale().get(0).get_id()))
                .map(r -> r.getRanking().getBooks());
    }
}
