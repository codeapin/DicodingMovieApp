package com.codeapin.dicodingmovieapp.util;


import com.codeapin.dicodingmovieapp.data.model.APIResponse;

import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiService {

    @GET("3/movie/popular?language=en-US&page=1")
    Single<APIResponse> getMovies();

    @GET("3/search/movie?language=en-US&spider&page=1&include_adult=false")
    Single<APIResponse> getMovies(@Query("query") String q);
}
