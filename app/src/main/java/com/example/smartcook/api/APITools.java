package com.example.smartcook.api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class APITools {
    private Smart service;
    Retrofit retrofit;

    public static String url ="https://copacetic-sets.000webhostapp.com/";




    APITools(){
        OkHttpClient client = new OkHttpClient.Builder()
               .build();
        retrofit =  new Retrofit.Builder()
                .baseUrl("https://copacetic-sets.000webhostapp.com/")
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        service = retrofit.create(Smart.class);
    }
    private static APITools instance = new APITools();
    public static APITools getInstance(){
        return instance;
    }

    public Smart getService() {
        return service;
    }
}