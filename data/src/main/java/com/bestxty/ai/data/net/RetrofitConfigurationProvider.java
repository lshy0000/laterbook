package com.bestxty.ai.data.net;

import java.util.List;
import java.util.concurrent.Executor;

import retrofit2.CallAdapter;
import retrofit2.Converter;

/**
 * @author 姜泰阳
 *         Created by 姜泰阳 on 2017/10/26.
 */

public interface RetrofitConfigurationProvider {

    List<Converter.Factory> converterFactories();

    List<CallAdapter.Factory> callAdapterFactories();

    Executor callbackExecutor();
}
