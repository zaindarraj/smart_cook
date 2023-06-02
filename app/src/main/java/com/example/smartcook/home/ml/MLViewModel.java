package com.example.smartcook.home.ml;


import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.smartcook.api.APITools;
import com.example.smartcook.api.ML;
import com.example.smartcook.api.MLAPI;
import com.example.smartcook.classes.Reciepe;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MLViewModel extends ViewModel {
    String ings;

    enum Result{
        Success,
        Failure
    }

    MutableLiveData<Result> predictionResult = new MutableLiveData<>();
    MutableLiveData<Result> getRecipeResult = new MutableLiveData<>();
    Reciepe reciepe;
    int recipeId;


    void predict(){
       Call<RecipeIDResponse> call =  MLAPI.getInstance().getService().predict(ings);
       call.enqueue(new Callback<RecipeIDResponse>() {
           @Override
           public void onResponse(Call<RecipeIDResponse> call, Response<RecipeIDResponse> response) {
               if(response.isSuccessful()){
                   RecipeIDResponse recipeIDResponse = response.body();
                   Log.println(Log.ASSERT,"1", response.body().id.toString());
                   assert recipeIDResponse != null;
                   if(recipeIDResponse.id != null){
                       recipeId =recipeIDResponse.id;

                       predictionResult.setValue(Result.Success);
                   }else{

                       predictionResult.setValue(Result.Failure);
                   }

               }else{

                   predictionResult.setValue(Result.Failure);

               }
           }

           @Override
           public void onFailure(Call<RecipeIDResponse> call, Throwable t) {

               predictionResult.setValue(Result.Failure);
           }
       });
    }


    void getRecipe(){
        Call<ReciepeResponse> call =  APITools.getInstance().getService().getRecipe(recipeId,"getRecipe");
        call.enqueue(new Callback<ReciepeResponse>() {
            @Override
            public void onResponse(Call<ReciepeResponse> call, Response<ReciepeResponse> response) {
                if(response.isSuccessful()){
                    ReciepeResponse body = response.body();
                        reciepe = body.reciepe;
                        getRecipeResult.setValue(Result.Success);
                }else{
                    getRecipeResult.setValue(Result.Failure);
                }
            }

            @Override
            public void onFailure(Call<ReciepeResponse> call, Throwable t) {
                getRecipeResult.setValue(Result.Failure);
                Log.println(Log.ASSERT,"1",t.toString());
            }
        });
    }

}
