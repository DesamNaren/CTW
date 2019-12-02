package com.example.twdinspection.viewmodel;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.twdinspection.databinding.ActivityMpinBinding;
import com.example.twdinspection.databinding.ActivityStudentsAttendanceBinding;


public class StudAttndCustomViewModel implements ViewModelProvider.Factory {
    private ActivityStudentsAttendanceBinding binding;
    private Context context;

    public StudAttndCustomViewModel(ActivityStudentsAttendanceBinding binding, Context context) {
        this.binding = binding;
        this.context = context;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new StudentsAttndViewModel(binding, context);
    }
}