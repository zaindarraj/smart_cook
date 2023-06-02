package com.example.smartcook.api;

import com.example.smartcook.home.ml.RecipeIDResponse;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface ML {
    @FormUrlEncoded
    @POST("predict")
    Call<RecipeIDResponse> predict(@Field("string") String string);
}
