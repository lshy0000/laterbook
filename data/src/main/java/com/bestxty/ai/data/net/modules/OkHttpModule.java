package com.bestxty.ai.data.net.modules;

import com.bestxty.ai.data.net.DefaultOkHttpConfigurationProvider;
import com.bestxty.ai.data.net.OkHttpConfigurationProvider;
import com.chuangfeigu.tools.app.App;

import java.net.ProxySelector;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.inject.Singleton;
import javax.net.SocketFactory;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.X509TrustManager;

import dagger.Module;
import dagger.Provides;
import okhttp3.Authenticator;
import okhttp3.Cache;
import okhttp3.CookieJar;
import okhttp3.Dns;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;

/**
 * @author 姜泰阳
 *         Created by 姜泰阳 on 2017/10/26.
 */
@Module
public class OkHttpModule {

    @Provides
    @Singleton
    OkHttpConfigurationProvider provideOkHttpConfigurationProvider(DefaultOkHttpConfigurationProvider configurationProvider) {
        return configurationProvider;
    }


    @Provides
    @Singleton
    OkHttpClient provideOkHttpClient(OkHttpConfigurationProvider configurationProvider) {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        processBuilder(builder, configurationProvider);
        return builder.build();

    }

    private void processBuilder(OkHttpClient.Builder builder, OkHttpConfigurationProvider configurationProvider) {

        List<Interceptor> interceptors = configurationProvider.getInterceptors();
        if (interceptors != null) {
            for (Interceptor interceptor : interceptors) {
                builder.addInterceptor(interceptor);
            }
        }

        List<Interceptor> networkInterceptors = configurationProvider.getNetworkInterceptors();
        if (networkInterceptors != null) {
            for (Interceptor networkInterceptor : networkInterceptors) {
                builder.addNetworkInterceptor(networkInterceptor);
            }
        }
        Authenticator authenticator = configurationProvider.getAuthenticator();
        if (authenticator != null) {
            builder.authenticator(authenticator);
        }
        Long writeTimeOut = configurationProvider.writeTimeoutOfSecond();
        if (writeTimeOut != null) {
            builder.writeTimeout(writeTimeOut, TimeUnit.SECONDS);
        }
        Long readTimeOut = configurationProvider.readTimeoutOfSecond();
        if (readTimeOut != null) {
            builder.readTimeout(readTimeOut, TimeUnit.SECONDS);
        }
        Long connectTimeOut = configurationProvider.connectTimeoutOfSecond();
        if (connectTimeOut != null) {
            builder.connectTimeout(connectTimeOut, TimeUnit.SECONDS);
        }
        SSLSocketFactory sslSocketFactory = configurationProvider.sslSocketFactory();
        X509TrustManager trustManager = configurationProvider.trustManager();
        if (sslSocketFactory != null && trustManager != null) {
            builder.sslSocketFactory(sslSocketFactory, trustManager);
        }
        if (sslSocketFactory != null && trustManager == null) {
            builder.sslSocketFactory(sslSocketFactory);
        }

        SocketFactory socketFactory = configurationProvider.socketFactory();
        if (socketFactory != null) {
            builder.socketFactory(socketFactory);
        }

        Boolean retryOnConnectionFailure = configurationProvider.retryOnConnectionFailure();
        if (retryOnConnectionFailure != null) {
            builder.retryOnConnectionFailure(retryOnConnectionFailure);
        }
        ProxySelector proxySelector = configurationProvider.proxySelector();
        if (proxySelector != null) {
            builder.proxySelector(proxySelector);
        }
        Authenticator proxyAuthenticator = configurationProvider.proxyAuthenticator();
        if (proxyAuthenticator != null) {
            builder.proxyAuthenticator(proxyAuthenticator);
        }

        HostnameVerifier hostnameVerifier = configurationProvider.hostnameVerifier();
        if (hostnameVerifier != null) {
            builder.hostnameVerifier(hostnameVerifier);
        }
        Boolean followRedirects = configurationProvider.followRedirects();
        if (followRedirects != null) {
            builder.followRedirects(followRedirects);
        }
        Boolean followSslRedirects = configurationProvider.followSslRedirects();
        if (followSslRedirects != null) {
            builder.followSslRedirects(followSslRedirects);
        }
        Dns dns = configurationProvider.dns();
        if (dns != null) {
            builder.dns(dns);
        }
        builder.cache(new Cache(App.getApp().getCacheDir().getAbsoluteFile(), 10 * 1024 * 1024));

        CookieJar cookieJar = configurationProvider.cookieJar();
        if (cookieJar != null) {
            builder.cookieJar(cookieJar);
        }
    }

}
