package com.cgg.twdinspection.schemes.viewmodel;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.cgg.twdinspection.databinding.ActivityBenDetailsActivtyBinding;
import com.cgg.twdinspection.databinding.ActivityBeneficiaryReportBinding;
import com.cgg.twdinspection.schemes.source.bendetails.BeneficiaryDetail;

public class BenCustomDetailViewModel implements ViewModelProvider.Factory {
    private ActivityBenDetailsActivtyBinding benDetailsActivtyBinding;
    private Context context;
    private BeneficiaryDetail beneficiaryDetail;

    public BenCustomDetailViewModel(BeneficiaryDetail beneficiaryDetail,
                                    ActivityBenDetailsActivtyBinding benDetailsActivtyBinding,
                                    Context context) {
        this.benDetailsActivtyBinding = benDetailsActivtyBinding;
        this.beneficiaryDetail = beneficiaryDetail;
        this.context = context;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new BenDetailsViewModel(beneficiaryDetail, benDetailsActivtyBinding, context);
    }
}
