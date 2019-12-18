package com.example.twdinspection.viewmodel;

import android.app.Application;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.twdinspection.application.TWDApplication;
import com.example.twdinspection.databinding.ActivityMpinBinding;
import com.example.twdinspection.databinding.ActivityStaffAttBinding;


public class StaffAttendCustomViewModel implements ViewModelProvider.Factory {
    private ActivityStaffAttBinding binding;
    private Context context;

    public StaffAttendCustomViewModel(ActivityStaffAttBinding binding, Context context) {
        this.binding = binding;
        this.context = context;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new StaffViewModel(binding, context, TWDApplication.get(context));
    }
}
