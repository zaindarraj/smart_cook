package com.example.smartcook.accountmanager;

import android.accounts.Account;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.Nullable;

import com.example.smartcook.classes.UserInfo;

public class AccountManager {
    private  static AccountManager accountManager = new AccountManager();
    public static AccountManager getInstance(){
        return accountManager;
    }

    public  static  android.accounts.AccountManager getManager(Context context){
        return  android.accounts.AccountManager.get(context);
    }
    public void addAccount(String email, String password, Context context , Bundle data){
        UserInfo.getInstance().setEmail(email);
        UserInfo.getInstance().setPassword(password);
        Account account = new Account(email , "cook");
        android.accounts.AccountManager.get(context).addAccountExplicitly(
                account,password,data
        );
    }
    @Nullable
    public Account getLoggedAccount(Context context){
        Account[] accounts = android.accounts.AccountManager.get(context).getAccountsByType("cook");
        for (Account account: accounts
        ) {
            if(!android.accounts.AccountManager.get(context).getPassword(account).isEmpty()){
                return  account;
            }
        }


        return null;
    }

}
