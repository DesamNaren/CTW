package com.cgg.twdinspection.inspection.viewmodel;

import android.app.Application;
import android.content.Context;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.cgg.twdinspection.common.network.TWDService;
import com.cgg.twdinspection.databinding.ActivitySchoolSyncBinding;
import com.cgg.twdinspection.inspection.source.dmv.SchoolDMVResponse;
import com.cgg.twdinspection.inspection.source.inst_master.InstMasterResponse;
import com.cgg.twdinspection.schemes.interfaces.ErrorHandlerInterface;

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
                getSchoolDMVResponseCall(officerID);
        }
        return schoolDMVResponseMutableLiveData;
    }

    private void getSchoolDMVResponseCall(String officerId) {
        try {
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
                    errorHandlerInterface.handleError(t, context);
                }
            });
        } catch (Exception e) {
            errorHandlerInterface.handleError(e, context);
            e.printStackTrace();
        }
    }

    public LiveData<InstMasterResponse> getInstMasterResponse() {
        if (instMasterResponseMutableLiveData != null) {
                getInstMasterResponseCall();
        }
        return instMasterResponseMutableLiveData;
    }

    private void getInstMasterResponseCall() {
        try {
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
                    errorHandlerInterface.handleError(t, context);
                }
            });
        } catch (Exception e) {
            errorHandlerInterface.handleError(e, context);
        }
    }


}

