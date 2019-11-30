package com.example.twdinspection.viewmodel;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.text.TextUtils;
import android.view.View;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.twdinspection.databinding.ActivityMpinBinding;
import com.example.twdinspection.databinding.ActivityStaffAttBinding;
import com.example.twdinspection.ui.BasicDetailsActivity;
import com.google.android.material.snackbar.Snackbar;

public class StaffViewModel extends ViewModel {

    private ActivityStaffAttBinding binding;
    private Context context;


    StaffViewModel(ActivityStaffAttBinding binding, Context context) {
        this.binding = binding;
        this.context = context;
    }


}
