package com.cgg.twdinspection.inspection.viewmodel;

import android.app.Application;
import android.content.Context;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.cgg.twdinspection.common.network.TWDService;
import com.cgg.twdinspection.gcc.reports.source.GCCReportResponse;
import com.cgg.twdinspection.inspection.source.version_check.VersionResponse;
import com.cgg.twdinspection.schemes.interfaces.ErrorHandlerInterface;

import org.jetbrains.annotations.NotNull;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SplashViewModel extends AndroidViewModel {
    private MutableLiveData<VersionResponse> versionResponseMutableLiveData;
    private Context context;
    private ErrorHandlerInterface errorHandlerInterface;

    public SplashViewModel(Context context, Application application) {
        super(application);
        this.context = context;
        versionResponseMutableLiveData = new MutableLiveData<>();
        errorHandlerInterface = (ErrorHandlerInterface) context;

    }

    public LiveData<VersionResponse> getCurrentVersion() {
        if (versionResponseMutableLiveData != null) {
            callVersionCheck();
        }
        return versionResponseMutableLiveData;
    }

    private void callVersionCheck() {
        TWDService twdService = TWDService.Factory.create("school");
        twdService.getVersionCheck().enqueue(new Callback<VersionResponse>() {
            @Override
            public void onResponse(@NotNull Call<VersionResponse> call, @NotNull Response<VersionResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    versionResponseMutableLiveData.setValue(response.body());
                }
            }

            @Override
            public void onFailure(@NotNull Call<VersionResponse> call, @NotNull Throwable t) {
                errorHandlerInterface.handleError(t, context);
            }
        });
    }

}

