package com.example.smartcook.home.ml;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.smartcook.R;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


public class MLScreen extends Fragment {

    TextInputEditText ing;

    MLViewModel mlViewModel;

    Toast toast;

    Observer<MLViewModel.Result> recipeIDResult;

    Observer<MLViewModel.Result> getRecipeResult;


   MaterialButton play;

    public MLScreen() {
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
        return inflater.inflate(R.layout.fragment_ml_screen, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        toast = new Toast(getContext());
        mlViewModel = new ViewModelProvider(getActivity()).get(MLViewModel.class);

        getRecipeResult = (Observer<MLViewModel.Result>) result -> {
            switch (result){
                case Failure:
                    toast.setText("Recipe was not found :(");
                    toast.show();
                    break;
                case Success:
                    Navigation.findNavController(view).navigate(R.id.action_homeFragment_to_recipeScreen);
                    break;
            }
        };
        recipeIDResult = result -> {
                switch (result){
                    case Failure:
                        toast.setText("Recipe was not found :(");
                        toast.show();
                        break;
                    case Success:
                        mlViewModel.getRecipe();
                }
        };
        ing = view.findViewById(R.id.ing);
        play = view.findViewById(R.id.play);
        mlViewModel.getRecipeResult.observe(getActivity(),getRecipeResult);
        mlViewModel.predictionResult.observe(getActivity(), recipeIDResult);

        play.setOnClickListener(view1 -> {
            mlViewModel.ings = ing.getText().toString();
            mlViewModel.predict();
        });

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mlViewModel.getRecipeResult.removeObserver(getRecipeResult);
        mlViewModel.predictionResult.removeObserver(recipeIDResult);
    }
}
