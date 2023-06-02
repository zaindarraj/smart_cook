package com.example.smartcook.registeration.login;

import android.content.Context;
import android.os.Bundle;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.smartcook.accountmanager.AccountManager;
import com.example.smartcook.api.APITools;
import com.example.smartcook.registeration.RegisterResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginViewModel extends ViewModel {
    public Context context;

    public enum LogOutResult{
        Success,
        Failure
    }
    enum LoginResult {
        Success,
        WrongInfo,
        Failure,
        Init,
        Waiting,
    }

    MutableLiveData<LoginResult> loginResult = new MutableLiveData<LoginResult>(LoginResult.Init);
    public MutableLiveData<LogOutResult> logOutResultMutableLiveData = new MutableLiveData<>();
    String email;
    String password;


    public boolean logOut(){
        if (AccountManager.getManager(context).removeAccountExplicitly(
                AccountManager.getInstance().getLoggedAccount(context)
        )){
            logOutResultMutableLiveData.setValue(LogOutResult.Success);
            return true;
        }else{
            logOutResultMutableLiveData.setValue(LogOutResult.Failure);
            return false;
        }
    }
    void login() {
        loginResult.setValue(LoginResult.Waiting);

        Call<RegisterResponse> call = APITools.getInstance().getService().login(email, password, "login");
        call.enqueue(new Callback<RegisterResponse>() {

            @Override
            public void onResponse(Call<RegisterResponse> call, Response<RegisterResponse> response) {
                if (response.isSuccessful()) {
                    RegisterResponse loginResponse = response.body();
                    if (loginResponse.code == 1) {
                        Bundle data = new Bundle();
                        data.putString("userID" , String.valueOf(loginResponse.userID));
                        AccountManager.getInstance().addAccount(email, password, context, data);
                        loginResult.setValue(LoginResult.Success);

                    } else{
                        loginResult.setValue(LoginResult.WrongInfo);
                    }
                } else {
                    loginResult.setValue(LoginResult.Failure);
                }
            }

            @Override
            public void onFailure(Call<RegisterResponse> call, Throwable t) {

            }


        });
    }
    void reset() {

        Call<ResetResponse> call = APITools.getInstance().getService().reset(email, "reset");
        call.enqueue(new Callback<ResetResponse>() {

            @Override
            public void onResponse(Call<ResetResponse> call, Response<ResetResponse> response) {
               
            }

            @Override
            public void onFailure(Call<ResetResponse> call, Throwable t) {

            }


        });
    }

}
