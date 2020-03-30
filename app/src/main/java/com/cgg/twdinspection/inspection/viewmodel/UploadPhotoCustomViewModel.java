package com.cgg.twdinspection.inspection.viewmodel;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.cgg.twdinspection.databinding.ActivityBenDetailsActivtyBinding;
import com.cgg.twdinspection.databinding.ActivityUploadedPhotoBinding;

public class UploadPhotoCustomViewModel implements ViewModelProvider.Factory {
    private Context context;

    public UploadPhotoCustomViewModel(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new UploadPhotoViewModel(context);
    }
}
