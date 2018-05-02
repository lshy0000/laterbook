package com.lsy.laterbook;

/**
 * Created by lshy on 2018-4-29.
 */

public enum BookType {
    //    type: 热门:hot 新书:new 好评:repulation 完结: over 包月: month
    HOT("hot", "热门"), NEW("new", "新书"), REPULATION("repulation", "好评"), OVER("hot", "完结"),;
    String en;
    String cn;

    public String getEn() {
        return en;
    }

    public void setEn(String en) {
        this.en = en;
    }

    public String getCn() {
        return cn;
    }

    public void setCn(String cn) {
        this.cn = cn;
    }

    BookType(String en, String cn) {
        this.en = en;

        this.cn = cn;
    }
}
