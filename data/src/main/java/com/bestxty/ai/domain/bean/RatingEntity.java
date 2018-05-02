package com.bestxty.ai.domain.bean;

import io.realm.RealmObject;

public class RatingEntity extends RealmObject{
        /**
         * count : 16585
         * score : 8.56
         * isEffect : true
         */

        private int count;
        private double score;
        private boolean isEffect;

        public int getCount() {
            return count;
        }

        public void setCount(int count) {
            this.count = count;
        }

        public double getScore() {
            return score;
        }

        public void setScore(double score) {
            this.score = score;
        }

        public boolean isIsEffect() {
            return isEffect;
        }

        public void setIsEffect(boolean isEffect) {
            this.isEffect = isEffect;
        }
    }