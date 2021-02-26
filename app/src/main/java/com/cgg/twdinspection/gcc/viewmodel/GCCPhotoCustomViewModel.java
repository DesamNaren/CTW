package com.cgg.twdinspection.gcc.viewmodel;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

public class GCCPhotoCustomViewModel implements ViewModelProvider.Factory {
    private final Context context;

    public GCCPhotoCustomViewModel(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new GCCPhotoViewModel(context);
    }
}
