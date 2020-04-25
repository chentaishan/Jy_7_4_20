package com.example.jy_7_4_20.net;

import android.util.Log;

import com.example.jy_7_4_20.MyApp;
import com.example.jy_7_4_20.utils.NetCheckUtils;

import java.io.IOException;

import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class HttpManager {
    private static HttpManager httpManager;

    private HttpManager() {
    }

    // 双重检查锁模式 （DCL）double check locked
    public static HttpManager getInstance() {

        if (httpManager == null) {

            synchronized (HttpManager.class) {

                if (httpManager == null) {
                    httpManager = new HttpManager();
                }
            }
        }

        return httpManager;
    }

    public <T> T getApiService(String url, Class<T> tClass) {

        return new Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                //配置okhttpclient
                .client(okHttpClient())
                .build().create(tClass);

    }


    class LoggingInterceptor implements Interceptor {


        @Override
        public Response intercept(Chain chain) throws IOException {

            long start = System.nanoTime();
            Request request = chain.request();
            Response proceed = chain.proceed(request);
            long end = System.nanoTime();


            Log.i("Request:", String.format("Sending request %s on %s%n%s", request.url(), chain.connection(), request.headers()));

            Log.i("interceptor", "time=" + (end - start) + "ms");
            return proceed;
        }
    }

    private OkHttpClient okHttpClient() {

        final OkHttpClient build = new OkHttpClient.Builder()
                //配置缓存目录
                .cache(new Cache(MyApp.context.getCacheDir(), 10 * 1024 * 1024))
                // 配置日志拦截器
                .addInterceptor(new LoggingInterceptor())
                //配置缓存拦截器
                .addNetworkInterceptor(new NetWorkInterceptor())
                .build();

        return build;
    }

    class NetWorkInterceptor implements Interceptor {
        Request request;

        @Override
        public Response intercept(Chain chain) throws IOException {

            request = chain.request();
            if (!NetCheckUtils.checkNetWork()) {
                request.newBuilder().cacheControl(CacheControl.FORCE_CACHE).build();
            }

            Response response = chain.proceed(request);
            if (!NetCheckUtils.checkNetWork()) {

                int maxAge = 0;
                return response.newBuilder().removeHeader("Pragma").header("Cache-Control", "public ,max-age=" + maxAge).build();

            } else {

                int maxTime = 60 * 60 * 24;

                return response.newBuilder().removeHeader("Pragma").header("Cache-Control", "public ,max-age=" + maxTime).build();

            }


        }
    }

}
