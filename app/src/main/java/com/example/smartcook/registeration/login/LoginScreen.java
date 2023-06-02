package com.example.smartcook.registeration.login;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.smartcook.R;
import com.example.smartcook.accountmanager.AccountManager;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;


public class LoginScreen extends Fragment {

    MaterialButton logup;
    MaterialButton reset;
    Toast toast;
    MaterialButton login;
    LoginViewModel loginViewModel;

    Observer<LoginViewModel.LoginResult> observer;

    TextInputEditText email;
    TextInputEditText password;
    public LoginScreen() {
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
        return inflater.inflate(R.layout.fragment_login_screen, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        observer = loginResult -> {
            switch (loginResult){
                case Waiting:
                    login.setEnabled(false);
                    break;
                case Failure:
                    toast.setText("Connection Problem");
                    toast.show();
                    login.setEnabled(true);
                    break;
                case Success:
                    Navigation.findNavController(getView()).navigate(R.id.action_loginScreen_to_homeScreen);
                    break;
                case WrongInfo:
                    login.setEnabled(true);
                    toast.setText("Wrong email or password");
                    toast.show();
                    break;



            }
        };
        toast = new Toast(getContext());
        email = view.findViewById(R.id.email);
        password = view.findViewById(R.id.password);
        loginViewModel = new ViewModelProvider(getActivity()).get(LoginViewModel.class);
        logup = getView().findViewById(R.id.logup);
        login = view.findViewById(R.id.login);
        reset = getView().findViewById(R.id.reset);
        logup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(getView()).navigate(R.id.action_loginScreen_to_logupScreen);
            }
        });
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(email.getText().toString().isEmpty()||password.getText().toString().isEmpty()){
                    toast.setText("Please fill all the fields");
                    toast.show();
                }else{
                    if(email.getText().toString().equals("admin@gmail.com") &&
                            password.getText().toString().equals("123456789")){
                        Bundle data = new Bundle();
                        AccountManager.getInstance().addAccount(email.getText().toString(),
                                password.getText().toString(), getContext(), data);
                        Navigation.findNavController(getView()).navigate(R.id.action_loginScreen_to_adminScreen);

                    }else{
                        loginViewModel.context  = getContext();
                        loginViewModel.email = email.getText().toString();
                        loginViewModel.password = password.getText().toString();
                        loginViewModel.login();
                        loginViewModel.loginResult.observe(getActivity(), observer);
                    }

                }

            }
        });
        reset.setOnClickListener(view1 -> Navigation.findNavController(getView()).navigate(R.id.action_loginScreen_to_resetPasswordScreen));
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        loginViewModel.loginResult.removeObserver(observer);
    }
}