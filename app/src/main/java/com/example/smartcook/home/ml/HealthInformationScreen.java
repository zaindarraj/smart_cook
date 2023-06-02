package com.example.smartcook.home.ml;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.smartcook.R;
import com.example.smartcook.api.APITools;


public class HealthInformationScreen extends Fragment {

    MLViewModel mlViewModel;

    ImageView imageView;

    TextView recipeName;
    TextView healthInfo;

    public HealthInformationScreen() {
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
        return inflater.inflate(R.layout.fragment_health_information_screen, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recipeName = view.findViewById(R.id.recipeName);
        healthInfo = view.findViewById(R.id.healthInfoText);
        mlViewModel = new ViewModelProvider(getActivity()).get(MLViewModel.class);
        imageView = view.findViewById(R.id.recipeImage);
        Glide.with(getContext())
                .load(APITools.url + mlViewModel.reciepe.getImage())
                .into(imageView);
        recipeName.setText(mlViewModel.reciepe.getName());
        healthInfo.setText(mlViewModel.reciepe.getBenf());
    }


}