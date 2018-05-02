package com.bestxty.ai.data.net;

import com.bestxty.ai.data.net.interceptor.MHttpLoggingInterceptor;

import java.net.ProxySelector;
import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.net.SocketFactory;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.X509TrustManager;

import okhttp3.Authenticator;
import okhttp3.CookieJar;
import okhttp3.Dns;
import okhttp3.Interceptor;
import okhttp3.logging.HttpLoggingInterceptor;

/**
 * @author 姜泰阳
 *         Created by 姜泰阳 on 2017/10/26.
 */
@Singleton
public class DefaultOkHttpConfigurationProvider implements OkHttpConfigurationProvider {

    @Inject
    public DefaultOkHttpConfigurationProvider() {
    }

    @Override
    public List<Interceptor> getInterceptors() {
        MHttpLoggingInterceptor loggingInterceptor = new MHttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        return Arrays.asList(loggingInterceptor);
    }

    @Override
    public List<Interceptor> getNetworkInterceptors() {
        return null;
    }

    @Override
    public Authenticator getAuthenticator() {
        return null;
    }

    @Override
    public Long writeTimeoutOfSecond() {
        return 10L;
    }

    @Override
    public Long readTimeoutOfSecond() {
        return 5L;
    }

    @Override
    public Long connectTimeoutOfSecond() {
        return null;
    }

    @Override
    public SSLSocketFactory sslSocketFactory() {
        return null;
    }

    @Override
    public X509TrustManager trustManager() {
        return null;
    }

    @Override
    public SocketFactory socketFactory() {
        return null;
    }

    @Override
    public Boolean retryOnConnectionFailure() {
        return null;
    }

    @Override
    public ProxySelector proxySelector() {
        return null;
    }

    @Override
    public Authenticator proxyAuthenticator() {
        return null;
    }

    @Override
    public HostnameVerifier hostnameVerifier() {
        return null;
    }

    @Override
    public Boolean followRedirects() {
        return null;
    }

    @Override
    public Boolean followSslRedirects() {
        return null;
    }

    @Override
    public Dns dns() {
        return null;
    }

    @Override
    public CookieJar cookieJar() {
        return null;
    }
}
