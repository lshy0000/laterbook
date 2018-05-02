package com.bestxty.ai.data.net.components;

import android.content.Context;

import com.bestxty.ai.data.net.modules.ApiModule;
import com.bestxty.ai.data.net.modules.ApplicationModule;
import com.bestxty.ai.data.net.modules.OkHttpModule;
import com.bestxty.ai.data.net.modules.RepositoryModule;
import com.bestxty.ai.data.net.modules.RetrofitModule;
import com.bestxty.ai.domain.executor.BehindSchedulerProvider;
import com.bestxty.ai.domain.executor.PostExecutionThread;
import com.bestxty.ai.domain.repository.AllRepository;
import com.bestxty.ai.domain.repository.RecordRepository;

import javax.inject.Singleton;

import dagger.Component;

/**
 * @author 姜泰阳
 *         Created by 姜泰阳 on 2017/10/26.//使用时注意强转。
 */
@Singleton
@Component(modules = {ApplicationModule.class, OkHttpModule.class,
        RetrofitModule.class, ApiModule.class, RepositoryModule.class})
public interface ApplicationComponent {

    Context context();

    BehindSchedulerProvider behindSchedulerProvider();

    PostExecutionThread postExecutionThread();

    AllRepository allRepository();
   RecordRepository recordRepository();

}
