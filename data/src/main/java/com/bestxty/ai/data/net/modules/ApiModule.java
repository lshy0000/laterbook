package com.bestxty.ai.data.net.modules;

import com.bestxty.ai.data.net.api.DetailsApi;
import com.bestxty.ai.data.net.api.SoureApi;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

/**
 * @author 姜泰阳
 *         Created by 姜泰阳 on 2017/10/26.
 */

@Module
public class ApiModule {

    @Provides
    @Singleton
    SoureApi provideSoureApi(@Type("r2") Retrofit retrofit) {
        return retrofit.create(SoureApi.class);
    }

    @Provides
    @Singleton
    DetailsApi provideDetailsApi(@Type("r1") Retrofit retrofit) {
        return retrofit.create(DetailsApi.class);
    }
}
