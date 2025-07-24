package com.example.MovieInABox.service;

import com.example.MovieInABox.model.ApiResponse;
import com.example.MovieInABox.model.Country;
import com.example.MovieInABox.model.Genre;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface CommonService {
    @GET("genres")
    Call<ApiResponse<List<Genre>>> getGenres();

    @GET("countries")
    Call<ApiResponse<List<Country>>> getCountries();
}
