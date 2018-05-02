package com.bestxty.ai.domain.bean;

import io.realm.RealmList;
import io.realm.RealmObject;

/**
 * Created by lshy on 2018-4-29.
 */

public class ChapterList extends RealmObject {


    /**
     * _id : 568fef99adb27bfb4b3a58dc
     * source : zhuishuvip
     * name : 优质书源
     * link : http://vip.zhuishushenqi.com/toc/568fef99adb27bfb4b3a58dc
     * book : 548d9c17eb0337ee6df738f5
     * chapters : [{"title":"正文 第001章    无限期的任务","link":"http://vip.zhuishushenqi.com/chapter/568fef9dadb27bfb4b3a58e3?cv=1512435934020","id":"568fef9dadb27bfb4b3a58e3","chapterCover":"","totalpage":0,"partsize":0,"order":1,"currency":25,"unreadble":false,"isVip":false},{"title":"正文 第002章    美女集团","link":"http://vip.zhuishushenqi.com/chapter/568fef9dadb27bfb4b3a58e6?cv=1495873183770","id":"568fef9dadb27bfb4b3a58e6","chapterCover":"","totalpage":0,"partsize":0,"order":2,"currency":15,"unreadble":false,"isVip":false},{"title":"正文 第003章    窃听风云","link":"http://vip.zhuishushenqi.com/chapter/568fef9eadb27bfb4b3a58e9?cv=1495873230240","id":"568fef9eadb27bfb4b3a58e9","chapterCover":"","totalpage":0,"partsize":0,"order":3,"currency":15,"unreadble":false,"isVip":false},{"title":"正文 第004章   维多利亚","link":"http://vip.zhuishushenqi.com/chapter/568fef9eadb27bfb4b3a58ec?cv=1486268627313","id":"568fef9eadb27bfb4b3a58ec","chapterCover":"","totalpage":0,"partsize":0,"order":4,"currency":20,"unreadble":false,"isVip":false}]
     * updated : 2018-04-28T09:33:33.462Z
     * host : vip.zhuishushenqi.com
     */
    private boolean read;

    public boolean getRead() {
        return read;
    }

    public void setRead(boolean read) {
        this.read = read;
    }

    private String _id;
    private String source;
    private String name;
    private String link;
    private String book;
    private String updated;
    private String host;
    private RealmList<ChaptersEntity> chapters;

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getBook() {
        return book;
    }

    public void setBook(String book) {
        this.book = book;
    }

    public String getUpdated() {
        return updated;
    }

    public void setUpdated(String updated) {
        this.updated = updated;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public RealmList<ChaptersEntity> getChapters() {
        return chapters;
    }

    public void setChapters(RealmList<ChaptersEntity> chapters) {
        this.chapters = chapters;
    }


}
