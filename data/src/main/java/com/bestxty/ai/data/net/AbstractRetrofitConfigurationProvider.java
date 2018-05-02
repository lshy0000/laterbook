package com.bestxty.ai.data.net;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;

import retrofit2.CallAdapter;
import retrofit2.Converter;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

/**
 * @author 姜泰阳
 *         Created by 姜泰阳 on 2017/10/26.
 */

public abstract class AbstractRetrofitConfigurationProvider implements RetrofitConfigurationProvider {

    @Override
    public List<Converter.Factory> converterFactories() {
        List<Converter.Factory> factories = new ArrayList<>();
        factories.add(GsonConverterFactory.create());
        factories.add(ScalarsConverterFactory.create());
        return factories;
    }

    @Override
    public List<CallAdapter.Factory> callAdapterFactories() {
        List<CallAdapter.Factory> factories = new ArrayList<>();
        factories.add(RxJava2CallAdapterFactory.create());
        return factories;
    }

    @Override
    public Executor callbackExecutor() {
        return null;
    }
}
