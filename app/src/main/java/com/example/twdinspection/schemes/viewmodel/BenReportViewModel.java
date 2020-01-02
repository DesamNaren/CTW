package com.example.twdinspection.schemes.viewmodel;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.twdinspection.common.network.TWDService;
import com.example.twdinspection.common.utils.AppConstants;
import com.example.twdinspection.schemes.source.bendetails.BeneficiaryDetail;
import com.example.twdinspection.schemes.source.bendetails.BeneficiaryReport;
import com.example.twdinspection.schemes.source.bendetails.BeneficiaryRequest;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BenReportViewModel extends AndroidViewModel {
    private MutableLiveData<List<BeneficiaryDetail>> beneficiaryLiveData;

    public BenReportViewModel(Application application) {
        super(application);
        beneficiaryLiveData = new MutableLiveData<>();
    }

    public LiveData<List<BeneficiaryDetail>> getBeneficiaryInfo(BeneficiaryRequest beneficiaryRequest) {
        if (beneficiaryLiveData != null) {
            getBeneficiaryDetails(beneficiaryRequest);
        }
        return beneficiaryLiveData;
    }

    private void getBeneficiaryDetails(BeneficiaryRequest beneficiaryRequest) {
        TWDService twdService = TWDService.Factory.create("schemes");
        twdService.getBeneficiaryDetails(beneficiaryRequest.getDistId(), beneficiaryRequest.getMandalId(), beneficiaryRequest.getVillageId(), beneficiaryRequest.getFinYearId()).enqueue(new Callback<BeneficiaryReport>() {
            @Override
            public void onResponse(@NotNull Call<BeneficiaryReport> call, @NotNull Response<BeneficiaryReport> response) {
                if (response.isSuccessful() && response.body() != null) {
                    if (response.body().getStatusCode() != null && response.body().getStatusCode().equals(AppConstants.SUCCESS_STRING_CODE)) {
                        if (response.body().getBeneficiaryDetails() != null && response.body().getBeneficiaryDetails().size() > 0) {
                            List<BeneficiaryDetail> beneficiaryDetails = response.body().getBeneficiaryDetails();
                            beneficiaryLiveData.setValue(beneficiaryDetails);
                        }
                    } else if (response.body().getStatusCode() != null && response.body().getStatusCode().equals(AppConstants.FAILURE_STRING_CODE)) {

                    } else {

                    }
                } else {

                }
            }

            @Override
            public void onFailure(@NotNull Call<BeneficiaryReport> call, @NotNull Throwable t) {
                Log.i("UU", "onFailure: " + t.getMessage());
            }
        });
    }
}
