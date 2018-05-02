package com.chuangfeigu.tools.common;

/**
 * Created by fro-soft on 2017-12-2.
 */
public interface Singleable<T> {
    void set(T t);

    void reset(T t);
}