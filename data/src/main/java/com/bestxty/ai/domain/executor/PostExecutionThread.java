package com.bestxty.ai.domain.executor;

import io.reactivex.Scheduler;

/**
 * @author 姜泰阳
 *         Created by 姜泰阳 on 2017/10/26.
 */

public interface PostExecutionThread {

    Scheduler getScheduler();
}
