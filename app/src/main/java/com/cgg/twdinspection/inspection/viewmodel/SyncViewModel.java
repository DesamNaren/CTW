package com.cgg.twdinspection.inspection.viewmodel;

import android.app.Application;
import android.content.Context;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.cgg.twdinspection.common.network.TWDService;
import com.cgg.twdinspection.databinding.ActivitySchoolSyncBinding;
import com.cgg.twdinspection.inspection.source.diet_issues.DietMasterResponse;
import com.cgg.twdinspection.inspection.source.dmv.SchoolDMVResponse;
import com.cgg.twdinspection.inspection.source.inst_master.InstMasterResponse;
import com.cgg.twdinspection.schemes.interfaces.ErrorHandlerInterface;

import org.jetbrains.annotations.NotNull;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SyncViewModel extends AndroidViewModel {
    private MutableLiveData<InstMasterResponse> instMasterResponseMutableLiveData;
    private MutableLiveData<DietMasterResponse> dietMasterResponseMutableLiveData;
    private Context context;
    private ErrorHandlerInterface errorHandlerInterface;

    public SyncViewModel(Context context, Application application) {
        super(application);
        this.context = context;
        instMasterResponseMutableLiveData = new MutableLiveData<>();
        dietMasterResponseMutableLiveData = new MutableLiveData<>();
        errorHandlerInterface = (ErrorHandlerInterface) context;
    }

    public LiveData<InstMasterResponse> getStudentsInstMasterResponse(String institute_id) {
        if (instMasterResponseMutableLiveData != null) {
            getInstMasterResponseCall(institute_id);
        }
        return instMasterResponseMutableLiveData;
    }

    private void getInstMasterResponseCall(String institute_id) {
        try {
            TWDService twdService = TWDService.Factory.create("school");
            twdService.getStudentInstMasterResponse(institute_id).enqueue(new Callback<InstMasterResponse>() {
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

    public LiveData<DietMasterResponse> getDietInstMasterResponse(String institute_id) {
        if (dietMasterResponseMutableLiveData != null) {
            getDietInstMasterResponseCall(institute_id);
        }
        return dietMasterResponseMutableLiveData;
    }

    private void getDietInstMasterResponseCall(String institute_id) {
        try {
            TWDService twdService = TWDService.Factory.create("school");
            twdService.getDietInstMasterResponse(institute_id).enqueue(new Callback<DietMasterResponse>() {
                @Override
                public void onResponse(@NotNull Call<DietMasterResponse> call, @NotNull Response<DietMasterResponse> response) {
                    if (response.isSuccessful() && response.body() != null) {
                        dietMasterResponseMutableLiveData.setValue(response.body());
                    }
                }

                @Override
                public void onFailure(@NotNull Call<DietMasterResponse> call, @NotNull Throwable t) {
                    errorHandlerInterface.handleError(t, context);
                }
            });
        } catch (Exception e) {
            errorHandlerInterface.handleError(e, context);
        }
    }
}

