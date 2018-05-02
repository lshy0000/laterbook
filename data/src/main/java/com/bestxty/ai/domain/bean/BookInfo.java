package com.bestxty.ai.domain.bean;

import java.util.List;

import io.realm.RealmObject;
import io.realm.annotations.Ignore;

/**
 * Created by lshy on 2018-4-29.
 */

public class BookInfo extends RealmObject {

    /**
     * _id : 548d9c17eb0337ee6df738f5
     * title : 最强狂兵
     * author : 烈焰滔滔
     * longIntro : 一代兵王含恨离开部队，销声匿迹几年后，逆天强者强势回归都市，再度掀起血雨腥风！简单粗暴是我的行事艺术，不服就干是我的生活态度！看顶级狂少如何纵横都市，书写属于他的天王传奇！依旧极爽极热血！（老书《都市邪王》280万字完本，无断更记录，人品保证！群：287999620）
     * cover : /agent/http%3A%2F%2Fimg.1391.com%2Fapi%2Fv1%2Fbookcenter%2Fcover%2F1%2F683354%2F_683354_716237.jpg%2F
     * creater : Lenovo Lenovo S899t
     * majorCate : 都市
     * minorCate : 都市生活
     * hiddenPackage : []
     * apptype : [0,1,2,4]
     * rating : {"count":16585,"score":8.56,"isEffect":true}
     * hasCopyright : true
     * buytype : 0
     * sizetype : -1
     * superscript :
     * currency : 0
     * contentType : txt
     * _le : false
     * allowMonthly : true
     * allowVoucher : true
     * allowBeanVoucher : true
     * hasCp : true
     * postCount : 16124
     * latelyFollower : 147014
     * followerCount : 0
     * wordCount : 9292309
     * serializeWordCount : 6265
     * retentionRatio : 69.03
     * updated : 2018-04-28T09:33:33.462Z
     * isSerial : true
     * chaptersCount : 2877
     * lastChapter : 正文 第2877章 午夜的脚步声
     * gender : ["male"]
     * tags : []
     * advertRead : true
     * cat : 都市生活
     * donate : false
     * _gg : false
     * discount : null
     * limit : false
     */

    private String _id;
    private String title;
    private String author;
    private String longIntro;
    private String cover;
    private String creater;
    private String majorCate;
    private String minorCate;
    private RatingEntity rating;
    private boolean hasCopyright;
    private int buytype;
    private int sizetype;
    private String superscript;
    private int currency;
    private String contentType;
    private boolean _le;
    private boolean allowMonthly;
    private boolean allowVoucher;
    private boolean allowBeanVoucher;
    private boolean hasCp;
    private int postCount;
    private int latelyFollower;
    private int followerCount;
    private int wordCount;
    private int serializeWordCount;
    private String retentionRatio;
    private String updated;
    private boolean isSerial;
    private int chaptersCount;
    private String lastChapter;
    private boolean advertRead;
    private String cat;
    private boolean donate;
    private boolean _gg;
    @Ignore
    private Object discount;
    private boolean limit;
    @Ignore
    private List<Integer> apptype;
    @Ignore
    private List<String> gender;


    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getLongIntro() {
        return longIntro;
    }

