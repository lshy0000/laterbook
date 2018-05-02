package com.bestxty.ai.domain.bean;

import java.util.List;

/**
 * Created by lshy on 2018-4-29.
 */

public class F {

    /**
     * keywords : ["斗罗大陆","斗罗大陆II绝世唐门","斗罗大陆之元素圣灵","斗罗大陆之青莲剑仙","斗罗大陆外传神界传说","斗罗大陆3龙王传说","斗罗大陆II绝世唐门⑤（漫画版）","斗罗系统之通天之路","斗罗大陆之极限","斗罗大陆2绝世唐门"]
     * ok : true
     */

    private boolean ok;
    private List<String> keywords;

    public boolean isOk() {
        return ok;
    }

    public void setOk(boolean ok) {
        this.ok = ok;
    }

    public List<String> getKeywords() {
        return keywords;
    }

    public void setKeywords(List<String> keywords) {
        this.keywords = keywords;
    }
}
