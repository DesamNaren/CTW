package com.example.twdinspection.inspection.viewmodel;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.twdinspection.databinding.ActivityGenerateMpinBinding;

public class GenerateMPINCustomViewModel implements ViewModelProvider.Factory {
    private ActivityGenerateMpinBinding binding;
    private Context context;

    public GenerateMPINCustomViewModel(ActivityGenerateMpinBinding binding, Context context) {
        this.binding = binding;
        this.context = context;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new GenerateMPINViewModel(binding,context);
    }
}
