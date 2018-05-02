package com.lsy.laterbook;

/**
 * Created by lshy on 2018-4-29.
 */

public enum Gender {

    MALE("male", "男生小说"),
    FEMALE("female", "女生小说"),
    PICTURE("picture", "文学作品"),
    PRESS("press", "出版物"),;
    String en;
    String cn;

    Gender(String en, String cn) {
        this.en = en;
        this.cn = cn;
    }

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


    public static Gender[] genders = new Gender[]{MALE, FEMALE, PICTURE, PRESS};

    public static String getType(String ar) {
        for (Gender gender : genders) {
            if (gender.getEn().equals(ar)) return gender.getCn();
        }
        return "";
    }

}
