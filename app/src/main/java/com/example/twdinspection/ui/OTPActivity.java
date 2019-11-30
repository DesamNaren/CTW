package com.example.twdinspection.ui;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;


import com.example.twdinspection.R;
import com.example.twdinspection.databinding.ActivityOtpBinding;
import com.example.twdinspection.interfaces.AuthListener;
import com.example.twdinspection.viewmodel.MPINCustomViewModel;
import com.example.twdinspection.viewmodel.MPINViewModel;

public class OTPActivity extends AppCompatActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityOtpBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_otp);
        MPINViewModel mpinViewModel = ViewModelProviders.of(this, new MPINCustomViewModel(binding)).get(MPINViewModel.class);
        binding.setViewmodel(mpinViewModel);
    }

}
