package com.example.MovieInABox.service;

import com.example.MovieInABox.model.ApiResponse;
import com.example.MovieInABox.model.User;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;

public interface UserService {
    @FormUrlEncoded
    @POST("login")
    Call<ApiResponse<User>>
    login(@Field("email") String email,
          @Field("password") String password);


    @FormUrlEncoded
    @POST("signup")
    Call<ApiResponse>
    signup(@Field("name") String name,
           @Field("password") String password,
           @Field("email") String email,
           @Field("birthday") String birthday);


    @GET("profile")
    Call<ApiResponse<User>> getUser();

    @FormUrlEncoded
    @PUT("profile")
    Call<ApiResponse<User>>
    updateUser(@Field("name") String name,
               @Field("email") String email,
               @Field("birthday") String birthday,
               @Field("photoURL") String photoURL);

    @FormUrlEncoded
    @POST("forgot-password")
    Call<ApiResponse>
    forgotPassword(@Field("email") String email);

    @FormUrlEncoded
    @POST("verify-otp")
    Call<ApiResponse>
    verifyOtp(@Field("email") String email,
              @Field("otp") String otp);


    @FormUrlEncoded
    @POST("change-password")
    Call<ApiResponse> changePassword(
            @Header("Authorization") String token,
            @Field("newPassword") String newPassword
    );
}
