package com.example.twdinspection.viewmodel;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.twdinspection.databinding.ActivityMpinBinding;


public class MPINCustomViewModel implements ViewModelProvider.Factory {
    private ActivityMpinBinding binding;
    private Context context;

    public MPINCustomViewModel(ActivityMpinBinding binding, Context context) {
        this.binding = binding;
        this.context = context;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new MPINViewModel(binding, context);
    }
}
