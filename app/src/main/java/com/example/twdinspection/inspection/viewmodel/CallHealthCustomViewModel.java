package com.example.twdinspection.inspection.viewmodel;

import android.app.Application;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.twdinspection.databinding.ActivityAcademicBinding;
import com.example.twdinspection.databinding.ActivityCallHealthBinding;


public class CallHealthCustomViewModel implements ViewModelProvider.Factory {
    private Context context;

    public CallHealthCustomViewModel(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new CallHealthViewModel(context);
    }
}
