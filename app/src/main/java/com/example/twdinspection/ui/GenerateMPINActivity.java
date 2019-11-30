package com.example.twdinspection.ui;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;

import com.example.twdinspection.R;
import com.example.twdinspection.databinding.ActivityGenerateMpinBinding;
import com.example.twdinspection.viewmodel.GenerateMPINCustomViewModel;
import com.example.twdinspection.viewmodel.GenerateMPINViewModel;

public class GenerateMPINActivity extends AppCompatActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityGenerateMpinBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_generate_mpin);
        GenerateMPINViewModel mpinViewModel = ViewModelProviders.of(this,
                new GenerateMPINCustomViewModel(binding, this)).get(GenerateMPINViewModel.class);
        binding.setViewmodel(mpinViewModel);
    }

}
