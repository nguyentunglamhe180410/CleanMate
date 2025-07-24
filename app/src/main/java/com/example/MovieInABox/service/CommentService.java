package com.example.MovieInABox.service;

import com.example.MovieInABox.model.ApiResponse;
import com.example.MovieInABox.model.Comment;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface CommentService {
    @FormUrlEncoded
    @POST("movies/{movieId}/comments")
    Call<ApiResponse>
    createComment(
            @Path("movieId") Integer movieId,
            @Field("comment") String comment);

    @GET("movies/{movieId}/comments")
    Call<ApiResponse<List<Comment>>>
    getCommentByMovie(@Path("movieId") Integer movieId);
}
