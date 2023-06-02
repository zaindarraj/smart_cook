package com.example.smartcook.registeration.logup;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.example.smartcook.R;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;


public class LogupScreen extends Fragment {
    Toast toast;
    LogupViewModel logupViewModel;

    MaterialButton login;
    TextInputEditText name;
    MaterialButton logup;
    TextInputLayout emailLayout;
    TextInputLayout passwordLayout;
    TextInputLayout confirmPasswordLayout;
    TextInputEditText email;
    TextInputEditText password;
    TextInputEditText confirmPassword;
    TextInputLayout phoneLayout;
    TextInputEditText phone;
    public LogupScreen() {
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
        return inflater.inflate(R.layout.fragment_logup_screen, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        toast = new Toast(getContext());
        logupViewModel = new ViewModelProvider(getActivity()).get(LogupViewModel.class);
        login = getView().findViewById(R.id.login);
        logup = view.findViewById(R.id.logup);
        phone = view.findViewById(R.id.phone);
        name= view.findViewById(R.id.name);
        phoneLayout = view.findViewById(R.id.phoneLayout);
        emailLayout = view.findViewById(R.id.emailLayout);
        passwordLayout = view.findViewById(R.id.passwordLayout);
        confirmPasswordLayout = view.findViewById(R.id.confirmPasswordLayout);
        emailLayout = view.findViewById(R.id.emailLayout);
        email = view.findViewById(R.id.email);
        password = view.findViewById(R.id.password);
        confirmPassword = view.findViewById(R.id.confirmPassword);
        name.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if(name.getText().toString().isEmpty()){
                    logup.setEnabled(false);
                }else{
                    logup.setEnabled(true);
                }
            }
        });
        phone.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if(!Patterns.PHONE.matcher(phone.getText().toString()).matches()){
                    phoneLayout.setErrorEnabled(true);
                    phoneLayout.setError("Not Valid");
                    logup.setEnabled(false);

                }else{
                    logup.setEnabled(true);

                    phoneLayout.setErrorEnabled(false);
                }
            }
        });



        confirmPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if(!confirmPassword.getText().toString().equals(password.getText().toString())){
                    confirmPasswordLayout.setErrorEnabled(true);
                    logup.setEnabled(false);

                    confirmPasswordLayout.setError("Password not matching");
                }else{
                    logup.setEnabled(true);

                    confirmPasswordLayout.setErrorEnabled(false);

                }
            }
        });


        password.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                    if(!confirmPassword.getText().toString().equals(password.getText().toString())){
                        confirmPasswordLayout.setErrorEnabled(true);
                        logup.setEnabled(false);

                        confirmPasswordLayout.setError("Password not matching");
                    }else{
                        logup.setEnabled(true);

                        confirmPasswordLayout.setErrorEnabled(false);

                    }
            }
        });



        email.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {

                    if(!Patterns.EMAIL_ADDRESS.matcher(email.getText().toString()).matches()){
                        emailLayout.setErrorEnabled(true);
                        emailLayout.setError("Email not valid");
                        logup.setEnabled(false);
                    }else{
                        logup.setEnabled(true);

                        emailLayout.setErrorEnabled(false);
                    }
            }
        });


        logup.setOnClickListener(view1 -> {
            if(email.getText().toString().isEmpty() || password.getText().toString().isEmpty()
            || confirmPassword.getText().toString().isEmpty()||name.getText().toString().isEmpty()||
            phone.getText().toString().isEmpty()){
                toast.setText("Please fill out all fields");
                toast.show();
            }else{
                logupViewModel.email =email.getText().toString();
                logupViewModel.password = password.getText().toString();
                logupViewModel.name  = name.getText().toString();
                logupViewModel.pn = phone.getText().toString();
                logupViewModel.logup(getContext());
                logupViewModel.logupResult.observe(getActivity(), logupResult -> {
                    switch (logupResult){
                        case Success:
                            //go to home scree

                            logup.setEnabled(true);
                            toast.setText("Create your account :)");
                            toast.show();
                            Navigation.findNavController(getView()).navigate(R.id.action_logupScreen_to_homeScreen);

                            break;
                        case Wait:
                            logup.setEnabled(false);
                            break;
                        case Failure:
                            toast.setText("Connection Problem");
                            toast.show();
                            logup.setEnabled(true);
                            break;
                        case EmailExist:
                            logup.setEnabled(true);

                            toast.setText("Email already exists");
                            toast.show();
                            break;
                    }
                });
            }

        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(getView()).navigate(R.id.action_logupScreen_to_loginScreen);
            }
        });
    }
}