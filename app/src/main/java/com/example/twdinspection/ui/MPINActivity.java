package com.example.twdinspection.ui;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;


import com.example.twdinspection.R;
import com.example.twdinspection.databinding.ActivityMpinBinding;
import com.example.twdinspection.viewmodel.MPINCustomViewModel;
import com.example.twdinspection.viewmodel.MPINViewModel;

public class MPINActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityMpinBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_mpin);
        MPINViewModel mpinViewModel = ViewModelProviders.of(this, new MPINCustomViewModel(binding, this)).get(MPINViewModel.class);
        binding.setViewmodel(mpinViewModel);
    }

}
