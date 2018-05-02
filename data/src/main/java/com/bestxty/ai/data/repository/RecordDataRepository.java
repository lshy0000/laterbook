package com.bestxty.ai.data.repository;

import com.bestxty.ai.data.repository.datasource.RecordDiskStore;
import com.bestxty.ai.domain.bean.BookInfo;
import com.bestxty.ai.domain.bean.ChapterList;
import com.bestxty.ai.domain.bean.Record;
import com.bestxty.ai.domain.repository.RecordRepository;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Observable;

/**
 * Created by lshy on 2018-4-29.
 */
@Singleton
public class RecordDataRepository implements RecordRepository {
    final RecordDiskStore recordStore;

    @Inject
    public RecordDataRepository(RecordDiskStore recordStore) {
        this.recordStore = recordStore;
    }

    @Override
    public Observable<Boolean> save(BookInfo bookInfo, ChapterList chapterList, int index,boolean isnet) {
        return recordStore.save(bookInfo, chapterList, index,isnet);
    }

    @Override
    public Observable<Boolean> delete(String sourceId) {
        return recordStore.delete(sourceId);
    }

    @Override
    public Observable<List<Record>> getRecords() {
        return recordStore.getRecords();
    }

    @Override
    public Observable<Record> getRecord(String sourceId) {
        return recordStore.getRecord(sourceId);
    }

    @Override
    public Observable<Boolean> save(Record sourceId) {
        return recordStore.save(sourceId);
    }

}
