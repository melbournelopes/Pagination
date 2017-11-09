package com.demo.coupondemo;

import android.app.Application;

import com.demo.coupondemo.commons.AppConstants;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CouponApplication extends Application {
    private static CouponApplication instance;
    private static Retrofit retrofit;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
    }

    public static CouponApplication getInstance() {
        return instance;
    }

    public static Retrofit getRetrofitInstance() {
        if (retrofit == null) {
            HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
            httpClient.connectTimeout(AppConstants.TIMEOUT, TimeUnit.SECONDS);
            httpClient.readTimeout(AppConstants.TIMEOUT, TimeUnit.SECONDS);
            httpClient.addInterceptor(interceptor);
            retrofit = new Retrofit.Builder()
                    .baseUrl(AppConstants.BASE_URL)
                    .client(httpClient.build())
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }

}
