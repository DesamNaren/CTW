package com.cgg.twdinspection.schemes.viewmodel;

import android.app.Activity;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.cgg.twdinspection.databinding.ActivityBeneficiaryReportBinding;
import com.example.twdinspection.databinding.ActivitySchemeReportBinding;

public class BenCustomReportViewModel implements ViewModelProvider.Factory {
    private ActivityBeneficiaryReportBinding binding;
    private ActivitySchemeReportBinding schemeReportBinding;
    private Activity context;

    public BenCustomReportViewModel(ActivityBeneficiaryReportBinding binding, Activity context) {
        this.binding = binding;
        this.context = context;
    }

    public BenCustomReportViewModel(ActivitySchemeReportBinding schemeReportBinding, Activity context) {
        this.schemeReportBinding = schemeReportBinding;
        this.context = context;
    }


    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new BenReportViewModel(binding, context);
    }
}