    public void setLongIntro(String longIntro) {
        this.longIntro = longIntro;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public String getCreater() {
        return creater;
    }

    public void setCreater(String creater) {
        this.creater = creater;
    }

    public String getMajorCate() {
        return majorCate;
    }

    public void setMajorCate(String majorCate) {
        this.majorCate = majorCate;
    }

    public String getMinorCate() {
        return minorCate;
    }

    public void setMinorCate(String minorCate) {
        this.minorCate = minorCate;
    }

    public RatingEntity getRating() {
        return rating;
    }

    public void setRating(RatingEntity rating) {
        this.rating = rating;
    }

    public boolean isHasCopyright() {
        return hasCopyright;
    }

    public void setHasCopyright(boolean hasCopyright) {
        this.hasCopyright = hasCopyright;
    }

    public int getBuytype() {
        return buytype;
    }

    public void setBuytype(int buytype) {
        this.buytype = buytype;
    }

    public int getSizetype() {
        return sizetype;
    }

    public void setSizetype(int sizetype) {
        this.sizetype = sizetype;
    }

    public String getSuperscript() {
        return superscript;
    }

    public void setSuperscript(String superscript) {
        this.superscript = superscript;
    }

    public int getCurrency() {
        return currency;
    }

    public void setCurrency(int currency) {
        this.currency = currency;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public boolean is_le() {
        return _le;
    }

    public void set_le(boolean _le) {
        this._le = _le;
    }

    public boolean isAllowMonthly() {
        return allowMonthly;
    }

    public void setAllowMonthly(boolean allowMonthly) {
        this.allowMonthly = allowMonthly;
    }

    public boolean isAllowVoucher() {
        return allowVoucher;
    }

    public void setAllowVoucher(boolean allowVoucher) {
        this.allowVoucher = allowVoucher;
    }

    public boolean isAllowBeanVoucher() {
        return allowBeanVoucher;
    }

    public void setAllowBeanVoucher(boolean allowBeanVoucher) {
        this.allowBeanVoucher = allowBeanVoucher;
    }

    public boolean isHasCp() {
        return hasCp;
    }

    public void setHasCp(boolean hasCp) {
        this.hasCp = hasCp;
    }

    public int getPostCount() {
        return postCount;
    }

    public void setPostCount(int postCount) {
        this.postCount = postCount;
    }

    public int getLatelyFollower() {
        return latelyFollower;
    }

    public void setLatelyFollower(int latelyFollower) {
        this.latelyFollower = latelyFollower;
    }

    public int getFollowerCount() {
        return followerCount;
    }

    public void setFollowerCount(int followerCount) {
        this.followerCount = followerCount;
    }

    public int getWordCount() {
        return wordCount;
    }

    public void setWordCount(int wordCount) {
        this.wordCount = wordCount;
    }

    public int getSerializeWordCount() {
        return serializeWordCount;
    }

    public void setSerializeWordCount(int serializeWordCount) {
        this.serializeWordCount = serializeWordCount;
    }

    public String getRetentionRatio() {
        return retentionRatio;
    }

    public void setRetentionRatio(String retentionRatio) {
        this.retentionRatio = retentionRatio;
    }

    public String getUpdated() {
        return updated;
    }

    public void setUpdated(String updated) {
        this.updated = updated;
    }

    public boolean isIsSerial() {
        return isSerial;
    }

    public void setIsSerial(boolean isSerial) {
        this.isSerial = isSerial;
    }

    public int getChaptersCount() {
        return chaptersCount;
    }

    public void setChaptersCount(int chaptersCount) {
        this.chaptersCount = chaptersCount;
    }

    public String getLastChapter() {
        return lastChapter;
    }

    public void setLastChapter(String lastChapter) {
        this.lastChapter = lastChapter;
    }

    public boolean isAdvertRead() {
        return advertRead;
    }

    public void setAdvertRead(boolean advertRead) {
        this.advertRead = advertRead;
    }

    public String getCat() {
        return cat;
    }

    public void setCat(String cat) {
        this.cat = cat;
    }

    public boolean isDonate() {
        return donate;
    }

    public void setDonate(boolean donate) {
        this.donate = donate;
    }

    public boolean is_gg() {
        return _gg;
    }

    public void set_gg(boolean _gg) {
        this._gg = _gg;
    }

    public Object getDiscount() {
        return discount;
    }

    public void setDiscount(Object discount) {
        this.discount = discount;
    }

    public boolean isLimit() {
        return limit;
    }

    public void setLimit(boolean limit) {
        this.limit = limit;
    }


    public List<Integer> getApptype() {
        return apptype;
    }

    public void setApptype(List<Integer> apptype) {
        this.apptype = apptype;
    }

    public List<String> getGender() {
        return gender;
    }

    public void setGender(List<String> gender) {
        this.gender = gender;
    }


}
