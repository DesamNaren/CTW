package com.example.twdinspection.schemes.viewmodel;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.twdinspection.common.network.TWDService;
import com.example.twdinspection.databinding.ActivityBeneficiaryReportBinding;
import com.example.twdinspection.databinding.ActivitySchemeReportBinding;
import com.example.twdinspection.schemes.interfaces.ErrorHandlerInterface;
import com.example.twdinspection.schemes.room.repository.SchemesInfoRepository;
import com.example.twdinspection.schemes.source.bendetails.BeneficiaryReport;
import com.example.twdinspection.schemes.source.bendetails.BeneficiaryRequest;
import com.example.twdinspection.schemes.source.schemes.SchemeEntity;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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
