package com.example.MovieInABox.service;

import com.example.MovieInABox.model.ApiResponse;
import com.example.MovieInABox.model.Rate;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface RatingService {
    @FormUrlEncoded
    @POST("movies/{movieId}/ratings")
    Call<ApiResponse>
    rateMovie(@Path("movieId") Integer movieId,
              @Field("rating") Integer rating);

    @GET("movies/{movieId}/ratings")
    Call<ApiResponse<Rate>>
    getMovieRate(@Path("movieId") Integer movieId);
}
