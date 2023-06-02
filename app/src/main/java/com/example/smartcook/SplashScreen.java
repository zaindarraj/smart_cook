package com.example.smartcook;

import android.accounts.Account;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.smartcook.accountmanager.AccountManager;
import com.example.smartcook.classes.UserInfo;

import java.util.Objects;


public class SplashScreen extends Fragment {



    public SplashScreen() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        return inflater.inflate(R.layout.fragment_splash_screen, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Account account = AccountManager.getInstance().getLoggedAccount(getContext());
        if(account != null){
            if(Objects.equals(account.name, "admin@gmail.com")){
                UserInfo.getInstance().setEmail(account.name);
                Navigation.findNavController(getView()).navigate(R.id.action_splashScreen_to_adminScreen);
            }else{
                UserInfo.getInstance().setEmail(account.name);
                UserInfo.getInstance().setUserID(Integer.parseInt(AccountManager.getManager(getContext()).getUserData(account, "userID")));
                Navigation.findNavController(getView()).navigate(R.id.action_splashScreen_to_homeScreen);

            }

        }else{
            Navigation.findNavController(getView()).navigate(R.id.action_splashScreen_to_loginScreen);

        }
    }
}