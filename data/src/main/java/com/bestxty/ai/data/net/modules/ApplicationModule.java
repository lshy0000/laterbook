package com.bestxty.ai.data.net.modules;

import android.app.Application;
import android.content.Context;

import com.bestxty.ai.data.executor.IoSchedulerProvider;
import com.bestxty.ai.data.executor.UIThread;
import com.bestxty.ai.data.repository.AllDataRepository;
import com.bestxty.ai.data.repository.RecordDataRepository;
import com.bestxty.ai.domain.executor.BehindSchedulerProvider;
import com.bestxty.ai.domain.executor.PostExecutionThread;
import com.bestxty.ai.domain.repository.AllRepository;
import com.bestxty.ai.domain.repository.RecordRepository;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * @author 姜泰阳
 *         Created by 姜泰阳 on 2017/10/26.
 */
@Module
public class ApplicationModule {
    private final Application application;

    public ApplicationModule(Application application) {
        this.application = application;
    }


    @Provides
    @Singleton
    Context provideAppContext() {
        return this.application;
    }


    @Provides
    @Singleton
    BehindSchedulerProvider provideBehindSchedulerProvider(IoSchedulerProvider ioSchedulerProvider) {
        return ioSchedulerProvider;
    }

    @Provides
    @Singleton
    PostExecutionThread providePostExecutionThread(UIThread uiThread) {
        return uiThread;
    }


    @Provides
    @Singleton
    AllRepository provideAllRepository(AllDataRepository identifyDataResponse) {
        return identifyDataResponse;
    }

    @Provides
    @Singleton
    RecordRepository provideRecordRepository(RecordDataRepository identifyDataResponse) {
        return identifyDataResponse;
    }
}
