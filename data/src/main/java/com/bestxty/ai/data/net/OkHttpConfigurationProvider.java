package com.bestxty.ai.data.net;

import java.net.ProxySelector;
import java.util.List;

import javax.net.SocketFactory;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.X509TrustManager;

import okhttp3.Authenticator;
import okhttp3.CookieJar;
import okhttp3.Dns;
import okhttp3.Interceptor;

/**
 * @author 姜泰阳
 *         Created by 姜泰阳 on 2017/10/26.
 */

public interface OkHttpConfigurationProvider {

    List<Interceptor> getInterceptors();

    List<Interceptor> getNetworkInterceptors();

    Authenticator getAuthenticator();

    Long writeTimeoutOfSecond();

    Long readTimeoutOfSecond();

    Long connectTimeoutOfSecond();

    SSLSocketFactory sslSocketFactory();

    X509TrustManager trustManager();

    SocketFactory socketFactory();

    Boolean retryOnConnectionFailure();

    ProxySelector proxySelector();

    Authenticator proxyAuthenticator();

    HostnameVerifier hostnameVerifier();

    Boolean followRedirects();

    Boolean followSslRedirects();

    Dns dns();

    CookieJar cookieJar();

}
