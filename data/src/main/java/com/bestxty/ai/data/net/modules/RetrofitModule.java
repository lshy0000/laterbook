package com.bestxty.ai.data.net.modules;

import com.bestxty.ai.data.net.DefaultRetrofitConfigurationProvider;
import com.bestxty.ai.data.net.RetrofitConfigurationProvider;

import java.util.List;
import java.util.concurrent.Executor;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import retrofit2.CallAdapter;
import retrofit2.Converter;
import retrofit2.Retrofit;

/**
 * @author 姜泰阳
 *         Created by 姜泰阳 on 2017/10/26.
 */
@Module
public class RetrofitModule {

    String baseUrl;
    String baseUrl2;

    public RetrofitModule() {
        baseUrl2 = "http://api.zhuishushenqi.com/";
        baseUrl = "http://chapterup.zhuishushenqi.com/";
    }

    @Provides
    @Singleton
    RetrofitConfigurationProvider provideRetrofitConfigurationProvider(DefaultRetrofitConfigurationProvider configurationProvider) {
        return configurationProvider;
    }

    @Type("r1")
    @Provides
    @Singleton
    Retrofit provideRetrofit(OkHttpClient okHttpClient, RetrofitConfigurationProvider configurationProvider) {
        Retrofit.Builder builder = new Retrofit.Builder();
        builder.client(okHttpClient);
        processBuilder(baseUrl, builder, configurationProvider);
        return builder.build();
    }

    @Type("r2")
    @Provides
    @Singleton
    Retrofit provideRetrofit2(OkHttpClient okHttpClient, RetrofitConfigurationProvider configurationProvider) {
        Retrofit.Builder builder = new Retrofit.Builder();
        builder.client(okHttpClient);
        processBuilder(baseUrl2, builder, configurationProvider);
        return builder.build();
    }

    private void processBuilder(String baseUrl, Retrofit.Builder builder, RetrofitConfigurationProvider configurationProvider) {
        List<Converter.Factory> converterFactories = configurationProvider.converterFactories();
        if (converterFactories != null) {
            for (Converter.Factory converterFactory : converterFactories) {
                builder.addConverterFactory(converterFactory);
            }
        }
        List<CallAdapter.Factory> callAdapterFactories = configurationProvider.callAdapterFactories();
        if (callAdapterFactories != null) {
            for (CallAdapter.Factory callAdapterFactory : callAdapterFactories) {
                builder.addCallAdapterFactory(callAdapterFactory);
            }
        }


        if (!baseUrl.endsWith("/")) baseUrl = baseUrl + "/";
        builder.baseUrl(baseUrl);

        Executor callbackExecutor = configurationProvider.callbackExecutor();
        if (callbackExecutor != null) {
            builder.callbackExecutor(callbackExecutor);
        }
    }

}
