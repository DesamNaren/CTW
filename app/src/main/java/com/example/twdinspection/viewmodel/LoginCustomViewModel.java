package com.example.twdinspection.viewmodel;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.twdinspection.databinding.ActivityLoginCreBinding;
import com.example.twdinspection.interfaces.AuthListener;
import com.example.twdinspection.source.LoginUser;

public class LoginCustomViewModel implements ViewModelProvider.Factory {
    private ActivityLoginCreBinding binding;
    private Context context;

    public LoginCustomViewModel(ActivityLoginCreBinding binding, Context context) {
        this.binding = binding;
        this.context = context;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new LoginViewModel(binding, context);
    }
}
