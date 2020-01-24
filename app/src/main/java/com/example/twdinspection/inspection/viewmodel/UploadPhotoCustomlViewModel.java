package com.example.twdinspection.inspection.viewmodel;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.twdinspection.databinding.ActivityBenDetailsActivtyBinding;
import com.example.twdinspection.databinding.ActivityUploadedPhotoBinding;
import com.example.twdinspection.schemes.source.bendetails.BeneficiaryDetail;
import com.example.twdinspection.schemes.viewmodel.BenDetailsViewModel;

public class UploadPhotoCustomlViewModel implements ViewModelProvider.Factory {
    private ActivityUploadedPhotoBinding benDetailsActivtyBinding;
    private Context context;
    private BeneficiaryDetail beneficiaryDetail;

    public UploadPhotoCustomlViewModel(ActivityUploadedPhotoBinding benDetailsActivtyBinding,
                                       Context context) {
        this.benDetailsActivtyBinding = benDetailsActivtyBinding;
        this.beneficiaryDetail = beneficiaryDetail;
        this.context = context;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new UploadPhotoViewModel(benDetailsActivtyBinding, context);
    }
}
