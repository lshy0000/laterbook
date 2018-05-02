package com.bestxty.ai.data.executor;

import com.bestxty.ai.domain.executor.BehindSchedulerProvider;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Scheduler;
import io.reactivex.schedulers.Schedulers;

/**
 * @author 姜泰阳
 *         Created by 姜泰阳 on 2017/10/26.
 */
@Singleton
public final class IoSchedulerProvider implements BehindSchedulerProvider {

    @Inject
    IoSchedulerProvider() {
    }

    @Override
    public Scheduler getScheduler() {
        return Schedulers.io();
    }
}
