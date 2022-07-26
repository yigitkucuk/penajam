package com.example.penajamm.news;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface NewsApiInterface {
    @GET("everything")
    Call<News> getNews(
            @Query("from") String date,
            @Query("domains") String domain,
            @Query("apiKey") String apiKey
    );
}
