package com.example.twdinspection.inspection.viewmodel;

import android.app.Application;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.twdinspection.databinding.ActivityDietIssuesBinding;
import com.example.twdinspection.databinding.ActivityMedicalBinding;


public class DietIssuesCustomViewModel implements ViewModelProvider.Factory {
    private ActivityDietIssuesBinding binding;
    private Context context;
    Application application;

    public DietIssuesCustomViewModel(ActivityDietIssuesBinding binding, Context context, Application application) {
        this.binding = binding;
        this.context = context;
        this.application = application;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new DietIsuuesViewModel(binding, application);
    }
}
