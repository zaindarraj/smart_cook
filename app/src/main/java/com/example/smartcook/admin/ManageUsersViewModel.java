package com.example.smartcook.admin;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.smartcook.api.APITools;
import com.example.smartcook.classes.User;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ManageUsersViewModel extends ViewModel {

    enum DeleteUserResult{
        Success,
        Failed
    }

    MutableLiveData<DeleteUserResult> deleteResultMutableLiveData = new MutableLiveData<>();

    public MutableLiveData<DeleteUserResult> getDeleteResultMutableLiveData() {
        return deleteResultMutableLiveData;
    }

    MutableLiveData<ArrayList<User>> usersArray = new MutableLiveData<ArrayList<User>>();

    public MutableLiveData<ArrayList<User>> getUsersArray() {
        return usersArray;
    }

    public void getUsers(){
        Call<UsersResponse> call = APITools.getInstance().getService().getUsers("getUsers");
        call.enqueue(new Callback<UsersResponse>() {
            @Override
            public void onResponse(Call<UsersResponse> call, Response<UsersResponse> response) {
                if(response.isSuccessful()){
                    UsersResponse body = response.body();
                    if(body.getCode() == 1){
                        if(!body.getUsers().isEmpty()){
                            usersArray.setValue(body.getUsers());
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<UsersResponse> call, Throwable t) {

            }
        });
    }


    public void deleteUser(int userID){
        Call<DeleteUserResponse> call = APITools.getInstance().getService().deleteUser(userID,"deleteUser");
        call.enqueue(new Callback<DeleteUserResponse>() {
            @Override
            public void onResponse(Call<DeleteUserResponse> call, Response<DeleteUserResponse> response) {
                if(response.isSuccessful()){
                    DeleteUserResponse body = response.body();
                    if(body.code == 1){
                        deleteResultMutableLiveData.setValue(DeleteUserResult.Success);
                    }else{
                        deleteResultMutableLiveData.setValue(DeleteUserResult.Failed);

                    }
                }else{
                    deleteResultMutableLiveData.setValue(DeleteUserResult.Failed);

                }
            }

            @Override
            public void onFailure(Call<DeleteUserResponse> call, Throwable t) {
                deleteResultMutableLiveData.setValue(DeleteUserResult.Failed);

            }
        });
    }


}
