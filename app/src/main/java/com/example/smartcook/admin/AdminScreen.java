package com.example.smartcook.admin;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.smartcook.MainActivity;
import com.example.smartcook.R;
import com.example.smartcook.registeration.login.LoginViewModel;
import com.google.android.material.button.MaterialButton;

public class AdminScreen extends Fragment {


    MaterialButton manageUsers;

    MaterialButton logout;
    MaterialButton managePosts;

    public AdminScreen() {
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
        return inflater.inflate(R.layout.fragment_admin_screen, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        managePosts = view.findViewById(R.id.managePosts);
        manageUsers = view.findViewById(R.id.manageUsers);
        LoginViewModel loginViewModel = new ViewModelProvider(getActivity()).get(LoginViewModel.class);
        logout = view.findViewById(R.id.logout);

        logout.setOnClickListener(view1 -> {
            loginViewModel.context = getContext();
            if(loginViewModel.logOut()){
                Intent intent = new Intent(getContext(), MainActivity.class);
                getActivity().finish();

                getActivity().startActivity(intent);
            }
        });

        manageUsers.setOnClickListener(view12 -> Navigation.findNavController(view12).navigate(R.id.action_adminScreen_to_manageUsersScreen));

        managePosts.setOnClickListener(view13 -> Navigation.findNavController(view13).navigate(R.id.action_adminScreen_to_managePostsScreen));
    }
}