package com.example.smartcook.api;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MLAPI {
    private ML service;
    Retrofit retrofit;

    MLAPI(){
        OkHttpClient client = new OkHttpClient.Builder()
                .build();
        retrofit =  new Retrofit.Builder()
                .baseUrl("https://python1ml2ai4app.pythonanywhere.com/")
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        service = retrofit.create(ML.class);
    }
    private static MLAPI instance = new MLAPI();
    public static MLAPI getInstance(){
        return instance;
    }

    public ML getService() {
        return service;
    }
}