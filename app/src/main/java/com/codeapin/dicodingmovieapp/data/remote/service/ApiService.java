package com.codeapin.dicodingmovieapp.data.remote.service;


import com.codeapin.dicodingmovieapp.data.remote.model.ApiResponse;

import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiService {

    @GET("3/movie/popular?language=en-US&page=1")
    Single<ApiResponse> getMovies();

    @GET("3/search/movie?language=en-US&spider&page=1&include_adult=false")
    Single<ApiResponse> getMovies(@Query("query") String q);

    @GET("3/movie/upcoming?language=en-US&page=1")
    Single<ApiResponse> getUpcomingMovies();

    @GET("3/movie/now_playing?language=en-US&page=1")
    Single<ApiResponse> getNowPlayingMovies();
}
