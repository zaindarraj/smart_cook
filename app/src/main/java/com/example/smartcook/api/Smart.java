package com.example.smartcook.api;

import com.example.smartcook.admin.DeletePostResponse;
import com.example.smartcook.admin.DeleteUserResponse;
import com.example.smartcook.home.ml.ReciepeResponse;
import com.example.smartcook.home.post.PostResponse;
import com.example.smartcook.home.post.PostsResponse;
import com.example.smartcook.admin.UsersResponse;
import com.example.smartcook.registeration.RegisterResponse;
import com.example.smartcook.registeration.login.ResetResponse;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface Smart {
    @FormUrlEncoded
    @POST("api.php")
    Call<RegisterResponse> login(@Field("email") String email, @Field("password") String password,
                                 @Field("query") String query);
    @FormUrlEncoded
    @POST("api.php")
    Call<RegisterResponse> logup(@Field("email") String email, @Field("password") String password,
                                 @Field("phone") String phone,
                                 @Field("name") String name,
                                 @Field("query") String query);
    @FormUrlEncoded
    @POST("api.php")
    Call<PostsResponse> getPosts(@Field("query") String query);
    @FormUrlEncoded
    @POST("api.php")
    Call<UsersResponse> getUsers(@Field("query") String query);
    @FormUrlEncoded
    @POST("api.php")
    Call<PostResponse> addPost(@Field("userID") int userID, @Field("postText") String text,
                               @Field("query") String query);
    @FormUrlEncoded
    @POST("api.php")
    Call<DeletePostResponse> deletePost(@Field("postID") int postID,
                                        @Field("query") String query);

    @FormUrlEncoded
    @POST("api.php")
    Call<DeleteUserResponse> deleteUser(@Field("userID") int userID,
                                        @Field("query") String query);
    @FormUrlEncoded
    @POST("api.php")
    Call<PostResponse> reply(@Field("userID") int userID, @Field("postID") int postID,
                             @Field("reply") String text,
                             @Field("query") String query);

    @FormUrlEncoded
    @POST("api.php")
    Call<ReciepeResponse> getRecipe(@Field("recipeID") int recipeID,@Field("query") String query);

    @FormUrlEncoded
    @POST("api.php")
    Call<ResetResponse> reset(@Field("email") String email, @Field("query") String query);

}
