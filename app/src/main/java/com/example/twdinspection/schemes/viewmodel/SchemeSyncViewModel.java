package com.example.twdinspection.schemes.viewmodel;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.twdinspection.common.network.TWDService;
import com.example.twdinspection.common.utils.AppConstants;
import com.example.twdinspection.schemes.room.repository.SchemeSyncRepository;
import com.example.twdinspection.schemes.source.DMV.SchemeDMVResponse;
import com.example.twdinspection.schemes.source.finyear.FinancialYearResponse;
import com.example.twdinspection.schemes.source.finyear.FinancialYrsEntity;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SchemeSyncViewModel extends AndroidViewModel {
    protected MutableLiveData<SchemeDMVResponse> schemeDMVResponseMutableLiveData;
    protected MutableLiveData<FinancialYearResponse> financialYearResponseMutableLiveData;

    public SchemeSyncViewModel(Application application) {
        super(application);
        schemeDMVResponseMutableLiveData = new MutableLiveData<>();
        financialYearResponseMutableLiveData = new MutableLiveData<>();
    }

    public LiveData<SchemeDMVResponse> getSchemeReposnse() {
        if (schemeDMVResponseMutableLiveData != null) {
            getSchemeReposnseCall();
        }
        return schemeDMVResponseMutableLiveData;
    }

    private void getSchemeReposnseCall() {
        TWDService twdService = TWDService.Factory.create("schemes");
        twdService.getSchemeDMV().enqueue(new Callback<SchemeDMVResponse>() {
            @Override
            public void onResponse(@NotNull Call<SchemeDMVResponse> call, @NotNull Response<SchemeDMVResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    if (response.body().getStatusCode() != null && response.body().getStatusCode().equals(AppConstants.SUCCESS_STRING_CODE)) {
                        schemeDMVResponseMutableLiveData.setValue(response.body());
                    } else if (response.body().getStatusCode() != null && response.body().getStatusCode().equals(AppConstants.FAILURE_STRING_CODE)) {

                    } else {

                    }
                } else {

                }
            }

            @Override
            public void onFailure(@NotNull Call<SchemeDMVResponse> call, @NotNull Throwable t) {
                Log.i("UU", "onFailure: " + t.getMessage());
            }
        });
    }

    public LiveData<FinancialYearResponse> getFinYearResponse() {
        if (financialYearResponseMutableLiveData != null) {
            getFinYearReposnseCall();
        }
        return financialYearResponseMutableLiveData;
    }

    private void getFinYearReposnseCall() {
        TWDService twdService = TWDService.Factory.create("schemes");
        twdService.getFinancialYears().enqueue(new Callback<FinancialYearResponse>() {
            @Override
            public void onResponse(@NotNull Call<FinancialYearResponse> call, @NotNull Response<FinancialYearResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    if (response.body().getStatusCode() != null && response.body().getStatusCode().equals(AppConstants.SUCCESS_STRING_CODE)) {
                        financialYearResponseMutableLiveData.setValue(response.body());
                    } else if (response.body().getStatusCode() != null && response.body().getStatusCode().equals(AppConstants.FAILURE_STRING_CODE)) {

                    } else {

                    }
                } else {

                }
            }

            @Override
            public void onFailure(@NotNull Call<FinancialYearResponse> call, @NotNull Throwable t) {
                Log.i("UU", "onFailure: " + t.getMessage());
            }
        });
    }
}

