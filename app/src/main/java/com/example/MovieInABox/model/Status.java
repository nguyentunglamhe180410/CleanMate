package com.example.MovieInABox.model;

import com.google.gson.annotations.SerializedName;

public enum Status {
    @SerializedName("Success")
    SUCCESS,
    @SerializedName("Error")
    ERROR
}
