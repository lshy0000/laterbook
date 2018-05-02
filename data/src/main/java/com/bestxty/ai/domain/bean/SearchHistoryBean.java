package com.bestxty.ai.domain.bean;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by lshy on 2018-2-2.
 */

public class SearchHistoryBean extends RealmObject {
    @PrimaryKey
    String word;

    public SearchHistoryBean() {
        super();
    }

    public SearchHistoryBean(String word) {
        super();
        this.word = word;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }
}
