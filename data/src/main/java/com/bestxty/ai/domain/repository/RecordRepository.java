package com.bestxty.ai.domain.repository;

import com.bestxty.ai.domain.bean.BookInfo;
import com.bestxty.ai.domain.bean.ChapterList;
import com.bestxty.ai.domain.bean.Record;

import java.util.List;

import io.reactivex.Observable;

/**
 * Created by lshy on 2018-4-29.
 */

public interface RecordRepository {
    Observable<Boolean> save(BookInfo bookInfo, ChapterList chapterList, int index,boolean isnet);

    Observable<Boolean> delete(String sourceId);

    Observable<List<Record>> getRecords();

    Observable<Record> getRecord(String sourceId);

    Observable<Boolean> save(Record sourceId);


}
