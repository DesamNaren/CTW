package com.cgg.twdinspection.engineering_works.viewmodels;

import android.app.Application;
import android.content.Context;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.cgg.twdinspection.common.network.TWDService;
import com.cgg.twdinspection.common.utils.AppConstants;
import com.cgg.twdinspection.databinding.ActivityEngSyncBinding;
import com.cgg.twdinspection.databinding.ActivitySchemeSyncBinding;
import com.cgg.twdinspection.engineering_works.source.GrantSchemesResponse;
import com.cgg.twdinspection.engineering_works.source.SectorsResponse;
import com.cgg.twdinspection.engineering_works.source.WorksMasterResponse;
import com.cgg.twdinspection.schemes.interfaces.ErrorHandlerInterface;
import com.cgg.twdinspection.schemes.source.dmv.SchemeDMVResponse;
import com.cgg.twdinspection.schemes.source.finyear.FinancialYearResponse;
import com.cgg.twdinspection.schemes.source.remarks.InspectionRemarkResponse;
import com.cgg.twdinspection.schemes.source.schemes.SchemeResponse;

import org.jetbrains.annotations.NotNull;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EngSyncViewModel extends AndroidViewModel {
    private MutableLiveData<WorksMasterResponse> worksMasterLiveData;
    private MutableLiveData<SectorsResponse> sectorsResponseLiveData;
    private MutableLiveData<GrantSchemesResponse> schemesResponseMutableLiveData;
    Context context;
    private ErrorHandlerInterface errorHandlerInterface;
    ActivityEngSyncBinding binding;

    public EngSyncViewModel(Context context, Application application, ActivityEngSyncBinding binding) {
        super(application);
        this.context=context;
        this.binding=binding;
        worksMasterLiveData = new MutableLiveData<>();
        sectorsResponseLiveData = new MutableLiveData<>();
        schemesResponseMutableLiveData = new MutableLiveData<>();
        errorHandlerInterface = (ErrorHandlerInterface) context;

    }

    public LiveData<WorksMasterResponse> getWorksMasterResponse() {
        if (worksMasterLiveData != null) {
            callWorksMasterResponse();
        }
        return worksMasterLiveData;
    }

    private void callWorksMasterResponse() {
        TWDService twdService = TWDService.Factory.create("school");
        twdService.getWorksMaster().enqueue(new Callback<WorksMasterResponse>() {
            @Override
            public void onResponse(@NotNull Call<WorksMasterResponse> call, @NotNull Response<WorksMasterResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    worksMasterLiveData.setValue(response.body());
                }
            }

            @Override
            public void onFailure(@NotNull Call<WorksMasterResponse> call, @NotNull Throwable t) {
                errorHandlerInterface.handleError(t, context);
            }
        });
    }

    public LiveData<SectorsResponse> getSectorResponse() {
        if (sectorsResponseLiveData != null) {
            getSectorsResponseCall();
        }
        return sectorsResponseLiveData;
    }

    private void getSectorsResponseCall() {
        TWDService twdService = TWDService.Factory.create("school");
        twdService.getSectorsMaster().enqueue(new Callback<SectorsResponse>() {
            @Override
            public void onResponse(@NotNull Call<SectorsResponse> call, @NotNull Response<SectorsResponse> response) {
                if (response.isSuccessful() && response.body() != null && response.body().getStatusCode().equalsIgnoreCase(AppConstants.SUCCESS_STRING_CODE)) {
                    sectorsResponseLiveData.setValue(response.body());
                }
            }

            @Override
            public void onFailure(@NotNull Call<SectorsResponse> call, @NotNull Throwable t) {
                errorHandlerInterface.handleError(t, context);
            }
        });
    }

    public LiveData<GrantSchemesResponse> getSchemesResponse() {
        if (schemesResponseMutableLiveData != null) {
            getGrantSchemesResponseCall();
        }
        return schemesResponseMutableLiveData;
    }

    private void getGrantSchemesResponseCall() {
        TWDService twdService = TWDService.Factory.create("school");
        twdService.getGrantSandSchemes().enqueue(new Callback<GrantSchemesResponse>() {
            @Override
            public void onResponse(@NotNull Call<GrantSchemesResponse> call, @NotNull Response<GrantSchemesResponse> response) {
                if (response.isSuccessful() && response.body() != null && response.body().getStatusCode().equalsIgnoreCase(AppConstants.SUCCESS_STRING_CODE)) {
                    schemesResponseMutableLiveData.setValue(response.body());
                }
            }

            @Override
            public void onFailure(@NotNull Call<GrantSchemesResponse> call, @NotNull Throwable t) {
                errorHandlerInterface.handleError(t, context);
            }
        });
    }

}
