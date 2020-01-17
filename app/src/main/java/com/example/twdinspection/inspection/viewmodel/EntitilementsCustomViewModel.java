package com.example.twdinspection.inspection.viewmodel;

import android.app.Application;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.twdinspection.databinding.ActivityAcademicBinding;
import com.example.twdinspection.databinding.ActivityEntitlementsBinding;


public class EntitilementsCustomViewModel implements ViewModelProvider.Factory {
    private ActivityEntitlementsBinding binding;
    private Context context;
    Application application;

    public EntitilementsCustomViewModel(ActivityEntitlementsBinding binding, Context context, Application application) {
        this.binding = binding;
        this.context = context;
        this.application = application;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new EntitlementsViewModel(binding, application);
    }
}