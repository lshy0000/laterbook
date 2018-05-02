package com.bestxty.ai.domain.bean;

import io.realm.RealmObject;

public class ChaptersEntity extends RealmObject {
        /**
         * title : 正文 第001章    无限期的任务
         * link : http://vip.zhuishushenqi.com/chapter/568fef9dadb27bfb4b3a58e3?cv=1512435934020
         * id : 568fef9dadb27bfb4b3a58e3
         * chapterCover :
         * totalpage : 0
         * partsize : 0
         * order : 1
         * currency : 25
         * unreadble : false
         * isVip : false
         */

        private String title;
        private String link;
        private String id;
        private String chapterCover;
        private int totalpage;
        private int partsize;
        private int order;
        private int currency;
        private boolean unreadble;
        private boolean isVip;

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getLink() {
            return link;
        }

        public void setLink(String link) {
            this.link = link;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getChapterCover() {
            return chapterCover;
        }

        public void setChapterCover(String chapterCover) {
            this.chapterCover = chapterCover;
        }

        public int getTotalpage() {
            return totalpage;
        }

        public void setTotalpage(int totalpage) {
            this.totalpage = totalpage;
        }

        public int getPartsize() {
            return partsize;
        }

        public void setPartsize(int partsize) {
            this.partsize = partsize;
        }

        public int getOrder() {
            return order;
        }

        public void setOrder(int order) {
            this.order = order;
        }

        public int getCurrency() {
            return currency;
        }

        public void setCurrency(int currency) {
            this.currency = currency;
        }

        public boolean isUnreadble() {
            return unreadble;
        }

        public void setUnreadble(boolean unreadble) {
            this.unreadble = unreadble;
        }

        public boolean isIsVip() {
            return isVip;
        }

        public void setIsVip(boolean isVip) {
            this.isVip = isVip;
        }
    }