package com.example.smartcook.home;

import static com.example.smartcook.registeration.login.LoginViewModel.LogOutResult.Success;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.os.Bundle;

import com.example.smartcook.MainActivity;
import com.example.smartcook.R;
import com.example.smartcook.home.post.PostsScreen;
import com.example.smartcook.registeration.login.LoginViewModel;
import com.google.android.material.tabs.TabLayout;

public class HomeScreen extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = new Intent(this, MainActivity.class);

        LoginViewModel loginViewModel = new ViewModelProvider(this).get(LoginViewModel.class);
        loginViewModel.logOutResultMutableLiveData.observe(this, new Observer<LoginViewModel.LogOutResult>() {
            @Override
            public void onChanged(LoginViewModel.LogOutResult logOutResult) {
                switch (logOutResult){
                    case Success:
                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);

                        finish();

                    startActivity(intent);
                        break;
                }

            }
        });
        setContentView(R.layout.activity_home_screen);
        getSupportActionBar().hide();

    }



}