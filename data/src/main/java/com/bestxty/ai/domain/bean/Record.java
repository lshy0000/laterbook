package com.bestxty.ai.domain.bean;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by lshy on 2018-4-29.
 */

public class Record extends RealmObject {

    @PrimaryKey
    String sourceId;
    BookInfo bookInfo;
    ChapterList chapterList;
    int readIndex;

    public boolean isIsnet() {
        return isnet;
    }

    public void setIsnet(boolean isnet) {
        this.isnet = isnet;
    }

    boolean isnet;

    public String getSourceId() {
        return sourceId;
    }

    public void setSourceId(String sourceId) {
        this.sourceId = sourceId;
    }

    public BookInfo getBookInfo() {
        return bookInfo;
    }

    public void setBookInfo(BookInfo bookInfo) {
        this.bookInfo = bookInfo;
    }

    public ChapterList getChapterList() {
        return chapterList;
    }

    public void setChapterList(ChapterList chapterList) {
        this.chapterList = chapterList;
    }

    public int getReadIndex() {
        return readIndex;
    }

    public void setReadIndex(int readIndex) {
        this.readIndex = readIndex;
    }
}
