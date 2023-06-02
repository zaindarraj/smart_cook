package com.example.smartcook.registeration.logup;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.smartcook.accountmanager.AccountManager;
import com.example.smartcook.api.APITools;
import com.example.smartcook.registeration.RegisterResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LogupViewModel extends ViewModel {
    String email;
    String name;
    String pn;
    String password;
    Context context;
    MutableLiveData<LogupResult> logupResult = new MutableLiveData<LogupResult>(LogupResult.Init);
    enum LogupResult{
        Success,
        Failure,
        EmailExist,
        Wait,
        Init
    };

    void logup(Context context){
        logupResult.setValue(LogupResult.Wait);
       Call<RegisterResponse> call = APITools.getInstance().getService().logup(email,password,pn, name,
               "logup");
       call.enqueue(new Callback<RegisterResponse>() {
           @Override
           public void onResponse(Call<RegisterResponse> call, Response<RegisterResponse> response) {
               if(response.isSuccessful()){
                   if(response.body().code == 1){
                       Bundle data = new Bundle();
                       data.putString("userID" , String.valueOf(response.body().userID));
                       AccountManager.getInstance().addAccount(
                               email,password,context, data
                       );
                       logupResult.setValue(LogupResult.Success);
                   }else{
                       logupResult.setValue(LogupResult.EmailExist);
                   }
               }else{
                   logupResult.setValue(LogupResult.Failure);
               }

           }

           @Override
           public void onFailure(Call<RegisterResponse> call, Throwable t) {
               Log.println(Log.ASSERT , "1" , t.toString());
                logupResult.setValue(LogupResult.Failure);
           }
       });
    }



}
