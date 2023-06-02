package com.example.smartcook.home.ml;

import static android.content.ContentValues.TAG;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.smartcook.R;
import com.example.smartcook.api.APITools;
import com.google.android.material.button.MaterialButton;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;


public class RecipeScreen extends Fragment {

    MLViewModel mlViewModel;

    ImageView imageView;

    TextView recipeName;

    MaterialButton healthInfo;

    MaterialButton method;

    public RecipeScreen() {
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
        return inflater.inflate(R.layout.fragment_recipe_screen, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        method = view.findViewById(R.id.prepMethod);
        healthInfo = view.findViewById(R.id.healthInfo);
        healthInfo.setOnClickListener(view1 -> {
            Navigation.findNavController(view).navigate(R.id.action_recipeScreen_to_healthInformationScreen);
        });
        mlViewModel = new ViewModelProvider(getActivity()).get(MLViewModel.class);
        imageView = view.findViewById(R.id.recipeImage);
        Glide.with(getContext())
                .load(APITools.url + mlViewModel.reciepe.getImage())
                .into(imageView);
        recipeName =  view.findViewById(R.id.recipeName);
        recipeName.setText(mlViewModel.reciepe.getName());
        method.setOnClickListener(view12 -> {
            Navigation.findNavController(view).navigate(R.id.action_recipeScreen_to_preparationMethodScreen);
        });

    }
}