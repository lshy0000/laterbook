package com.chuangfeigu.tools.common;

/**
 * Created by fro-soft on 2017-12-2.
 */

/**
 * 单一动作工具类
 *
 * @param <T>
 */
public class SingleAction<T> {
    public T single;

    public T getSingle() {
        return single;
    }

    public void setSingle(T single) {
        this.single = single;
    }

    public Object Targe;

    public Object getTarge() {
        return Targe;
    }

    public void setTarge(Object targe) {
        Targe = targe;
    }

    /**
     * 配合静态变量与Singleable实现只有一个T 对象执行了set方法，并在静态变量指向别处是执行reset方法
     *
     * @param t
     * @param singleable
     */
    public synchronized void doSingleAction(T t, Singleable<T> singleable) {
        if (t == null || singleable == null) {
            return;
        }
        if (single != null) {
            singleable.reset(single);
        }
        single = t;
        singleable.set(t);
    }


}
