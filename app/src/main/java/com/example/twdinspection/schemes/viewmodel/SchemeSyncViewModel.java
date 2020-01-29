package com.example.twdinspection.schemes.viewmodel;

import android.app.Application;
import android.content.Context;
import android.util.Log;
import android.view.View;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.twdinspection.R;
import com.example.twdinspection.common.network.TWDService;
import com.example.twdinspection.common.utils.AppConstants;
import com.example.twdinspection.common.utils.Utils;
import com.example.twdinspection.databinding.ActivitySchemeSyncBinding;
import com.example.twdinspection.schemes.interfaces.ErrorHandlerInterface;
import com.example.twdinspection.schemes.source.DMV.SchemeDMVResponse;
import com.example.twdinspection.schemes.source.finyear.FinancialYearResponse;
import com.example.twdinspection.schemes.source.remarks.InspectionRemarkResponse;
import com.example.twdinspection.schemes.source.schemes.SchemeResponse;

import org.jetbrains.annotations.NotNull;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SchemeSyncViewModel extends AndroidViewModel {
    private MutableLiveData<SchemeDMVResponse> schemeDMVResponseMutableLiveData;
    private MutableLiveData<FinancialYearResponse> financialYearResponseMutableLiveData;
    private MutableLiveData<InspectionRemarkResponse> inspectionRemarkResponseMutableLiveData;
    private MutableLiveData<SchemeResponse> schemeResponseMutableLiveData;
    Context context;
    private ErrorHandlerInterface errorHandlerInterface;
    ActivitySchemeSyncBinding binding;

    public SchemeSyncViewModel(Context context, Application application, ActivitySchemeSyncBinding binding) {
        super(application);
        this.context=context;
        this.binding=binding;
        schemeDMVResponseMutableLiveData = new MutableLiveData<>();
        financialYearResponseMutableLiveData = new MutableLiveData<>();
        inspectionRemarkResponseMutableLiveData = new MutableLiveData<>();
        schemeResponseMutableLiveData = new MutableLiveData<>();
        errorHandlerInterface = (ErrorHandlerInterface) context;

    }

    public LiveData<SchemeDMVResponse> getSchemeDMVReposnse() {
        if (schemeDMVResponseMutableLiveData != null) {

                getSchemeDMVReposnseCall();

        }
        return schemeDMVResponseMutableLiveData;
    }

    private void getSchemeDMVReposnseCall() {
        TWDService twdService = TWDService.Factory.create("schemes");
        twdService.getSchemeDMV().enqueue(new Callback<SchemeDMVResponse>() {
            @Override
            public void onResponse(@NotNull Call<SchemeDMVResponse> call, @NotNull Response<SchemeDMVResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    schemeDMVResponseMutableLiveData.setValue(response.body());
                }
            }

            @Override
            public void onFailure(@NotNull Call<SchemeDMVResponse> call, @NotNull Throwable t) {
                errorHandlerInterface.handleError(t, context);
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
                        financialYearResponseMutableLiveData.setValue(response.body());
                }
            }
            @Override
            public void onFailure(@NotNull Call<FinancialYearResponse> call, @NotNull Throwable t) {
                errorHandlerInterface.handleError(t, context);
            }
        });
    }

    public LiveData<InspectionRemarkResponse> getInspectionRemarks() {
        if (inspectionRemarkResponseMutableLiveData != null) {
                getInspectionRemarksCall();

        }
        return inspectionRemarkResponseMutableLiveData;
    }

    private void getInspectionRemarksCall() {
        TWDService twdService = TWDService.Factory.create("schemes");
        twdService.getInspectionRemarks().enqueue(new Callback<InspectionRemarkResponse>() {
            @Override
            public void onResponse(@NotNull Call<InspectionRemarkResponse> call, @NotNull Response<InspectionRemarkResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                        inspectionRemarkResponseMutableLiveData.setValue(response.body());
                }
            }

            @Override
            public void onFailure(@NotNull Call<InspectionRemarkResponse> call, @NotNull Throwable t) {
                errorHandlerInterface.handleError(t, context);
            }
        });
    }

    public LiveData<SchemeResponse> getSchemeResponse() {
        if (schemeResponseMutableLiveData != null) {
                getSchemeResponseCall();
        }
        return schemeResponseMutableLiveData;
    }

    private void getSchemeResponseCall() {
        TWDService twdService = TWDService.Factory.create("schemes");
        twdService.getSchemeResponse().enqueue(new Callback<SchemeResponse>() {
            @Override
            public void onResponse(@NotNull Call<SchemeResponse> call, @NotNull Response<SchemeResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                        schemeResponseMutableLiveData.setValue(response.body());
                }
            }

            @Override
            public void onFailure(@NotNull Call<SchemeResponse> call, @NotNull Throwable t) {
                errorHandlerInterface.handleError(t, context);
            }
        });
    }
}

