package com.example.twdinspection.inspection.viewmodel;

import android.app.Application;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.twdinspection.databinding.ActivityGeneralCommentsBinding;
import com.example.twdinspection.databinding.ActivityRegistersBinding;


public class GenCommentsCustomViewModel implements ViewModelProvider.Factory {

    private ActivityGeneralCommentsBinding binding;
    private Context context;
    Application application;

    public GenCommentsCustomViewModel(ActivityGeneralCommentsBinding binding, Context context, Application application) {
        this.binding = binding;
        this.context = context;
        this.application = application;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new GenCommentsViewModel(binding, application);
    }
}
