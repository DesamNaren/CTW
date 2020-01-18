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
import com.example.twdinspection.common.utils.Utils;
import com.example.twdinspection.databinding.ActivitySchemeSyncBinding;
import com.example.twdinspection.databinding.ActivitySchoolSyncBinding;
import com.example.twdinspection.inspection.source.dmv.SchoolDMVResponse;
import com.example.twdinspection.inspection.source.inst_master.InstMasterResponse;
import com.example.twdinspection.schemes.interfaces.ErrorHandlerInterface;
import com.example.twdinspection.schemes.source.DMV.SchemeDMVResponse;
import com.example.twdinspection.schemes.source.finyear.FinancialYearResponse;
import com.example.twdinspection.schemes.source.remarks.InspectionRemarkResponse;
import com.example.twdinspection.schemes.source.schemes.SchemeResponse;

import org.jetbrains.annotations.NotNull;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SchoolSyncViewModel extends AndroidViewModel {
    private MutableLiveData<SchoolDMVResponse> schoolDMVResponseMutableLiveData;
    private MutableLiveData<InstMasterResponse> instMasterResponseMutableLiveData;
    private Context context;
    private ErrorHandlerInterface errorHandlerInterface;
    private ActivitySchoolSyncBinding binding;

    public SchoolSyncViewModel(Context context, Application application, ActivitySchoolSyncBinding binding) {
        super(application);
        this.context=context;
        this.binding=binding;
        schoolDMVResponseMutableLiveData = new MutableLiveData<>();
        instMasterResponseMutableLiveData = new MutableLiveData<>();
        errorHandlerInterface = (ErrorHandlerInterface) context;
    }

    public LiveData<SchoolDMVResponse> getSchoolDMVReposnse(String officerID) {
        if (schoolDMVResponseMutableLiveData != null) {
            if (Utils.checkInternetConnection(context)) {
                getSchoolDMVResponseCall(officerID);
            }else{
                Utils.customWarningAlert(context,context.getResources().getString(R.string.app_name),"Please check internet");
            }
        }
        return schoolDMVResponseMutableLiveData;
    }

    private void getSchoolDMVResponseCall(String officerId) {
        binding.progress.setVisibility(View.VISIBLE);
        TWDService twdService = TWDService.Factory.create("school");
        twdService.getSchoolDMV(officerId).enqueue(new Callback<SchoolDMVResponse>() {
            @Override
            public void onResponse(@NotNull Call<SchoolDMVResponse> call, @NotNull Response<SchoolDMVResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    schoolDMVResponseMutableLiveData.setValue(response.body());
                }
            }

            @Override
            public void onFailure(@NotNull Call<SchoolDMVResponse> call, @NotNull Throwable t) {
                binding.progress.setVisibility(View.GONE);
                errorHandlerInterface.handleError(t, context);
            }
        });
    }

    public LiveData<InstMasterResponse> getInstMasterResponse() {
        if (instMasterResponseMutableLiveData != null) {
            if (Utils.checkInternetConnection(context)) {
                getInstMasterResponseCall();
            }else{
                Utils.customWarningAlert(context,context.getResources().getString(R.string.app_name),"Please check internet");
            }
        }
        return instMasterResponseMutableLiveData;
    }

    private void getInstMasterResponseCall() {
        binding.progress.setVisibility(View.VISIBLE);
        TWDService twdService = TWDService.Factory.create("school");
        twdService.getInstMasterResponse().enqueue(new Callback<InstMasterResponse>() {
            @Override
            public void onResponse(@NotNull Call<InstMasterResponse> call, @NotNull Response<InstMasterResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    instMasterResponseMutableLiveData.setValue(response.body());
                }
            }

            @Override
            public void onFailure(@NotNull Call<InstMasterResponse> call, @NotNull Throwable t) {
                binding.progress.setVisibility(View.GONE);
                errorHandlerInterface.handleError(t, context);
            }
        });
    }


}

