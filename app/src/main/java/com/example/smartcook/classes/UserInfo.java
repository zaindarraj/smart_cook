package com.example.smartcook.classes;

import androidx.lifecycle.MutableLiveData;

public class UserInfo {
    private MutableLiveData<String> email;
    private MutableLiveData<String> password;

    private  int userID;
    UserInfo(){
        this.email = new MutableLiveData<String>();
        this.password =new MutableLiveData<String>();
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public int getUserID() {
        return userID;
    }

    private static UserInfo userInfo = new UserInfo();
    public  static UserInfo getInstance(){
        return userInfo;
    }

    public void setEmail(String email) {
        this.email.setValue(email);
    }

    public void setPassword(String password) {
        this.password.setValue(password);
    }

    public MutableLiveData<String> getEmail() {
        return email;
    }

    public MutableLiveData<String> getPassword() {
        return password;
    }
}
