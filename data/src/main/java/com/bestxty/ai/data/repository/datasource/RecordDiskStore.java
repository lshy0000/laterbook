package com.bestxty.ai.data.repository.datasource;

import com.bestxty.ai.domain.bean.BookInfo;
import com.bestxty.ai.domain.bean.ChapterList;
import com.bestxty.ai.domain.bean.Record;
import com.bestxty.ai.domain.repository.RecordRepository;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Observable;
import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by lshy on 2018-4-29.
 */
@Singleton
public class RecordDiskStore implements RecordRepository {
    @Inject
    public RecordDiskStore() {
    }

    @Override
    public Observable<Boolean> save(BookInfo bookInfo, ChapterList chapterList, int index, boolean isnet) {
        return Observable.create(emitter -> {
            Boolean token = null;
            try (Realm realm = Realm.getDefaultInstance()) {
                realm.beginTransaction();
                Record person = new Record();
                chapterList.setRead(true);
                person.setBookInfo(bookInfo);
                person.setIsnet(isnet);
                person.setChapterList(chapterList);
                person.setReadIndex(index);
                person.setSourceId(chapterList.get_id());
                realm.copyToRealmOrUpdate(person);//传入对象
                token = true;
                realm.commitTransaction();
            }
            if (token == null) {
                emitter.onError(new NullPointerException("realm is null"));
                return;
            }
            emitter.onNext(token);
            emitter.onComplete();
        });
    }

    @Override
    public Observable<Boolean> delete(String sourceId) {
        return Observable.create(emitter -> {
            Boolean token = null;
            try (Realm realm = Realm.getDefaultInstance()) {
                realm.beginTransaction();
                Record savedToken = realm.where(Record.class).equalTo("sourceId", sourceId).findFirst();
                if (savedToken != null) {
                    savedToken.deleteFromRealm();
                    token = true;
                }
                realm.commitTransaction();
            }
            if (token == null) {
                emitter.onError(new NullPointerException("realm is null"));
                return;
            }
            emitter.onNext(token);
            emitter.onComplete();
        });
    }

    @Override
    public Observable<List<Record>> getRecords() {
        return Observable.create(emitter -> {
            List<Record> token = null;
            try (Realm realm = Realm.getDefaultInstance()) {
                RealmResults<Record> savedToken = realm.where(Record.class).findAll();
                if (savedToken != null) {
                    token = realm.copyFromRealm(savedToken);
                }
            }
            if (token == null) {
                emitter.onError(new NullPointerException("realm is null"));
                return;
            }
            emitter.onNext(token);
            emitter.onComplete();
        });
    }

    @Override
    public Observable<Record> getRecord(String sourceId) {
        return Observable.create(emitter -> {
            Record token = null;
            try (Realm realm = Realm.getDefaultInstance()) {
                Record savedToken = realm.where(Record.class).equalTo("sourceId", sourceId).findFirst();
                if (savedToken != null) {
                    token = realm.copyFromRealm(savedToken);
                }
            }
            if (token == null) {
                emitter.onError(new NullPointerException("realm is null"));
                return;
            }
            emitter.onNext(token);
            emitter.onComplete();
        });
    }

    @Override
    public Observable<Boolean> save(Record sourceId) {
        return Observable.create(emitter -> {
            Boolean token = null;
            try (Realm realm = Realm.getDefaultInstance()) {
                realm.beginTransaction();
                sourceId.getChapterList().setRead(true);
                realm.copyToRealmOrUpdate(sourceId);//传入对象
                token = true;
                realm.commitTransaction();
            }
            if (token == null) {
                emitter.onError(new NullPointerException("realm is null"));
                return;
            }
            emitter.onNext(token);
            emitter.onComplete();
        });
    }
}
