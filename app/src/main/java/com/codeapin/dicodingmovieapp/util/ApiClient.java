package com.codeapin.dicodingmovieapp.util;


import com.codeapin.dicodingmovieapp.BuildConfig;

import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {

    private static final String BASE_URL = "https://api.themoviedb.org/";
    public static final String BASE_IMAGE_URL_W185 = "http://image.tmdb.org/t/p/w185";
    public static final String BASE_IMAGE_URL_W500 = "http://image.tmdb.org/t/p/w500";
    private static ApiService sApiService;

    public static ApiService getApiService() {
        if (sApiService == null) {

            HttpLoggingInterceptor logging = new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY);
            OkHttpClient client = new OkHttpClient.Builder()
                    .addInterceptor(logging)
                    .addInterceptor(chain -> {
                        Request original = chain.request();
                        HttpUrl url = original.url().newBuilder()
                                .setQueryParameter("api_key", BuildConfig.MOVIE_DB_API_KEY)
                                .build();
                        Request request = original.newBuilder().url(url).build();
                        return chain.proceed(request);
                    }).build();

            sApiService = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .client(client)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .build().create(ApiService.class);
        }
        return sApiService;
    }
}
