package com.example.twdinspection.engineering_works.viewmodels;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

public class UploadEngPhotoCustomViewModel implements ViewModelProvider.Factory {
    private Context context;

    public UploadEngPhotoCustomViewModel(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new UploadEngPhotoViewModel(context);
    }
}
