package com.example.twdinspection.viewmodel;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.twdinspection.databinding.ActivityMpinBinding;


public class MPINCustomViewModel implements ViewModelProvider.Factory {
    private ActivityMpinBinding binding;

    public MPINCustomViewModel(ActivityMpinBinding binding) {
        this.binding = binding;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new MPINViewModel(binding);
    }
}
