package com.cgg.twdinspection.inspection.viewmodel;

import android.app.Application;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.cgg.twdinspection.databinding.ActivityAcademicBinding;
import com.cgg.twdinspection.databinding.ActivityMedicalBinding;


public class AcademicCustomViewModel implements ViewModelProvider.Factory {
    private ActivityAcademicBinding binding;
    private Context context;
    Application application;

    public AcademicCustomViewModel(ActivityAcademicBinding binding, Context context, Application application) {
        this.binding = binding;
        this.context = context;
        this.application = application;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new AcademicViewModel(binding, application);
    }
}
